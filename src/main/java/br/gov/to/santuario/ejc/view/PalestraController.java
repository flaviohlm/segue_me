package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Equipe;
import br.gov.to.santuario.ejc.domain.Palestra;
import br.gov.to.santuario.ejc.service.PalestraService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class PalestraController implements Serializable {
    @ManagedProperty(value = "#{palestraService}")
    private PalestraService palestraService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Palestra palestra = new Palestra();
    private List<Palestra> listaPalestras;

    public void novo(){
        palestra = new Palestra(); 
        palestra.setDescricao("Insira a descrição");        
        listaPalestras.add(0, palestra);
    }
    
    public void deletar(){
        try{
            palestraService.deletePalestras(palestra);
            listaPalestras = null;
            messages.info("Dados excluídos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível excluir os dados.");
        }     
    }
    
    public void onCellEdit(CellEditEvent event) {
        DataTable s = (DataTable) event.getSource();
        Palestra obj = (Palestra) s.getRowData();
        
        try{
            palestraService.savePalestras(obj);
        }catch(Exception e){
            messages.error("Erro ao salvar os dados!");
            e.printStackTrace();
        } 
    }
    
    //GETTERS AND SETTERS
    public PalestraService getPalestraService() {
        return palestraService;
    }

    public void setPalestraService(PalestraService palestraService) {
        this.palestraService = palestraService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Palestra getPalestra() {
        return palestra;
    }

    public void setPalestra(Palestra palestra) {
        this.palestra = palestra;
    }

    public List<Palestra> getListaPalestras() {
        if(listaPalestras==null){
            listaPalestras = palestraService.findAllPalestras();
        }
        return listaPalestras;
    }

    public void setListaPalestras(List<Palestra> listaPalestras) {
        this.listaPalestras = listaPalestras;
    }
}
