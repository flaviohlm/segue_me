package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Habilidade;
import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.service.HabilidadeService;
import br.gov.to.santuario.ejc.service.SeguidorService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.Serializable;
import java.util.Date;
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
public class SeguidorController implements Serializable {
    @ManagedProperty(value = "#{seguidorService}")
    private SeguidorService seguidorService;
    
    @ManagedProperty(value = "#{habilidadeService}")
    private HabilidadeService habilidadeService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private Seguidor seguidor = new Seguidor();
    private List<Seguidor> listaSeguidores;
    private List<Habilidade> listaHabilidades;
    
    public void salvar(){
        try{        
            if(seguidor.getParticipante().getDataCadastro() == null){
                seguidor.getParticipante().setDataCadastro(new Date());
            }
            if(seguidor.getParticipante().getApelido().equals("") || seguidor.getParticipante().getApelido() == null){
                String[] apelido = seguidor.getParticipante().getNome().split(" ");
                seguidor.getParticipante().setApelido(apelido[0]);
            }
            seguidorService.saveSeguidor(seguidor);            
            messages.info("Seguidor salvo com sucesso!");
            seguidor = new Seguidor();
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar o seguidor.");
            e.printStackTrace();
        }
    }
    
    public void deletar(){
        try{            
            seguidorService.deleteSeguidor(seguidor);
            listaSeguidores = null;
            messages.info("Seguidor deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o seguidor.");
            e.printStackTrace();
        }
    }
    
    public void loadModel(){
        if(id != null){
            seguidor = seguidorService.findOneSeguidor(id);
            if(seguidor == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
        }
    }
    
    public String gotoEdit(){
        return "/segue-me/participantes/seguidores/edit.xhtlm?faces-redirect=true";
    }
    
    public String gotoEdit(Integer ide){
        return "/segue-me/participantes/seguidores/edit.xhtlm?id="+ide+"&faces-redirect=true";
    }

    //GETTERS AND SETTERS
    public SeguidorService getSeguidorService() {
        return seguidorService;
    }

    public void setSeguidorService(SeguidorService seguidorService) {
        this.seguidorService = seguidorService;
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

    public Seguidor getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(Seguidor seguidor) {
        this.seguidor = seguidor;
    }

    public List<Seguidor> getListaSeguidores() {
        if(listaSeguidores == null){
            listaSeguidores = seguidorService.findAllSeguidor();
        }
        return listaSeguidores;
    }

    public void setListaSeguidores(List<Seguidor> listaSeguidores) {
        this.listaSeguidores = listaSeguidores;
    }

    public HabilidadeService getHabilidadeService() {
        return habilidadeService;
    }

    public void setHabilidadeService(HabilidadeService habilidadeService) {
        this.habilidadeService = habilidadeService;
    }

    public List<Habilidade> getListaHabilidades() {
        if(listaHabilidades == null){
            listaHabilidades = habilidadeService.findAllHabilidade();
        }
        return listaHabilidades;
    }

    public void setListaHabilidades(List<Habilidade> listaHabilidades) {
        this.listaHabilidades = listaHabilidades;
    }
    
}

