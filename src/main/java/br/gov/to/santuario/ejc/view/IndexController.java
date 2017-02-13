package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.service.EncontroService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Flavio
 */
@ManagedBean
@ViewScoped
public class IndexController implements Serializable {
    
    @ManagedProperty(value = "#{encontroService}")
    private EncontroService encontroService;
    
    private FacesMessages messages = new FacesMessages();
    
    private List<Encontro> listaEncontros;

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

    public List<Encontro> getListaEncontros() {
        if(listaEncontros == null){
            listaEncontros = encontroService.findAllEncontro();
        }
        return listaEncontros;
    }

    public void setListaEncontros(List<Encontro> listaEncontros) {
        this.listaEncontros = listaEncontros;
    }
        
}
