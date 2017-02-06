package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Habilidade;
import br.gov.to.santuario.ejc.service.HabilidadeService;
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
public class HabilidadeController implements Serializable {
    @ManagedProperty(value = "#{habilidadeService}")
    private HabilidadeService habilidadeService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Habilidade habilidade = new Habilidade();
    private List<Habilidade> listaHabilidades;

    public void novo(){
        habilidade = new Habilidade(); 
        habilidade.setDescricao("Insira a descrição");        
        listaHabilidades.add(0, habilidade);
    }
    
    public void deletar(){
        try{
            habilidadeService.deleteHabilidade(habilidade);
            listaHabilidades = null;
            messages.info("Dados excluídos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível excluir os dados.");
        }     
    }
    
    public void onRowEdit(RowEditEvent event) {
        Habilidade o = (Habilidade) event.getObject();                
        
        try{
//            if(o.getDataCadastro() == null){
//                o.setDataCadastro(new Date());
//            }
            habilidadeService.saveHabilidade(o);        
            messages.info("Dados salvos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível salvar os dados.");
            ex.printStackTrace();
        } 
        
    }
    
    //GETTERS AND SETTERS
    public HabilidadeService getHabilidadeService() {
        return habilidadeService;
    }

    public void setHabilidadeService(HabilidadeService habilidadeService) {
        this.habilidadeService = habilidadeService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Habilidade getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(Habilidade habilidade) {
        this.habilidade = habilidade;
    }

    public List<Habilidade> getListaHabilidades() {
        if(listaHabilidades==null){
            listaHabilidades = habilidadeService.findAllHabilidade();
        }
        return listaHabilidades;
    }

    public void setListaHabilidades(List<Habilidade> listaHabilidades) {
        this.listaHabilidades = listaHabilidades;
    }
}
