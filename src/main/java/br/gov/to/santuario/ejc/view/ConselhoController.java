package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Conselho;
import br.gov.to.santuario.ejc.domain.ConselhoIntegrante;
import br.gov.to.santuario.ejc.domain.Funcao;
import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.service.ConselhoIntegranteService;
import br.gov.to.santuario.ejc.service.ConselhoService;
import br.gov.to.santuario.ejc.service.FuncaoService;
import br.gov.to.santuario.ejc.service.SeguidorService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Flavio
 */
@ManagedBean
@ViewScoped
public class ConselhoController implements Serializable {
    @ManagedProperty(value = "#{conselhoService}")
    private ConselhoService conselhoService;
    
    @ManagedProperty(value = "#{conselhoIntegranteService}")
    private ConselhoIntegranteService conselhoIntegranteService;
    
    @ManagedProperty(value = "#{seguidorService}")
    private SeguidorService seguidorService;
    
    @ManagedProperty(value = "#{funcaoService}")
    private FuncaoService funcaoService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private Conselho conselho = new Conselho();
    private List<Conselho> listaConselho;
    private Conselho conselhoSelecionada;
    private List<ConselhoIntegrante> listaConselhoIntegrante;
    private List<Seguidor> listaSeguidores;
    private List<Funcao> listaFuncoes;    

    
    public String salvar(){
        try{            
            if(conselho.getDataFim() == null){
                if(listaConselho == null){
                    getListaConselho();
                }
                for(Conselho ed :listaConselho){
                    if(ed.getDataFim() == null){
                        messages.error("Existe equipe dirigente ativa. Por favor, finalize-a antes de cadastrar uma nova.");
                        return "/segue-me/estrutura/conselho/cadastrar/index.xhtml?id="+conselho.getId()+"&faces-redirect=true";
                    }
                }
            }
            
            if(conselho.getDescricao() == null || conselho.getDescricao().equals("")){
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                String data = dt.format(conselho.getDataInicio());
                conselho.setDescricao("Conselho Arquidiocesano "+data);
            }
            
            conselhoService.saveConselho(conselho);
            //messages.info("Agora insira integrantes para o conselho.");
        }catch(Exception e){
            messages.error("Não foi possível salvar o conselho.");
            e.printStackTrace();
            return "";
        }
        return "/segue-me/estrutura/conselho/editar/index.xhtml?id="+conselho.getId()+"&faces-redirect=true";
    }
    
    public String atualizar(){
        try{        
            if(conselho.getDescricao() == null || conselho.getDescricao().equals("")){
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                String data = dt.format(conselho.getDataInicio());
                conselho.setDescricao("Conselho Arquidiocesano "+data);
            }
            conselhoService.saveConselho(conselho);
            messages.info("Equipe dirigente atualizada com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível salvar o conselho.");
            e.printStackTrace();
            return "/segue-me/estrutura/conselho/editar/index.xhtml?id="+conselho.getId()+"&faces-redirect=true";
        }
        
        return "/segue-me/estrutura/conselho/index.xhtml?id="+conselho.getId()+"&faces-redirect=true";
    }
    
    public void deletar(){
        try{
            conselhoService.deleteConselho(conselho);
            listaConselho = null;
            messages.info("Dados excluídos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível excluir os dados.");
        }     
    }
    
    public String gotoEdit(){
        return "/segue-me/estrutura/conselho/cadastrar/index.xhtml?faces-redirect=true";
    }
    
    public void selecionarConselho(SelectEvent event) throws IOException {                
        FacesContext.getCurrentInstance().getExternalContext().redirect("/segueme/segue-me/estrutura/conselho/editar/index.xhtml?id=" + conselhoSelecionada.getId());
    }
    
