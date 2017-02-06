package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Equipe;
import br.gov.to.santuario.ejc.service.EquipeService;
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
public class EquipeController implements Serializable {
    @ManagedProperty(value = "#{equipeService}")
    private EquipeService equipeService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Equipe equipe = new Equipe();
    private List<Equipe> listaEquipes;

    public void novo(){
        equipe = new Equipe(); 
        equipe.setDescricao("Insira a descrição");        
        listaEquipes.add(0, equipe);
    }
    
    public void deletar(){
        try{
            equipeService.deleteEquipe(equipe);
            listaEquipes = null;
            messages.info("Dados excluídos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível excluir os dados.");
        }     
    }
    
    public void onRowEdit(RowEditEvent event) {
        Equipe o = (Equipe) event.getObject();                
        
        try{
//            if(o.getDataCadastro() == null){
//                o.setDataCadastro(new Date());
//            }
            equipeService.saveEquipe(o);        
            messages.info("Dados salvos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível salvar os dados.");
            ex.printStackTrace();
        } 
        
    }
    
    //GETTERS AND SETTERS
    public EquipeService getEquipeService() {
        return equipeService;
    }

    public void setEquipeService(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public List<Equipe> getListaEquipes() {
        if(listaEquipes==null){
            listaEquipes = equipeService.findAllEquipe();
        }
        return listaEquipes;
    }

    public void setListaEquipes(List<Equipe> listaEquipes) {
        this.listaEquipes = listaEquipes;
    }
}
