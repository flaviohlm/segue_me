package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.EquipeDirigente;
import br.gov.to.santuario.ejc.domain.EquipeDirigenteIntegrante;
import br.gov.to.santuario.ejc.domain.Funcao;
import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.service.EquipeDirigenteIntegranteService;
import br.gov.to.santuario.ejc.service.EquipeDirigenteService;
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
public class EquipeDirigenteController implements Serializable {
    @ManagedProperty(value = "#{equipeDirigenteService}")
    private EquipeDirigenteService equipeDirigenteService;
    
    @ManagedProperty(value = "#{equipeDirigenteIntegranteService}")
    private EquipeDirigenteIntegranteService equipeDirigenteIntegranteService;
    
    @ManagedProperty(value = "#{seguidorService}")
    private SeguidorService seguidorService;
    
    @ManagedProperty(value = "#{funcaoService}")
    private FuncaoService funcaoService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private EquipeDirigente equipeDirigente = new EquipeDirigente();
    private List<EquipeDirigente> listaEquipeDirigente;
    private EquipeDirigente equipeDirigenteSelecionada;
    private List<EquipeDirigenteIntegrante> listaEquipeDirigenteIntegrante;
    private List<Seguidor> listaSeguidores;
    private List<Funcao> listaFuncoes;    

    
    public String salvar(){
        try{            
            if(equipeDirigente.getDataFim() == null){
                for(EquipeDirigente ed :listaEquipeDirigente){
                    if(ed.getDataFim() == null){
                        messages.error("Existe equipe dirigente ativa. Por favor, finalize-a antes de cadastrar uma nova.");
                        return "/segue-me/estrutura/equipe-dirigente/cadastrar/index.xhtml?id="+equipeDirigente.getId()+"&faces-redirect=true";
                    }
                }
            }
            
            if(equipeDirigente.getDescricao() == null || equipeDirigente.getDescricao().equals("")){
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                String data = dt.format(equipeDirigente.getDataInicio());
                equipeDirigente.setDescricao("Conselho Arquidiocesano "+data);
            }
            
            equipeDirigenteService.saveEquipeDirigente(equipeDirigente);
            //messages.info("Agora insira integrantes para a equipe dirigente.");
        }catch(Exception e){
            messages.error("Não foi possível salvar a equipe dirigente.");
            e.printStackTrace();
            return "";
        }
        return "/segue-me/estrutura/equipe-dirigente/editar/index.xhtml?id="+equipeDirigente.getId()+"&faces-redirect=true";
    }
    
    public String atualizar(){
        try{
            if(equipeDirigente.getDescricao() == null || equipeDirigente.getDescricao().equals("")){
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                String data = dt.format(equipeDirigente.getDataInicio());
                equipeDirigente.setDescricao("Conselho Arquidiocesano "+data);
            }
            equipeDirigenteService.saveEquipeDirigente(equipeDirigente);
            messages.info("Equipe dirigente atualizada com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível salvar a equipe dirigente.");
            e.printStackTrace();
            return "/segue-me/estrutura/equipe-dirigente/editar/index.xhtml?id="+equipeDirigente.getId()+"&faces-redirect=true";
        }
        
        return "/segue-me/estrutura/equipe-dirigente/index.xhtml?id="+equipeDirigente.getId()+"&faces-redirect=true";
    }
    
    public void deletar(){
        try{
            equipeDirigenteService.deleteEquipeDirigente(equipeDirigente);
            listaEquipeDirigente = null;
            messages.info("Dados excluídos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível excluir os dados.");
        }     
    }
    
    public String gotoEdit(){
        return "/segue-me/estrutura/equipe-dirigente/cadastrar/index.xhtml?faces-redirect=true";
    }
    
    public void selecionarEquipeDirigente(SelectEvent event) throws IOException {                
        FacesContext.getCurrentInstance().getExternalContext().redirect("/segueme/segue-me/estrutura/equipe-dirigente/editar/index.xhtml?id=" + equipeDirigenteSelecionada.getId());
    }
    
