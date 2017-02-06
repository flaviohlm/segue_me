package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.domain.Habilidade;
import br.gov.to.santuario.ejc.domain.Palestrante;
import br.gov.to.santuario.ejc.service.EncontroService;
import br.gov.to.santuario.ejc.service.HabilidadeService;
import br.gov.to.santuario.ejc.service.PalestranteService;
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
public class PalestranteController implements Serializable {
    @ManagedProperty(value = "#{palestranteService}")
    private PalestranteService palestranteService;
    
    @ManagedProperty(value = "#{habilidadeService}")
    private HabilidadeService habilidadeService;
    
    @ManagedProperty(value = "#{encontroService}")
    private EncontroService encontroService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private Palestrante palestrante = new Palestrante();
    private List<Palestrante> listaPalestrantes;
    private List<Habilidade> listaHabilidades;
    private List<Encontro> listaEncontro;
    
    public void salvar(){
        try{        
            if(palestrante.getParticipante().getDataCadastro() == null){
                palestrante.getParticipante().setDataCadastro(new Date());
            }
            if(palestrante.getParticipante().getApelido().equals("") || palestrante.getParticipante().getApelido() == null){
                String[] apelido = palestrante.getParticipante().getNome().split(" ");
                palestrante.getParticipante().setApelido(apelido[0]);
            }
            palestranteService.savePalestrante(palestrante);            
            messages.info("Palestrante salvo com sucesso!");
            palestrante = new Palestrante();            
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar o palestrante.");
            e.printStackTrace();
        }
    }
    
    public void deletar(){
        try{            
            palestranteService.deletePalestrante(palestrante);
            listaPalestrantes = null;
            messages.info("Palestrante deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o palestrante.");
            e.printStackTrace();
        }
    }
    
    public void loadModel(){
        if(id != null){
            palestrante = palestranteService.findOnePalestrante(id);
            if(palestrante == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
        }
    }
    
    public String gotoEdit(){
        return "/segue-me/participantes/palestrantes/edit.xhtlm?faces-redirect=true";
    }
    
    public String gotoEdit(Integer ide){
        return "/segue-me/participantes/palestrantes/edit.xhtlm?id="+ide+"&faces-redirect=true";
    }

    //GETTERS AND SETTERS
    public PalestranteService getPalestranteService() {
        return palestranteService;
    }

    public void setPalestranteService(PalestranteService palestranteService) {
        this.palestranteService = palestranteService;
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

    public Palestrante getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(Palestrante palestrante) {
        this.palestrante = palestrante;
    }

    public List<Palestrante> getListaPalestrantes() {
        if(listaPalestrantes == null){
            listaPalestrantes = palestranteService.findAllPalestrante();
        }
        return listaPalestrantes;
    }

    public void setListaPalestrantes(List<Palestrante> listaPalestrantes) {
        this.listaPalestrantes = listaPalestrantes;
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

    public EncontroService getEncontroService() {
        return encontroService;
    }

    public void setEncontroService(EncontroService encontroService) {
        this.encontroService = encontroService;
    }

    public List<Encontro> getListaEncontro() {
        if(listaEncontro == null){
            listaEncontro = encontroService.findAllEncontro();
        }
        return listaEncontro;
    }

    public void setListaEncontro(List<Encontro> listaEncontro) {
        this.listaEncontro = listaEncontro;
    }
    
}

