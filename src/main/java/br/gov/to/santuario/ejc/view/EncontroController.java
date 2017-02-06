package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Circulo;
import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.domain.Equipe;
import br.gov.to.santuario.ejc.domain.Palestra;
import br.gov.to.santuario.ejc.domain.Paroquia;
import br.gov.to.santuario.ejc.service.CirculoService;
import br.gov.to.santuario.ejc.service.EncontroService;
import br.gov.to.santuario.ejc.service.EquipeService;
import br.gov.to.santuario.ejc.service.PalestraService;
import br.gov.to.santuario.ejc.service.ParoquiaService;
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
public class EncontroController implements Serializable {
    @ManagedProperty(value = "#{encontroService}")
    private EncontroService encontroService;
    
    @ManagedProperty(value = "#{paroquiaService}")
    private ParoquiaService paroquiaService;
    
    @ManagedProperty(value = "#{circuloService}")
    private CirculoService circuloService;
    
    @ManagedProperty(value = "#{equipeService}")
    private EquipeService equipeService;
    
    @ManagedProperty(value = "#{palestraService}")
    private PalestraService palestraService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private Encontro encontro = new Encontro();
    private List<Encontro> listaEncontros;
    
    private List<Paroquia> listaParoquia;
    private List<Circulo> listaCirculo;
    private List<Equipe> listaEquipe;
    private List<Palestra> listaPalestra;
    
    public void salvar(){        
        try{            
            encontroService.saveEncontro(encontro);
            messages.info("Encontro salvo com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível salvar o encontro.");
            e.printStackTrace();
        }
        
    }
    
    public void deletar(){
        try{            
            encontroService.deleteEncontro(encontro);
            messages.info("Encontro deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o encontro.");
            e.printStackTrace();
        }
    }
    
    public String gotoEdit(){
        return "/segue-me/estrutura/encontro/edit.xhtlm?faces-redirect=true";
    }
    
    public String gotoEdit(Integer ide){
        return "/segue-me/estrutura/encontro/edit.xhtlm?id="+ide+"&faces-redirect=true";
    }
    
    public String gotoEquipes(Integer ide){
        return "/segue-me/estrutura/equipes/index.xhtlm?id="+ide+"&faces-redirect=true";
    }
    
    public String gotoCirculos(Integer ide){
        return "/segue-me/estrutura/circulos/index.xhtlm?id="+ide+"&faces-redirect=true";
    }
    
    public String gotoPalestras(Integer ide){
        return "/segue-me/estrutura/palestras/index.xhtlm?id="+ide+"&faces-redirect=true";
    }
    
    public void loadModel(){
        if(id != null){
            encontro = encontroService.findOneEncontro(id);
            if(encontro == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
        }
    }
    
    //GETTERS AND SETTERS
    public EncontroService getEncontroService() {
        return encontroService;
    }

    public void setEncontroService(EncontroService encontroService) {
        this.encontroService = encontroService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Encontro getEncontro() {
        return encontro;
    }

    public void setEncontro(Encontro encontro) {
        this.encontro = encontro;
    }

    public List<Encontro> getListaEncontros() {
        if(listaEncontros == null){
            listaEncontros = encontroService.findAllEncontro();
        }
        return listaEncontros = encontroService.findAllEncontro();
    }

    public void setListaEncontros(List<Encontro> listaEncontros) {
        this.listaEncontros = listaEncontros;
    }     

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ParoquiaService getParoquiaService() {
        return paroquiaService;
    }

    public void setParoquiaService(ParoquiaService paroquiaService) {
        this.paroquiaService = paroquiaService;
    }

    public List<Paroquia> getListaParoquia() {
        if(listaParoquia == null){
            listaParoquia = paroquiaService.findAllParoquia();
        }
        return listaParoquia;
    }

    public void setListaParoquia(List<Paroquia> listaParoquia) {
        this.listaParoquia = listaParoquia;
    }
 
    public CirculoService getCirculoService() {
        return circuloService;
    }

    public void setCirculoService(CirculoService circuloService) {
        this.circuloService = circuloService;
    }

    public EquipeService getEquipeService() {
        return equipeService;
    }

    public void setEquipeService(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    public PalestraService getPalestraService() {
        return palestraService;
    }

    public void setPalestraService(PalestraService palestraService) {
        this.palestraService = palestraService;
    }

    public List<Circulo> getListaCirculo() {
        if(listaCirculo == null){
            listaCirculo = circuloService.findAllCirculo();
        }
        return listaCirculo;
    }

    public void setListaCirculo(List<Circulo> listaCirculo) {
        this.listaCirculo = listaCirculo;
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

    public List<Palestra> getListaPalestra() {
        if(listaPalestra == null){
            listaPalestra = palestraService.findAllPalestras();
        }
        return listaPalestra;
    }

    public void setListaPalestra(List<Palestra> listaPalestra) {
        this.listaPalestra = listaPalestra;
    }

}
