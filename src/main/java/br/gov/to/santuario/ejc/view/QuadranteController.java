package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.service.EncontroService;
import br.gov.to.santuario.seg.util.FacesMessages;
import br.gov.to.santuario.seg.util.RelatorioUtil;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class QuadranteController implements Serializable {
    
    @ManagedProperty(value = "#{encontroService}")
    private EncontroService encontroService;
    
    @ManagedProperty(value = "#{dataSource}")
    private DataSource dataSource;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private Encontro encontro = new Encontro();
    
    public void loadModel(){
        if(id != null){
            encontro = encontroService.findOneEncontro(id);
            if(encontro == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
        }
    }
    
    public void imprimir(String pasta, String relatorioJasper) throws IOException, SQLException, JRException{        
        try{
            RelatorioUtil relatorio = new RelatorioUtil();
            ArrayList<Object> parametro = new ArrayList<>();
            parametro.add("R_ID");
            parametro.add(id);
            
            relatorio.imprimeRelatorio(relatorio.DIR + "/"+ pasta + "/" +relatorioJasper+".jasper", parametro, dataSource.getConnection(), ""+relatorioJasper, pasta);  
  
        }catch(IOException | SQLException ex){
            messages.error("Não foi possível imprimir o documento!");
            System.err.println("Erro ao gerar relatório! " + ex.getMessage());
        }
    }
    
    //GETTERS AND SETTERS
    public EncontroService getEncontroService() {
        return encontroService;
    }

    public void setEncontroService(EncontroService encontroService) {
        this.encontroService = encontroService;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
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

    public Encontro getEncontro() {
        return encontro;
    }

    public void setEncontro(Encontro encontro) {
        this.encontro = encontro;
    }
    
    
}
