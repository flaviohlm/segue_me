package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.EncontroEquipe;
import br.gov.to.santuario.ejc.domain.EncontroEquipeIntegrante;
import br.gov.to.santuario.ejc.domain.Equipe;
import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.service.EncontroEquipeIntegranteService;
import br.gov.to.santuario.ejc.service.EncontroEquipeService;
import br.gov.to.santuario.ejc.service.EquipeService;
import br.gov.to.santuario.ejc.service.SeguidorService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class EncontroEquipeController implements Serializable {
    @ManagedProperty(value = "#{encontroEquipeService}")
    private EncontroEquipeService encontroEquipeService;
    
    @ManagedProperty(value = "#{equipeService}")
    private EquipeService equipeService;
    
    @ManagedProperty(value = "#{seguidorService}")
    private SeguidorService seguidorService;
    
    @ManagedProperty(value = "#{encontroEquipeIntegranteService}")
    private EncontroEquipeIntegranteService encontroEquipeIntegranteService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private EncontroEquipe encontroEquipe = new EncontroEquipe();
    private List<Equipe> listaEquipe;    
    private List<Seguidor> listaSeguidores; 
    private EncontroEquipeIntegrante encontroEquipeIntegrante = new EncontroEquipeIntegrante();
    
    public void salvar(){        
        try{            
            encontroEquipeService.saveEncontroEquipe(encontroEquipe);
            messages.info("Integrantes da equipe salvo com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível salvar o encontroEquipe.");
            e.printStackTrace();
        }
    }
    
    public void deletar(){
        try{            
            encontroEquipeService.deleteEncontroEquipe(encontroEquipe);
            messages.info("EncontroEquipe deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o encontroEquipe.");
            e.printStackTrace();
        }
    }
    
    public void loadModel(){
        if(id != null){
            encontroEquipe = encontroEquipeService.findOneEncontroEquipe(id);
            if(encontroEquipe == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }       
        }
    }
    
    public String gotoEdit(Integer ide){
        return "/segue-me/estrutura/equipes/edit.xhtlm?id="+ide+"&faces-redirect=true";
    }
    
    public void selecionarIntegrante(){
        try{            
            encontroEquipeService.saveEncontroEquipe(encontroEquipe);  
            this.loadModel();
        }catch(Exception e){
            messages.error("Não foi possível salvar o(s) Integrante(s).");
            e.printStackTrace();
        }
    }
    
    public void selecionarCoordenador(EncontroEquipeIntegrante encontroEquipeIntegrantes){
        try{
            encontroEquipeIntegranteService.saveEncontroEquipeIntegrante(encontroEquipeIntegrantes);
        }catch(Exception e){
            messages.error("Não foi possível salvar o(a) coordenador(a).");
            e.printStackTrace();
        }
    }
    
    public void salvarEncontroEquipeIntegrante(){
        try{
            encontroEquipeIntegranteService.saveEncontroEquipeIntegrante(encontroEquipeIntegrante);
        }catch(Exception e){
            messages.error("Não foi possível salvar o(a) coordenador(a).");
            e.printStackTrace();
        }
    }
    
    //GETTERS AND SETTERS
    public EncontroEquipeService getEncontroEquipeService() {
        return encontroEquipeService;
    }

    public void setEncontroEquipeService(EncontroEquipeService encontroEquipeService) {
        this.encontroEquipeService = encontroEquipeService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public EncontroEquipe getEncontroEquipe() {
        return encontroEquipe;
    }

    public void setEncontroEquipe(EncontroEquipe encontroEquipe) {
        this.encontroEquipe = encontroEquipe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EquipeService getEquipeService() {
        return equipeService;
    }

    public void setEquipeService(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    public List<Equipe> getListaEquipe() {
        if(listaEquipe == null){
            listaEquipe = equipeService.findAllEquipe();
        }
        return listaEquipe;
    }

    public void setListaEquipe(List<Equipe> listaEquipe) {
        this.listaEquipe = listaEquipe;
    }

    public SeguidorService getSeguidorService() {
        return seguidorService;
    }

    public void setSeguidorService(SeguidorService seguidorService) {
        this.seguidorService = seguidorService;
    }

    public List<Seguidor> getListaSeguidores() {
        if(encontroEquipe != null){
            listaSeguidores = seguidorService.findSeguidoresDisponiveis(encontroEquipe);
        }
        return listaSeguidores;
    }

    public void setListaSeguidores(List<Seguidor> listaSeguidores) {
        this.listaSeguidores = listaSeguidores;
    }

    public EncontroEquipeIntegranteService getEncontroEquipeIntegranteService() {
        return encontroEquipeIntegranteService;
    }

    public void setEncontroEquipeIntegranteService(EncontroEquipeIntegranteService encontroEquipeIntegranteService) {
        this.encontroEquipeIntegranteService = encontroEquipeIntegranteService;
    }

    public EncontroEquipeIntegrante getEncontroEquipeIntegrante() {
        return encontroEquipeIntegrante;
    }

    public void setEncontroEquipeIntegrante(EncontroEquipeIntegrante encontroEquipeIntegrante) {
        this.encontroEquipeIntegrante = encontroEquipeIntegrante;
    }

}
