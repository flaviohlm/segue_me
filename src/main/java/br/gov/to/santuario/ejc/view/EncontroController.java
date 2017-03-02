package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Circulo;
import br.gov.to.santuario.ejc.domain.Conselho;
import br.gov.to.santuario.ejc.domain.Encontro;
import br.gov.to.santuario.ejc.domain.EncontroCirculo;
import br.gov.to.santuario.ejc.domain.EncontroEquipe;
import br.gov.to.santuario.ejc.domain.EncontroEquipeIntegrante;
import br.gov.to.santuario.ejc.domain.Equipe;
import br.gov.to.santuario.ejc.domain.EquipeDirigente;
import br.gov.to.santuario.ejc.domain.Palestra;
import br.gov.to.santuario.ejc.domain.Paroquia;
import br.gov.to.santuario.ejc.service.CirculoService;
import br.gov.to.santuario.ejc.service.ConselhoService;
import br.gov.to.santuario.ejc.service.EncontroEquipeIntegranteService;
import br.gov.to.santuario.ejc.service.EncontroEquipeService;
import br.gov.to.santuario.ejc.service.EncontroService;
import br.gov.to.santuario.ejc.service.EquipeDirigenteService;
import br.gov.to.santuario.ejc.service.EquipeService;
import br.gov.to.santuario.ejc.service.PalestraService;
import br.gov.to.santuario.ejc.service.ParoquiaService;
import br.gov.to.santuario.seg.util.FacesMessages;
import br.gov.to.santuario.seg.util.RelatorioUtil;
import br.gov.to.santuario.seg.util.UsuarioUtil;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class EncontroController implements Serializable {
    @ManagedProperty(value = "#{encontroService}")
    private EncontroService encontroService;
    
    @ManagedProperty(value = "#{paroquiaService}")
    private ParoquiaService paroquiaService;
    
    @ManagedProperty(value = "#{circuloService}")
    private CirculoService circuloService;
    
    @ManagedProperty(value = "#{equipeService}")
    private EquipeService equipeService;
    
    @ManagedProperty(value = "#{palestraService}")
    private PalestraService palestraService;
    
    @ManagedProperty(value = "#{encontroEquipeService}")
    private EncontroEquipeService encontroEquipeService;
    
    @ManagedProperty(value = "#{encontroEquipeIntegranteService}")
    private EncontroEquipeIntegranteService encontroEquipeIntegranteService;
    
    @ManagedProperty(value = "#{equipeDirigenteService}")
    private EquipeDirigenteService equipeDirigenteService;
    
    @ManagedProperty(value = "#{conselhoService}")
    private ConselhoService conselhoService;
    
    @ManagedProperty(value = "#{dataSource}")
    private DataSource dataSource;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private Encontro encontro = new Encontro();
    private List<Encontro> listaEncontros;
    
    private List<Paroquia> listaParoquia;
    private List<Circulo> listaCirculo;
    private List<Equipe> listaEquipe;
    private List<Palestra> listaPalestra;
    private List<EncontroEquipe> listaEncontroEquipe;
    private EncontroEquipe encontroEquipeSelecionado;
    private EncontroCirculo encontroCirculoSelecionado;
    private List<EncontroEquipeIntegrante> listaIntegrantes;
    private LazyDataModel<EncontroEquipeIntegrante> listaObjetosLazy;
    private List<EquipeDirigente> listaEquipeDirigente;
    private List<Conselho> listaConselho;
    
    @PostConstruct
    public void init() {        
        // Classe interna anônima: ao mesmo tempo em que está instanciando, muda o comportamento interno da classe LazyDataModel (herança>>polimorfismo)        
        listaObjetosLazy = new LazyDataModel<EncontroEquipeIntegrante>() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public List<EncontroEquipeIntegrante> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                String globalFilter = removeAcentos((String) filters.get("globalFilter"));
                
                //Variavel de retorno
                List<EncontroEquipeIntegrante> result;
                //Seta o tamanho da pagina
                this.setPageSize(pageSize);

                if (sortOrder == SortOrder.UNSORTED || StringUtils.isBlank(sortField)) {
                    //Atribui para o request em qual pagina vai e o tamanho da pagina                    
                    PageRequest request = new PageRequest(first / pageSize, pageSize);
                    //Faz a consulta no banco passando o request e o os filtros para o service
                    Page<EncontroEquipeIntegrante> page = encontroEquipeIntegranteService.findAll(request, globalFilter);
                    //Atribui a quantidade de registro total
                    this.setRowCount((int) page.getTotalElements());
                    // Pega a lista de registro que irá mostrar na pagina
                    result = page.getContent();
                } else {
                    Sort sort = new Sort(sortOrder == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

                    PageRequest request = new PageRequest(first / pageSize, pageSize, sort);
                    Page<EncontroEquipeIntegrante> page = encontroEquipeIntegranteService.findAll(request, globalFilter);
                    this.setRowCount((int) page.getTotalElements());
                    result = page.getContent();
                }
                // retorna a lista com os registro
                return result;
            }

            @Override
            public Object getRowKey(EncontroEquipeIntegrante object) {
                return object.getId().toString();
            }

            @Override
            public EncontroEquipeIntegrante getRowData(String rowKey) {
                return encontroEquipeIntegranteService.findOneEncontroEquipeIntegrante(Integer.parseInt(rowKey));
            }
        };
               
    }
    
    public String salvar(){
        try{            
            encontroService.saveEncontro(encontro);
            messages.info("Encontro salvo com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível salvar o encontro.");
            e.printStackTrace();
        }
        return "/segue-me/estrutura/encontro/index.xhtml?faces-redirect=true";
    }
    
    public void deletar(){
        try{            
            encontroService.deleteEncontro(encontro);
            messages.info("Encontro deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o encontro.");
            e.printStackTrace();
        }
    }
    
    public String gotoEdit(){
        return "/segue-me/estrutura/encontro/cadastrar/index.xhtml?faces-redirect=true";
    }
    
    public String gotoEdit(Integer ide){
        return "/segue-me/estrutura/encontro/editar/index.xhtml?id="+ide+"&faces-redirect=true";
    }
    
    public String gotoEquipes(Integer ide){
        return "/segue-me/estrutura/encontro/equipes/index.xhtml?id="+ide+"&faces-redirect=true";
    }
    
    public String gotoCirculos(Integer ide){
        return "/segue-me/estrutura/encontro/circulos/index.xhtml?id="+ide+"&faces-redirect=true";
    }
    
    public String gotoPalestras(Integer ide){
        return "/segue-me/estrutura/encontro/palestras/index.xhtml?id="+ide+"&faces-redirect=true";
    }
    
    public String gotoIntegrantes(Integer ide){
        return "/segue-me/estrutura/encontro/equipes/integrantes/index.xhtml?id="+ide+"&faces-redirect=true";
    }
    
    public void loadModel(){
        if(id != null){
            encontro = encontroService.findOneEncontro(id);
            if(encontro == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }
            listaEncontroEquipe = encontroEquipeService.findAllByEncontro(encontro);
        }
    }
    
    public void imprimir(Integer id, String pasta, String relatorioJasper) throws IOException, SQLException, JRException{        
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
    
    public void selecionarEquipe(SelectEvent event) throws IOException {                
        FacesContext.getCurrentInstance().getExternalContext().redirect("/segueme/segue-me/estrutura/encontro/equipes/editar/index.xhtml?id=" + encontroEquipeSelecionado.getId());
    }
    
    public void selecionarCirculo(SelectEvent event) throws IOException {                
        FacesContext.getCurrentInstance().getExternalContext().redirect("/segueme/segue-me/estrutura/encontro/circulos/editar/index.xhtml?id=" + encontroCirculoSelecionado.getId());
    }
    
    public void onCellEdit(CellEditEvent event) {
        DataTable s = (DataTable) event.getSource();
        EncontroEquipeIntegrante eei = (EncontroEquipeIntegrante) s.getRowData();
        
        try{
            encontroEquipeIntegranteService.saveEncontroEquipeIntegrante(eei);
            //messages.info("Dados salvos com sucesso!");
        }catch(Exception e){
            messages.error("Erro ao salvar os dados!");
            e.printStackTrace();
        } 
    }
    
    public void finalizarEncontro(){
        try{
            encontro.setFinalizado(!encontro.getFinalizado());
            encontroService.saveEncontro(encontro);
            if(encontro.getFinalizado()){
                messages.info("Encontro finalizado com sucesso!");
            }else{
                messages.info("Encontro reativado com sucesso!");
            }
        }catch(Exception e){
            messages.error("Não foi possível salvar os dados.");
            e.printStackTrace();
        }
    }
    
    public String removeAcentos(String s) {
        if (s == null) {
            return s;
        }
        String semAcentos = s.toLowerCase();
        semAcentos = semAcentos.replaceAll("[áàâãäa]", "_");
        semAcentos = semAcentos.replaceAll("[éèêëe]", "_");
        semAcentos = semAcentos.replaceAll("[íìîïi]", "_");
        semAcentos = semAcentos.replaceAll("[óòôõöo]", "_");
        semAcentos = semAcentos.replaceAll("[úùûüu]", "_");
        semAcentos = semAcentos.replaceAll("çc", "_");
        semAcentos = semAcentos.replaceAll("ñn", "_");
        return semAcentos;
    }
    
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

    public Encontro getEncontro() {
        return encontro;
    }

    public void setEncontro(Encontro encontro) {
        this.encontro = encontro;
    }

    public List<Encontro> getListaEncontros() {
        if(listaEncontros == null){
            listaEncontros = encontroService.findAllEncontro();
        }
        return listaEncontros = encontroService.findAllEncontro();
    }

    public void setListaEncontros(List<Encontro> listaEncontros) {
        this.listaEncontros = listaEncontros;
    }     

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ParoquiaService getParoquiaService() {
        return paroquiaService;
    }

    public void setParoquiaService(ParoquiaService paroquiaService) {
        this.paroquiaService = paroquiaService;
    }

    public List<Paroquia> getListaParoquia() {
        if(listaParoquia == null){
            listaParoquia = paroquiaService.findAllParoquia();
        }
        return listaParoquia;
    }

    public void setListaParoquia(List<Paroquia> listaParoquia) {
        this.listaParoquia = listaParoquia;
    }
 
    public CirculoService getCirculoService() {
        return circuloService;
    }

    public void setCirculoService(CirculoService circuloService) {
        this.circuloService = circuloService;
    }

    public EquipeService getEquipeService() {
        return equipeService;
    }

    public void setEquipeService(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    public PalestraService getPalestraService() {
        return palestraService;
    }

    public void setPalestraService(PalestraService palestraService) {
        this.palestraService = palestraService;
    }

    public List<Circulo> getListaCirculo() {
        if(listaCirculo == null){
            listaCirculo = circuloService.findAllCirculo();
        }
        return listaCirculo;
    }

    public void setListaCirculo(List<Circulo> listaCirculo) {
        this.listaCirculo = listaCirculo;
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

    public List<Palestra> getListaPalestra() {
        if(listaPalestra == null){
            listaPalestra = palestraService.findAllPalestras();
        }
        return listaPalestra;
    }

    public void setListaPalestra(List<Palestra> listaPalestra) {
        this.listaPalestra = listaPalestra;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public EncontroEquipe getEncontroEquipeSelecionado() {
        return encontroEquipeSelecionado;
    }

    public void setEncontroEquipeSelecionado(EncontroEquipe encontroEquipeSelecionado) {
        this.encontroEquipeSelecionado = encontroEquipeSelecionado;
    }

    public EncontroCirculo getEncontroCirculoSelecionado() {
        return encontroCirculoSelecionado;
    }

    public void setEncontroCirculoSelecionado(EncontroCirculo encontroCirculoSelecionado) {
        this.encontroCirculoSelecionado = encontroCirculoSelecionado;
    }

    public EncontroEquipeService getEncontroEquipeService() {
        return encontroEquipeService;
    }

    public void setEncontroEquipeService(EncontroEquipeService encontroEquipeService) {
        this.encontroEquipeService = encontroEquipeService;
    }

    public List<EncontroEquipe> getListaEncontroEquipe() {
        return listaEncontroEquipe;
    }

    public void setListaEncontroEquipe(List<EncontroEquipe> listaEncontroEquipe) {
        this.listaEncontroEquipe = listaEncontroEquipe;
    }

    public EncontroEquipeIntegranteService getEncontroEquipeIntegranteService() {
        return encontroEquipeIntegranteService;
    }

    public void setEncontroEquipeIntegranteService(EncontroEquipeIntegranteService encontroEquipeIntegranteService) {
        this.encontroEquipeIntegranteService = encontroEquipeIntegranteService;
    }

    public List<EncontroEquipeIntegrante> getListaIntegrantes() {
        if(listaIntegrantes == null){
            listaIntegrantes = encontroEquipeIntegranteService.findAllByEncontro(encontro);
        }
        return listaIntegrantes;
    }

    public void setListaIntegrantes(List<EncontroEquipeIntegrante> listaIntegrantes) {
        this.listaIntegrantes = listaIntegrantes;
    }

    public EquipeDirigenteService getEquipeDirigenteService() {
        return equipeDirigenteService;
    }

    public void setEquipeDirigenteService(EquipeDirigenteService equipeDirigenteService) {
        this.equipeDirigenteService = equipeDirigenteService;
    }

    public List<EquipeDirigente> getListaEquipeDirigente() {
        if(listaEquipeDirigente == null){            
            listaEquipeDirigente = equipeDirigenteService.findAllEquipeDirigenteAtiva(UsuarioUtil.getUsuario().getParoquia().getId());
        }
        return listaEquipeDirigente;
    }

    public void setListaEquipeDirigente(List<EquipeDirigente> listaEquipeDirigente) {
        this.listaEquipeDirigente = listaEquipeDirigente;
    }

    public ConselhoService getConselhoService() {
        return conselhoService;
    }

    public void setConselhoService(ConselhoService conselhoService) {
        this.conselhoService = conselhoService;
    }

    public List<Conselho> getListaConselho() {
        if(listaConselho == null){            
            listaConselho = conselhoService.findAllConselhoAtiva();
        }
        return listaConselho;
    }

    public void setListaConselho(List<Conselho> listaConselho) {
        this.listaConselho = listaConselho;
    }

    public LazyDataModel<EncontroEquipeIntegrante> getListaObjetosLazy() {
        return listaObjetosLazy;
    }

    public void setListaObjetosLazy(LazyDataModel<EncontroEquipeIntegrante> listaObjetosLazy) {
        this.listaObjetosLazy = listaObjetosLazy;
    }

}