    public void loadModel(){
        if(id != null){
            equipeDirigente = equipeDirigenteService.findOneEquipeDirigente(id);
            if(equipeDirigente == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
            //listaEquipeDirigenteIntegrante = equipeDirigente.getEquipeDirigenteIntegranteList();
//            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<---------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>.");
//            listaEquipeDirigenteIntegrante = equipeDirigenteIntegranteService.findSeguidoresDaEquipe(equipeDirigente);
//            System.out.println("-------------------------------------------------------------------------------------");
        }
    }
    
    public void selecionarIntegrante(){        
        try{                     
            equipeDirigenteService.saveEquipeDirigente(equipeDirigente);
            this.loadModel();
        }catch(Exception e){
            messages.error("Não foi possível salvar o(s) Integrante(s).");
            e.printStackTrace();
        }
    }
    
    public void onCellEdit(CellEditEvent event) {
        DataTable s = (DataTable) event.getSource();
        EquipeDirigenteIntegrante obj = (EquipeDirigenteIntegrante) s.getRowData();
        
        try{
            equipeDirigenteIntegranteService.saveEquipeDirigenteIntegrante(obj);  
            //this.loadModel();
        }catch(Exception e){
            messages.error("Erro ao salvar os dados!");
            e.printStackTrace();
        } 
    }
    
    public void duplicarEquipeDirigente(){
        
        equipeDirigente.setDataFim(new Date());
        
        EquipeDirigente ed = new EquipeDirigente();        
        ed.setDataInicio(new Date());
        ed.setDataFim(null);
        ed.setDataInicio(new Date());
        ed.setParoquia(equipeDirigente.getParoquia());
        ed.setDiretorEspiritual(equipeDirigente.getDiretorEspiritual());
        List<EquipeDirigenteIntegrante> listaAux = new ArrayList<>();
        ed.setEquipeDirigenteIntegranteList(listaAux);
        
        EquipeDirigenteIntegrante novaEdi;
        for(EquipeDirigenteIntegrante edi : equipeDirigente.getEquipeDirigenteIntegranteList()){
            novaEdi = new EquipeDirigenteIntegrante();
            novaEdi.setEquipeDirigente(ed);
            novaEdi.setFuncao(edi.getFuncao());
            novaEdi.setSeguidor(edi.getSeguidor());
            
            ed.getEquipeDirigenteIntegranteList().add(novaEdi);
        }
        
        try{
            equipeDirigenteService.saveEquipeDirigente(equipeDirigente);            
            equipeDirigenteService.saveEquipeDirigente(ed);
            listaEquipeDirigente = null;
            messages.info("Equipe duplicada com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível duplicar a equipe dirigente.");
            e.printStackTrace();
        }
        
    }
    
    //GETTERS AND SETTERS
    public EquipeDirigenteService getEquipeDirigenteService() {
        return equipeDirigenteService;
    }

    public void setEquipeDirigenteService(EquipeDirigenteService equipeDirigenteService) {
        this.equipeDirigenteService = equipeDirigenteService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public EquipeDirigente getEquipeDirigente() {
        return equipeDirigente;
    }

    public void setEquipeDirigente(EquipeDirigente equipeDirigente) {
        this.equipeDirigente = equipeDirigente;
    }

    public List<EquipeDirigente> getListaEquipeDirigente() {
        if(listaEquipeDirigente == null){
            listaEquipeDirigente = equipeDirigenteService.findAllEquipeDirigente();
        }
        return listaEquipeDirigente;
    }

    public void setListaEquipeDirigente(List<EquipeDirigente> listaEquipeDirigente) {
        this.listaEquipeDirigente = listaEquipeDirigente;
    }

    public EquipeDirigente getEquipeDirigenteSelecionada() {
        return equipeDirigenteSelecionada;
    }

    public void setEquipeDirigenteSelecionada(EquipeDirigente equipeDirigenteSelecionada) {
        this.equipeDirigenteSelecionada = equipeDirigenteSelecionada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<EquipeDirigenteIntegrante> getListaEquipeDirigenteIntegrante() {
        return listaEquipeDirigenteIntegrante;
    }

    public void setListaEquipeDirigenteIntegrante(List<EquipeDirigenteIntegrante> listaEquipeDirigenteIntegrante) {
        this.listaEquipeDirigenteIntegrante = listaEquipeDirigenteIntegrante;
    }
    
    public List<Seguidor> getListaSeguidores() {
        if(listaSeguidores == null){
            listaSeguidores = seguidorService.findSeguidoresEquipeDirigente(id);           
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

    public EquipeDirigenteIntegranteService getEquipeDirigenteIntegranteService() {
        return equipeDirigenteIntegranteService;
    }

    public void setEquipeDirigenteIntegranteService(EquipeDirigenteIntegranteService equipeDirigenteIntegranteService) {
        this.equipeDirigenteIntegranteService = equipeDirigenteIntegranteService;
    }

    public FuncaoService getFuncaoService() {
        return funcaoService;
    }

    public void setFuncaoService(FuncaoService funcaoService) {
        this.funcaoService = funcaoService;
    }

    public List<Funcao> getListaFuncoes() {
        if(listaFuncoes == null){
            listaFuncoes = funcaoService.findAllFuncaoByTabela("Equipe Dirigente");
        }
        return listaFuncoes;
    }

    public void setListaFuncoes(List<Funcao> listaFuncoes) {
        this.listaFuncoes = listaFuncoes;
    }
    
    
}
