package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.domain.EncontroCirculo;
import br.gov.to.santuario.ejc.domain.EncontroCirculoSeguimista;
import br.gov.to.santuario.ejc.domain.Equipe;
import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.domain.Seguimista;
import br.gov.to.santuario.ejc.service.EncontroCirculoSeguimistaService;
import br.gov.to.santuario.ejc.service.EncontroCirculoService;
import br.gov.to.santuario.ejc.service.EncontroService;
import br.gov.to.santuario.ejc.service.EquipeService;
import br.gov.to.santuario.ejc.service.SeguidorService;
import br.gov.to.santuario.ejc.service.SeguimistaService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class EncontroCirculoController implements Serializable {
    @ManagedProperty(value = "#{encontroCirculoService}")
    private EncontroCirculoService encontroCirculoService;    
    
    @ManagedProperty(value = "#{equipeService}")
    private EquipeService equipeService;
    
    @ManagedProperty(value = "#{seguidorService}")
    private SeguidorService seguidorService;
    
    @ManagedProperty(value = "#{seguimistaService}")
    private SeguimistaService seguimistaService;
    
    @ManagedProperty(value = "#{encontroCirculoSeguimistaService}")
    private EncontroCirculoSeguimistaService encontroCirculoSeguimistaService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private EncontroCirculo encontroCirculo;    
    private List<EncontroCirculo> listaEncontroCirculo = new ArrayList<>();
    private List<Equipe> listaEquipe;    
    private List<Seguimista> listaSeguimista; 
    private List<Seguidor> listaSeguidores;
    
    private List<Seguidor> listaSeguidoresPadrinhos;
    private List<Seguidor> listaSeguidoresMadrinhas;
    private Seguidor padrinho;
    private Seguidor madrinha;
    
    private EncontroCirculoSeguimista encontroCirculoSeguimista = new EncontroCirculoSeguimista();
    
    
    public void deletar(){
        try{            
            encontroCirculoService.deleteEncontroCirculo(encontroCirculo);
            messages.info("EncontroCirculo deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o encontroCirculo.");
            e.printStackTrace();
        }
    }
    
    public void loadModelEncontro(){
        if(id != null){
            listaEncontroCirculo = encontroCirculoService.findAllEncontroCirculoByEncontro(id);                
        }
    }
    
    public void loadModel(){
        if(id != null){
            encontroCirculo = encontroCirculoService.findOneEncontroCirculo(id);            
            if(encontroCirculo == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }       
        }
    }
    
    public String gotoEdit(Integer ide){
        return "/segue-me/estrutura/circulos/edit.xhtlm?id="+ide+"&faces-redirect=true";
    }
    
    public void selecionarIntegrante(){
        try{              
            if(padrinho != null){
                encontroCirculo.setSeguidorPadrinho(padrinho);
                padrinho = null;
            }
            if(madrinha != null){
                encontroCirculo.setSeguidorMadrinha(madrinha);
                madrinha = null;
            }
            encontroCirculoService.saveEncontroCirculo(encontroCirculo);  
            this.loadModel();
        }catch(Exception e){
            messages.error("Não foi possível salvar o(s) Integrante(s).");
            e.printStackTrace();
        }
    }
       
    public void salvarEncontroCirculoSeguimista(){
        try{
            encontroCirculoSeguimistaService.saveEncontroCirculoSeguimista(encontroCirculoSeguimista);
        }catch(Exception e){
            messages.error("Não foi possível salvar o(a) coordenador(a).");
            e.printStackTrace();
        }
    }
    
    public void caralho(EncontroCirculo ec){
        encontroCirculo = ec;
        padrinho = ec.getSeguidorPadrinho();
        madrinha = ec.getSeguidorMadrinha();
        getListaSeguidoresPadrinhos();
    }
    
    
    //GETTERS AND SETTERS
    public EncontroCirculoService getEncontroCirculoService() {
        return encontroCirculoService;
    }

    public void setEncontroCirculoService(EncontroCirculoService encontroCirculoService) {
        this.encontroCirculoService = encontroCirculoService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public EncontroCirculo getEncontroCirculo() {
        return encontroCirculo;
    }

    public void setEncontroCirculo(EncontroCirculo encontroCirculo) {
        this.encontroCirculo = encontroCirculo;
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
        if(encontroCirculo != null){
            listaSeguidores = seguidorService.findAllSeguidor();//findSeguidoresDisponiveis(encontroCirculo);
        }
        return listaSeguidores;
    }

    public void setListaSeguidores(List<Seguidor> listaSeguidores) {
        this.listaSeguidores = listaSeguidores;
    }

    public EncontroCirculoSeguimistaService getEncontroCirculoSeguimistaService() {
        return encontroCirculoSeguimistaService;
    }

    public void setEncontroCirculoSeguimistaService(EncontroCirculoSeguimistaService encontroCirculoSeguimistaService) {
        this.encontroCirculoSeguimistaService = encontroCirculoSeguimistaService;
    }

    public EncontroCirculoSeguimista getEncontroCirculoSeguimista() {
        return encontroCirculoSeguimista;
    }

    public void setEncontroCirculoSeguimista(EncontroCirculoSeguimista encontroCirculoSeguimista) {
        this.encontroCirculoSeguimista = encontroCirculoSeguimista;
    }

    public SeguimistaService getSeguimistaService() {
        return seguimistaService;
    }

    public void setSeguimistaService(SeguimistaService seguimistaService) {
        this.seguimistaService = seguimistaService;
    }

    public List<Seguimista> getListaSeguimista() {
        if(encontroCirculo != null){
            listaSeguimista = seguimistaService.findSeguimistasDisponiveis(encontroCirculo);//findSeguidoresDisponiveis(encontroCirculo);
        }
        return listaSeguimista;
    }

    public void setListaSeguimista(List<Seguimista> listaSeguimista) {
        this.listaSeguimista = listaSeguimista;
    }

    public List<Seguidor> getListaSeguidoresPadrinhos() {              
        if(listaSeguidoresPadrinhos == null && encontroCirculo != null){            
            listaSeguidoresPadrinhos = seguidorService.findSeguidoresPadrinhosDisponiveis(encontroCirculo, "M");
        }
        return listaSeguidoresPadrinhos;
    }

    public void setListaSeguidoresPadrinhos(List<Seguidor> listaSeguidoresPadrinhos) {
        this.listaSeguidoresPadrinhos = listaSeguidoresPadrinhos;
    }

    public List<Seguidor> getListaSeguidoresMadrinhas() {
        if(listaSeguidoresMadrinhas == null && encontroCirculo != null){
            listaSeguidoresMadrinhas = seguidorService.findSeguidoresPadrinhosDisponiveis(encontroCirculo, "F");
        }
        return listaSeguidoresMadrinhas;
    }

    public void setListaSeguidoresMadrinhas(List<Seguidor> listaSeguidoresMadrinhas) {        
        this.listaSeguidoresMadrinhas = listaSeguidoresMadrinhas;
    }

    public Seguidor getPadrinho() {
        return padrinho;
    }

    public void setPadrinho(Seguidor padrinho) {
        this.padrinho = padrinho;
    }

    public Seguidor getMadrinha() {
        return madrinha;
    }

    public void setMadrinha(Seguidor madrinha) {
        this.madrinha = madrinha;
    }

    public List<EncontroCirculo> getListaEncontroCirculo() {
        return listaEncontroCirculo;
    }

    public void setListaEncontroCirculo(List<EncontroCirculo> listaEncontroCirculo) {
        this.listaEncontroCirculo = listaEncontroCirculo;
    }

}
