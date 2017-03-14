package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.EncontroEquipe;
import br.gov.to.santuario.ejc.domain.EncontroEquipeIntegrante;
import br.gov.to.santuario.ejc.domain.Equipe;
import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.service.EncontroEquipeIntegranteService;
import br.gov.to.santuario.ejc.service.EncontroEquipeService;
import br.gov.to.santuario.ejc.service.EquipeService;
import br.gov.to.santuario.ejc.service.SeguidorService;
import br.gov.to.santuario.seg.util.FacesMessages;
import br.gov.to.santuario.seg.util.RelatorioUtil;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class EncontroEquipeController implements Serializable {
    @ManagedProperty(value = "#{encontroEquipeService}")
    private EncontroEquipeService encontroEquipeService;
    
    @ManagedProperty(value = "#{equipeService}")
    private EquipeService equipeService;
    
    @ManagedProperty(value = "#{seguidorService}")
    private SeguidorService seguidorService;
    
    @ManagedProperty(value = "#{encontroEquipeIntegranteService}")
    private EncontroEquipeIntegranteService encontroEquipeIntegranteService;    
    
    @ManagedProperty(value = "#{dataSource}")
    private DataSource dataSource;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private EncontroEquipe encontroEquipe = new EncontroEquipe();
    private List<Equipe> listaEquipe;    
    private List<Seguidor> listaSeguidores; 
    private EncontroEquipeIntegrante encontroEquipeIntegrante = new EncontroEquipeIntegrante();    
    private List<EncontroEquipeIntegrante> listaEncontroEquipeIntegrante;
    
    public void salvar(){        
        try{            
            encontroEquipeService.saveEncontroEquipe(encontroEquipe);
            messages.info("Integrantes da equipe salvo com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível salvar o encontroEquipe.");
            e.printStackTrace();
        }
    }
    
    public void deletar(){
        try{            
            encontroEquipeService.deleteEncontroEquipe(encontroEquipe);
            messages.info("EncontroEquipe deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o encontroEquipe.");
            e.printStackTrace();
        }
    }
    
    public void loadModel(){
        if(id != null){
            encontroEquipe = encontroEquipeService.findOneEncontroEquipe(id);
            if(encontroEquipe == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
            listaEncontroEquipeIntegrante = encontroEquipeIntegranteService.findSeguidoresDaEquipe(encontroEquipe);
        }
    }
    
    public void selecionarIntegrante(){
        try{            
            encontroEquipeService.saveEncontroEquipe(encontroEquipe);  
            this.loadModel();
        }catch(Exception e){
            messages.error("Não foi possível salvar o(s) Integrante(s).");
            e.printStackTrace();
        }
    }
    
    public void selecionarCoordenador(EncontroEquipeIntegrante encontroEquipeIntegrantes){
        try{
            encontroEquipeIntegranteService.saveEncontroEquipeIntegrante(encontroEquipeIntegrantes);
        }catch(Exception e){
            messages.error("Não foi possível salvar o(a) coordenador(a).");
            e.printStackTrace();
        }
    }
    
    public void conviteAceito(EncontroEquipeIntegrante encontroEquipeIntegrantes){
        try{
            encontroEquipeIntegranteService.saveEncontroEquipeIntegrante(encontroEquipeIntegrantes);
        }catch(Exception e){
            messages.error("Não foi possível salvar o(a) coordenador(a).");
            e.printStackTrace();
        }
    }
    
    public void salvarEncontroEquipeIntegrante(){
        try{
            encontroEquipeIntegranteService.saveEncontroEquipeIntegrante(encontroEquipeIntegrante);
            this.loadModel();
        }catch(Exception e){
            messages.error("Não foi possível salvar o(a) coordenador(a).");
            e.printStackTrace();
        }
    }
    
    public void imprimir() throws IOException, SQLException, JRException{
        
        try{
            RelatorioUtil relatorio = new RelatorioUtil();
            ArrayList<Object> parametro = new ArrayList<>();
            parametro.add("R_ID");
            parametro.add(encontroEquipe.getId());
            
            relatorio.imprimeRelatorio(relatorio.DIR + "/equipe.jasper", parametro, dataSource.getConnection(), "Segue-me - "+encontroEquipe.getEquipe().getDescricao());  
  
        }catch(IOException | SQLException ex){
            messages.error("Não foi possível imprimir o documento!");
            System.err.println("Erro ao gerar relatório! " + ex.getMessage());
        }
    }
    
    //GETTERS AND SETTERS
    public EncontroEquipeService getEncontroEquipeService() {
        return encontroEquipeService;
    }

    public void setEncontroEquipeService(EncontroEquipeService encontroEquipeService) {
        this.encontroEquipeService = encontroEquipeService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public EncontroEquipe getEncontroEquipe() {
        return encontroEquipe;
    }

    public void setEncontroEquipe(EncontroEquipe encontroEquipe) {
        this.encontroEquipe = encontroEquipe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EquipeService getEquipeService() {
        return equipeService;
    }

    public void setEquipeService(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    public List<Equipe> getListaEquipe() {
        if(listaEquipe == null){
            listaEquipe = equipeService.findAllEquipe();
        }
        return listaEquipe;
    }

    public void setListaEquipe(List<Equipe> listaEquipe) {
        this.listaEquipe = listaEquipe;
    }

    public SeguidorService getSeguidorService() {
        return seguidorService;
    }

    public void setSeguidorService(SeguidorService seguidorService) {
        this.seguidorService = seguidorService;
    }

    public List<Seguidor> getListaSeguidores() {
        if(encontroEquipe != null && listaSeguidores == null){
            listaSeguidores = seguidorService.findSeguidoresDisponiveis(encontroEquipe);
        }
        return listaSeguidores;
    }

    public void setListaSeguidores(List<Seguidor> listaSeguidores) {
        this.listaSeguidores = listaSeguidores;
    }

    public EncontroEquipeIntegranteService getEncontroEquipeIntegranteService() {
        return encontroEquipeIntegranteService;
    }

    public void setEncontroEquipeIntegranteService(EncontroEquipeIntegranteService encontroEquipeIntegranteService) {
        this.encontroEquipeIntegranteService = encontroEquipeIntegranteService;
    }

    public EncontroEquipeIntegrante getEncontroEquipeIntegrante() {
        return encontroEquipeIntegrante;
    }

    public void setEncontroEquipeIntegrante(EncontroEquipeIntegrante encontroEquipeIntegrante) {
        this.encontroEquipeIntegrante = encontroEquipeIntegrante;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<EncontroEquipeIntegrante> getListaEncontroEquipeIntegrante() {
        return listaEncontroEquipeIntegrante;
    }

    public void setListaEncontroEquipeIntegrante(List<EncontroEquipeIntegrante> listaEncontroEquipeIntegrante) {
        this.listaEncontroEquipeIntegrante = listaEncontroEquipeIntegrante;
    }

}
