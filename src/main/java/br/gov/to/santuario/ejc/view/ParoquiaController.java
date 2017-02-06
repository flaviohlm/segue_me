package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Paroquia;
import br.gov.to.santuario.ejc.service.ParoquiaService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class ParoquiaController implements Serializable {
    @ManagedProperty(value = "#{paroquiaService}")
    private ParoquiaService paroquiaService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Paroquia paroquia = new Paroquia();
    private List<Paroquia> listaParoquias;

    public void novo(){
        paroquia = new Paroquia(); 
        paroquia.setDescricao("Insira a descrição");        
        listaParoquias.add(0, paroquia);
    }
    
    public void deletar(){
        try{
            paroquiaService.deleteParoquia(paroquia);
            listaParoquias = null;
            messages.info("Dados excluídos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível excluir os dados.");
        }     
    }
    
    public void onRowEdit(RowEditEvent event) {
        Paroquia o = (Paroquia) event.getObject();                
        
        try{
//            if(o.getDataCadastro() == null){
//                o.setDataCadastro(new Date());
//            }
            paroquiaService.saveParoquia(o);        
            messages.info("Dados salvos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível salvar os dados.");
            ex.printStackTrace();
        } 
        
    }
    
    //GETTERS AND SETTERS
    public ParoquiaService getParoquiaService() {
        return paroquiaService;
    }

    public void setParoquiaService(ParoquiaService paroquiaService) {
        this.paroquiaService = paroquiaService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Paroquia getParoquia() {
        return paroquia;
    }

    public void setParoquia(Paroquia paroquia) {
        this.paroquia = paroquia;
    }

    public List<Paroquia> getListaParoquias() {
        if(listaParoquias==null){
            listaParoquias = paroquiaService.findAllParoquia();
        }
        return listaParoquias;
    }

    public void setListaParoquias(List<Paroquia> listaParoquias) {
        this.listaParoquias = listaParoquias;
    }
}
