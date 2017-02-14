package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.domain.Habilidade;
import br.gov.to.santuario.ejc.domain.Seguimista;
import br.gov.to.santuario.ejc.service.EncontroService;
import br.gov.to.santuario.ejc.service.HabilidadeService;
import br.gov.to.santuario.ejc.service.SeguimistaService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.IOException;
import java.io.Serializable;
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
public class SeguimistaController implements Serializable {
    @ManagedProperty(value = "#{seguimistaService}")
    private SeguimistaService seguimistaService;
    
    @ManagedProperty(value = "#{habilidadeService}")
    private HabilidadeService habilidadeService;
    
    @ManagedProperty(value = "#{encontroService}")
    private EncontroService encontroService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private Seguimista seguimista = new Seguimista();
    private List<Seguimista> listaSeguimistas;
    private List<Habilidade> listaHabilidades;
    private List<Encontro> listaEncontro;
    private Seguimista seguimistaSelecionado;
    
    public void salvar(){
        try{        
            if(seguimista.getParticipante().getDataCadastro() == null){
                seguimista.getParticipante().setDataCadastro(new Date());
            }
            if(seguimista.getParticipante().getApelido().equals("") || seguimista.getParticipante().getApelido() == null){
                String[] apelido = seguimista.getParticipante().getNome().split(" ");
                seguimista.getParticipante().setApelido(apelido[0]);
            }
            seguimistaService.saveSeguimista(seguimista);            
            messages.info("Seguimista salvo com sucesso!");
            seguimista = new Seguimista();
            if(getListaEncontro().size() > 0){                            
                seguimista.setEncontro(listaEncontro.get(0));
            }
            
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar o seguimista.");
            e.printStackTrace();
        }
    }
    
    public String atualizar(){
        try{                   
            if(seguimista.getParticipante().getApelido().equals("") || seguimista.getParticipante().getApelido() == null){
                String[] apelido = seguimista.getParticipante().getNome().split(" ");
                seguimista.getParticipante().setApelido(apelido[0]);
            }
            seguimistaService.saveSeguimista(seguimista);            
            messages.info("Seguimista salvo com sucesso!");
            seguimista = new Seguimista();
            if(getListaEncontro().size() > 0){                            
                seguimista.setEncontro(listaEncontro.get(0));
            }
            
        }catch(Exception e){
            System.out.println("error" + e.getMessage());
            messages.error("Não foi possível salvar o seguimista.");
            e.printStackTrace();
            return "";
        }
        return "/segue-me/participantes/seguimistas/index.xhtml";
    }
    
    public void deletar(){
        try{            
            seguimistaService.deleteSeguimista(seguimista);
            listaSeguimistas = null;
            messages.info("Seguimista deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o seguimista.");
            e.printStackTrace();
        }
    }
    
    public void loadModel(){
        if(id != null){
            seguimista = seguimistaService.findOneSeguimista(id);
            if(seguimista == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
        }else{
            if(getListaEncontro().size() > 0){                
                seguimista = new Seguimista();
                seguimista.setEncontro(listaEncontro.get(0));
            }
        }
    }
    
    public String gotoEdit(){
        return "/segue-me/participantes/seguimistas/cadastrar/index.xhtlm?faces-redirect=true";
    }
    
    public void selecionarSeguimista(SelectEvent event) throws IOException {                
        FacesContext.getCurrentInstance().getExternalContext().redirect("/segueme/segue-me/participantes/seguimistas/editar/index.xhtml?id=" + seguimistaSelecionado.getId());
    }
    
    //GETTERS AND SETTERS
    public SeguimistaService getSeguimistaService() {
        return seguimistaService;
    }

    public void setSeguimistaService(SeguimistaService seguimistaService) {
        this.seguimistaService = seguimistaService;
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

    public Seguimista getSeguimista() {
        return seguimista;
    }

    public void setSeguimista(Seguimista seguimista) {
        this.seguimista = seguimista;
    }

    public List<Seguimista> getListaSeguimistas() {
        if(listaSeguimistas == null){
            listaSeguimistas = seguimistaService.findAllSeguimista();
        }
        return listaSeguimistas;
    }

    public void setListaSeguimistas(List<Seguimista> listaSeguimistas) {
        this.listaSeguimistas = listaSeguimistas;
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

    public Seguimista getSeguimistaSelecionado() {
        return seguimistaSelecionado;
    }

    public void setSeguimistaSelecionado(Seguimista seguimistaSelecionado) {
        this.seguimistaSelecionado = seguimistaSelecionado;
    }
    
}

