package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Funcao;
import br.gov.to.santuario.ejc.service.FuncaoService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class FuncaoController implements Serializable {
    @ManagedProperty(value = "#{funcaoService}")
    private FuncaoService funcaoService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Funcao funcao = new Funcao();
    private List<Funcao> listaFuncoes;    

    public void novo(){
        funcao = new Funcao(); 
        funcao.setDescricao("Insira aqui a descrição");
        listaFuncoes.add(0, funcao);
    }
    
    public void deletar(){
        try{
            funcaoService.deleteFuncao(funcao);
            listaFuncoes = null;
            messages.info("Dados excluídos com sucesso!!!");
        }catch(Exception ex){
            messages.error("Não foi possível excluir os dados.");
        }     
    }
    
    public void onCellEdit(CellEditEvent event) {
        DataTable s = (DataTable) event.getSource();
        Funcao obj = (Funcao) s.getRowData();
        
        try{
            funcaoService.saveFuncao(obj);            
        }catch(Exception e){
            messages.error("Erro ao salvar os dados!");
            e.printStackTrace();
        } 
    }
    
    //GETTERS AND SETTERS
    public FuncaoService getFuncaoService() {
        return funcaoService;
    }

    public void setFuncaoService(FuncaoService funcaoService) {
        this.funcaoService = funcaoService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public List<Funcao> getListaFuncoes() {
        if(listaFuncoes==null){
            listaFuncoes = funcaoService.findAllFuncao();
        }
        return listaFuncoes;
    }

    public void setListaFuncoes(List<Funcao> listaFuncoes) {
        this.listaFuncoes = listaFuncoes;
    }    
    
}
