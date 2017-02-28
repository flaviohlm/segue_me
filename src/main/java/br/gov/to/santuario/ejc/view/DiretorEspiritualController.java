package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.domain.EncontroPalestra;
import br.gov.to.santuario.ejc.domain.Habilidade;
import br.gov.to.santuario.ejc.domain.DiretorEspiritual;
import br.gov.to.santuario.ejc.service.EncontroPalestraService;
import br.gov.to.santuario.ejc.service.EncontroService;
import br.gov.to.santuario.ejc.service.HabilidadeService;
import br.gov.to.santuario.ejc.service.DiretorEspiritualService;
import br.gov.to.santuario.seg.domain.Participante;
import br.gov.to.santuario.seg.service.ParticipanteService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class DiretorEspiritualController implements Serializable {
    @ManagedProperty(value = "#{diretorEspiritualService}")
    private DiretorEspiritualService diretorEspiritualService;
    
    @ManagedProperty(value = "#{habilidadeService}")
    private HabilidadeService habilidadeService;
    
    @ManagedProperty(value = "#{encontroService}")
    private EncontroService encontroService;
    
    @ManagedProperty(value = "#{encontroPalestraService}")
    private EncontroPalestraService encontroPalestraService;
    
    @ManagedProperty(value = "#{participanteService}")
    private ParticipanteService participanteService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private DiretorEspiritual diretorEspiritual = new DiretorEspiritual();
    private List<DiretorEspiritual> listaDiretorEspirituals;    
    private DiretorEspiritual diretorEspiritualSelecionado;     
    
    public void salvar(){        
        try{        
            if(diretorEspiritual.getParticipante().getDataCadastro() == null){
                diretorEspiritual.getParticipante().setDataCadastro(new Date());
            }
            if(diretorEspiritual.getParticipante().getApelido().equals("") || diretorEspiritual.getParticipante().getApelido() == null){
                String[] apelido = diretorEspiritual.getParticipante().getNome().split(" ");
                diretorEspiritual.getParticipante().setApelido("Pe. "+apelido[0]);
            }
            diretorEspiritual.getParticipante().setSexo("M");
            diretorEspiritualService.saveDiretorEspiritual(diretorEspiritual);            
            messages.info("DiretorEspiritual salvo com sucesso!");
            diretorEspiritual = new DiretorEspiritual();            
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar o diretorEspiritual.");
            e.printStackTrace();
        }
    }
    
    public String atualizar(){        
        try{                    
            if(diretorEspiritual.getParticipante().getApelido().equals("") || diretorEspiritual.getParticipante().getApelido() == null){
                String[] apelido = diretorEspiritual.getParticipante().getNome().split(" ");
                diretorEspiritual.getParticipante().setApelido(apelido[0]);
            }
            diretorEspiritualService.saveDiretorEspiritual(diretorEspiritual);            
            messages.info("DiretorEspiritual salvo com sucesso!");
            diretorEspiritual = new DiretorEspiritual();            
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar o diretorEspiritual.");
            e.printStackTrace();
            return "";
        }
        
        return "/segue-me/participantes/diretorEspirituals/index.xhtml";
    }
    
    public void deletar(){
        try{            
            diretorEspiritualService.deleteDiretorEspiritual(diretorEspiritual);
            listaDiretorEspirituals = null;
            messages.info("DiretorEspiritual deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o diretorEspiritual.");
            e.printStackTrace();
        }
    }
    
    public void loadModel(){
        if(id != null){
            diretorEspiritual = diretorEspiritualService.findOneDiretorEspiritual(id);
            if(diretorEspiritual == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
        }
    }
    
    public String gotoEdit(){
        return "/segue-me/cadastros-gerais/diretor-espiritual/cadastrar/index.xhtml?faces-redirect=true";
    }    

    public void selecionarDiretorEspiritual(SelectEvent event) throws IOException {                
        FacesContext.getCurrentInstance().getExternalContext().redirect("/segueme/segue-me/cadastros-gerais/diretor-espiritual/editar/index.xhtml?id=" + diretorEspiritualSelecionado.getId());
    }
    
    public void onItemSelect(SelectEvent event) {        
        String nome = (String) event.getObject(); 
        
        Participante participante = participanteService.findByNomeExato(nome);
        
        DiretorEspiritual p = diretorEspiritualService.findByParticipante(participante.getId());
        
        if(p == null){
            participante.setNome(nome);
            diretorEspiritual.setParticipante(participante);
        }else{
            diretorEspiritual = p;
        }
    }
        
    //GETTERS AND SETTERS
    public DiretorEspiritualService getDiretorEspiritualService() {
        return diretorEspiritualService;
    }

    public void setDiretorEspiritualService(DiretorEspiritualService diretorEspiritualService) {
        this.diretorEspiritualService = diretorEspiritualService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DiretorEspiritual getDiretorEspiritual() {
        return diretorEspiritual;
    }

    public void setDiretorEspiritual(DiretorEspiritual diretorEspiritual) {
        this.diretorEspiritual = diretorEspiritual;
    }

    public List<DiretorEspiritual> getListaDiretorEspirituals() {
        if(listaDiretorEspirituals == null){
            listaDiretorEspirituals = diretorEspiritualService.findAllDiretorEspiritual();
        }
        return listaDiretorEspirituals;
    }

    public void setListaDiretorEspirituals(List<DiretorEspiritual> listaDiretorEspirituals) {
        this.listaDiretorEspirituals = listaDiretorEspirituals;
    }

    public HabilidadeService getHabilidadeService() {
        return habilidadeService;
    }

    public void setHabilidadeService(HabilidadeService habilidadeService) {
        this.habilidadeService = habilidadeService;
    }

    public EncontroService getEncontroService() {
        return encontroService;
    }

    public void setEncontroService(EncontroService encontroService) {
        this.encontroService = encontroService;
    }

    public EncontroPalestraService getEncontroPalestraService() {
        return encontroPalestraService;
    }

    public void setEncontroPalestraService(EncontroPalestraService encontroPalestraService) {
        this.encontroPalestraService = encontroPalestraService;
    }

    public DiretorEspiritual getDiretorEspiritualSelecionado() {
        return diretorEspiritualSelecionado;
    }

    public void setDiretorEspiritualSelecionado(DiretorEspiritual diretorEspiritualSelecionado) {
        this.diretorEspiritualSelecionado = diretorEspiritualSelecionado;
    }

    public ParticipanteService getParticipanteService() {
        return participanteService;
    }

    public void setParticipanteService(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }
   
}

