package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Circulo;
import br.gov.to.santuario.ejc.domain.EncontroEquipeIntegrante;
import br.gov.to.santuario.ejc.service.CirculoService;
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
public class CirculoController implements Serializable {
    @ManagedProperty(value = "#{circuloService}")
    private CirculoService circuloService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Circulo circulo = new Circulo();
    private List<Circulo> listaCirculos;

    public void novo(){
        circulo = new Circulo(); 
        circulo.setApostolo("Insira aqui o nome do apóstolo do círculo");        
        circulo.setCor("Insira aqui o nome da cor para o círculo");
        listaCirculos.add(0, circulo);
    }
    
    public void deletar(){
        try{
            circuloService.deleteCirculo(circulo);
            listaCirculos = null;
            messages.info("Dados excluídos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível excluir os dados.");
        }     
    }
    
    public void onCellEdit(CellEditEvent event) {
        DataTable s = (DataTable) event.getSource();
        Circulo obj = (Circulo) s.getRowData();
        
        try{
            circuloService.saveCirculo(obj);            
        }catch(Exception e){
            messages.error("Erro ao salvar os dados!");
            e.printStackTrace();
        } 
    }
    
    //GETTERS AND SETTERS
    public CirculoService getCirculoService() {
        return circuloService;
    }

    public void setCirculoService(CirculoService circuloService) {
        this.circuloService = circuloService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Circulo getCirculo() {
        return circulo;
    }

    public void setCirculo(Circulo circulo) {
        this.circulo = circulo;
    }

    public List<Circulo> getListaCirculos() {
        if(listaCirculos==null){
            listaCirculos = circuloService.findAllCirculo();
        }
        return listaCirculos;
    }

    public void setListaCirculos(List<Circulo> listaCirculos) {
        this.listaCirculos = listaCirculos;
    }
}