    public void loadModel(){
        if(id != null){
            conselho = conselhoService.findOneConselho(id);
            if(conselho == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
        }
    }
    
    public void selecionarIntegrante(){        
        try{                     
            conselhoService.saveConselho(conselho);
            this.loadModel();
        }catch(Exception e){
            messages.error("Não foi possível salvar o(s) Integrante(s).");
            e.printStackTrace();
        }
    }
    
    public void onCellEdit(CellEditEvent event) {
        DataTable s = (DataTable) event.getSource();
        ConselhoIntegrante obj = (ConselhoIntegrante) s.getRowData();
        
        try{
            conselhoIntegranteService.saveConselhoIntegrante(obj);  
            //this.loadModel();
        }catch(Exception e){
            messages.error("Erro ao salvar os dados!");
            e.printStackTrace();
        } 
    }
    
    public void duplicarConselho(){
        
        conselho.setDataFim(new Date());
        
        Conselho ed = new Conselho();        
        ed.setDataInicio(new Date());
        ed.setDataFim(null);
        ed.setDataInicio(new Date());        
        ed.setDiretorEspiritual(conselho.getDiretorEspiritual());
        List<ConselhoIntegrante> listaAux = new ArrayList<>();
        ed.setConselhoIntegranteList(listaAux);
        
        ConselhoIntegrante novaEdi;
        for(ConselhoIntegrante edi : conselho.getConselhoIntegranteList()){
            novaEdi = new ConselhoIntegrante();
            novaEdi.setConselho(ed);
            novaEdi.setFuncao(edi.getFuncao());
            novaEdi.setSeguidor(edi.getSeguidor());
            
            ed.getConselhoIntegranteList().add(novaEdi);
        }
        
        try{
            conselhoService.saveConselho(conselho);            
            conselhoService.saveConselho(ed);
            listaConselho = null;
            messages.info("Conselho duplicado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível duplicar o conselho.");
            e.printStackTrace();
        }
        
    }
    
    //GETTERS AND SETTERS
    public ConselhoService getConselhoService() {
        return conselhoService;
    }

    public void setConselhoService(ConselhoService conselhoService) {
        this.conselhoService = conselhoService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Conselho getConselho() {
        return conselho;
    }

    public void setConselho(Conselho conselho) {
        this.conselho = conselho;
    }

    public List<Conselho> getListaConselho() {
        if(listaConselho == null){
            listaConselho = conselhoService.findAllConselho();
        }
        return listaConselho;
    }

    public void setListaConselho(List<Conselho> listaConselho) {
        this.listaConselho = listaConselho;
    }

    public Conselho getConselhoSelecionada() {
        return conselhoSelecionada;
    }

    public void setConselhoSelecionada(Conselho conselhoSelecionada) {
        this.conselhoSelecionada = conselhoSelecionada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ConselhoIntegrante> getListaConselhoIntegrante() {
        return listaConselhoIntegrante;
    }

    public void setListaConselhoIntegrante(List<ConselhoIntegrante> listaConselhoIntegrante) {
        this.listaConselhoIntegrante = listaConselhoIntegrante;
    }
    
    public List<Seguidor> getListaSeguidores() {
        if(listaSeguidores == null){
            listaSeguidores = seguidorService.findSeguidoresConselho(id);           
        }
        return listaSeguidores;
    }

    public void setListaSeguidores(List<Seguidor> listaSeguidores) {
        this.listaSeguidores = listaSeguidores;
    }

    public SeguidorService getSeguidorService() {
        return seguidorService;
    }

    public void setSeguidorService(SeguidorService seguidorService) {
        this.seguidorService = seguidorService;
    }

    public ConselhoIntegranteService getConselhoIntegranteService() {
        return conselhoIntegranteService;
    }

    public void setConselhoIntegranteService(ConselhoIntegranteService conselhoIntegranteService) {
        this.conselhoIntegranteService = conselhoIntegranteService;
    }

    public FuncaoService getFuncaoService() {
        return funcaoService;
    }

    public void setFuncaoService(FuncaoService funcaoService) {
        this.funcaoService = funcaoService;
    }

    public List<Funcao> getListaFuncoes() {
        if(listaFuncoes == null){
            listaFuncoes = funcaoService.findAllFuncaoByTabela("Conselho Arquidiocesano");
        }
        return listaFuncoes;
    }

    public void setListaFuncoes(List<Funcao> listaFuncoes) {
        this.listaFuncoes = listaFuncoes;
    }
    
}
