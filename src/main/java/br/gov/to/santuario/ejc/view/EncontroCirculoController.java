package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.EncontroCirculo;
import br.gov.to.santuario.ejc.domain.EncontroCirculoSeguimista;
import br.gov.to.santuario.ejc.domain.EncontroEquipeIntegrante;
import br.gov.to.santuario.ejc.domain.Equipe;
import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.domain.Seguimista;
import br.gov.to.santuario.ejc.service.EncontroCirculoSeguimistaService;
import br.gov.to.santuario.ejc.service.EncontroCirculoService;
import br.gov.to.santuario.ejc.service.EquipeService;
import br.gov.to.santuario.ejc.service.SeguidorService;
import br.gov.to.santuario.ejc.service.SeguimistaService;
import br.gov.to.santuario.seg.domain.Participante;
import br.gov.to.santuario.seg.util.FacesMessages;
import br.gov.to.santuario.seg.util.ListaAnos;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@ViewScoped
public class EncontroCirculoController implements Serializable {
    @ManagedProperty(value = "#{encontroCirculoService}")
    private EncontroCirculoService encontroCirculoService;    
    
    @ManagedProperty(value = "#{equipeService}")
    private EquipeService equipeService;
    
    @ManagedProperty(value = "#{seguidorService}")
    private SeguidorService seguidorService;
    
    @ManagedProperty(value = "#{seguimistaService}")
    private SeguimistaService seguimistaService;
    
    @ManagedProperty(value = "#{encontroCirculoSeguimistaService}")
    private EncontroCirculoSeguimistaService encontroCirculoSeguimistaService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer id;
    private EncontroCirculo encontroCirculo;    
    private List<EncontroCirculo> listaEncontroCirculo = new ArrayList<>();
    private List<Equipe> listaEquipe;    
    private List<Seguimista> listaSeguimista; 
    private List<Seguidor> listaSeguidores;
    private List<Seguidor> listaSeguidoresPadrinhos;
    private List<Seguidor> listaSeguidoresMadrinhas;
    private List<Seguidor> listaSeguidoresTios;
    private List<Seguidor> listaSeguidoresTias;
    private Seguidor padrinho;
    private Seguidor madrinha;
    private Seguidor tio;
    private Seguidor tia;
    private EncontroCirculo encontroCirculoSelecionado = new EncontroCirculo();
    private EncontroCirculoSeguimista encontroCirculoSeguimista;    
    
    private StreamedContent file;
    private StreamedContent fileTodosCirculos;
    
    public void deletar(){
        try{            
            encontroCirculoService.deleteEncontroCirculo(encontroCirculo);
            messages.info("EncontroCirculo deletado com sucesso!");
        }catch(Exception e){
            messages.error("Não foi possível deletar o encontroCirculo.");
            e.printStackTrace();
        }
    }
    
    public void loadModelEncontro(){
        if(id != null){
            listaEncontroCirculo = encontroCirculoService.findAllEncontroCirculoByEncontro(id);                
        }
    }
    
    public void loadModel(){
        if(id != null){
            encontroCirculo = encontroCirculoService.findOneEncontroCirculo(id);            
            if(encontroCirculo == null){
                messages.info("O registro que você está tentando acessar não existe.");
                return;
            }              
        }
    }
    
    public String gotoEdit(Integer ide){
        return "/segue-me/estrutura/circulos/edit.xhtlm?id="+ide+"&faces-redirect=true";
    }
    
    public void selecionarIntegrante(){
        try{              
            if(padrinho != null){
                encontroCirculo.setSeguidorPadrinho(padrinho);
                padrinho = null;
            }
            if(madrinha != null){
                encontroCirculo.setSeguidorMadrinha(madrinha);
                madrinha = null;
            }
            if(tio != null){
                encontroCirculo.setSeguidorTio(tio);
                tio = null;
            }
            if(tia != null){
                encontroCirculo.setSeguidorTia(tia);
                tia = null;
            }
            encontroCirculoService.saveEncontroCirculo(encontroCirculo);  
            this.loadModel();
        }catch(Exception e){
            messages.error("Não foi possível salvar o(s) Integrante(s).");
            e.printStackTrace();
        }
    }
       
    public void salvarEncontroCirculoSeguimista(){
        try{
            encontroCirculoSeguimistaService.saveEncontroCirculoSeguimista(encontroCirculoSeguimista);
        }catch(Exception e){
            messages.error("Não foi possível salvar o(a) coordenador(a).");
            e.printStackTrace();
        }
    }
    
    public void selecionarCirculo(EncontroCirculo ec){        
        encontroCirculo = ec;
        encontroCirculoSelecionado = ec;
        padrinho = ec.getSeguidorPadrinho();
        madrinha = ec.getSeguidorMadrinha();
        tio = ec.getSeguidorTio();
        tia = ec.getSeguidorTia();
        getListaSeguidoresPadrinhos();
        getListaSeguidoresMadrinhas();
    }
    
    
    public String retornaTelefone(Participante p){
        
        ArrayList<String> str = new ArrayList<>();
        String telefones = "";
        
        if(p.getTelefoneCelular() != null && !p.getTelefoneCelular().equals("")){
            str.add(p.getTelefoneCelular());
        }
        
        if(p.getTelefoneResidencial() != null && !p.getTelefoneResidencial().equals("")){
            str.add(p.getTelefoneResidencial());
        }
        
        telefones = StringUtils.join(str, "/");
        
        
        return telefones;
    }
    
    public String getRealPath(String diretorio) {
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return sc.getRealPath(diretorio);
    }
    
    public void baixarCSV(){
        System.out.println("1");
        try{
            // local do arquivo
            String DIR = "/upload";
            String filename=getRealPath(DIR)+"/Círculos - "+encontroCirculo.getCirculo().getCor()+" - "+encontroCirculo.getCirculo().getApostolo()+".xls" ;
            HSSFWorkbook workbook=new HSSFWorkbook();
            HSSFSheet sheet =  workbook.createSheet(encontroCirculo.getCirculo().getCor()+" - "+encontroCirculo.getCirculo().getApostolo());  
            
            System.out.println("2");
            
            // criando as linhas
            HSSFRow rowhead=   sheet.createRow((short)0);
            rowhead.createCell(0).setCellValue("Nome");            
            rowhead.createCell(1).setCellValue("Telefones");
            rowhead.createCell(2).setCellValue("Endereço");
            rowhead.createCell(3).setCellValue("E-mail");
            rowhead.createCell(4).setCellValue("Data de Nascimento");
            System.out.println("3");
            int col = 1;
            for(Seguimista i : encontroCirculo.getSeguimistaList()){
                HSSFRow row = sheet.createRow((short)col);

                row.createCell(0).setCellValue(i.getParticipante().getNome());
                row.createCell(1).setCellValue(this.retornaTelefone(i.getParticipante()));
                if(i.getParticipante().getEndereco() == null){
                    row.createCell(2).setCellValue("");
                }else{
                   row.createCell(2).setCellValue(i.getParticipante().getEndereco());
                }
                if(i.getParticipante().getEmail() == null){
                    row.createCell(3).setCellValue("");
                }else{
                   row.createCell(3).setCellValue(i.getParticipante().getEmail());
                }
                row.createCell(4).setCellValue(ListaAnos.getStringFromDate(i.getParticipante().getDataNascimento()));
                col++;
            }
            System.out.println("4");
            FileOutputStream fileOut =  new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            
            InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/upload/Círculo - "+encontroCirculo.getCirculo().getCor()+" - "+encontroCirculo.getCirculo().getApostolo()+".xls");
            file = new DefaultStreamedContent(stream, "application/xls", "Círculo - "+encontroCirculo.getCirculo().getCor()+" - "+encontroCirculo.getCirculo().getApostolo()+".xls");
            
            System.out.println("Seu arquivo excel foi gerado!");

        } catch ( Exception ex ) {
            System.out.println(ex);
        }
    }
    
    public void baixarCSVTodosCirculos(){
        if(listaEncontroCirculo.isEmpty()){
            System.out.println("LISTA VAZIA");
            return;
        }
        try{
            // local do arquivo
            String DIR = "/upload";
            String filename=getRealPath(DIR)+"/Círculos - "+listaEncontroCirculo.get(0).getEncontro().getDescricao() + " - " + listaEncontroCirculo.get(0).getEncontro().getParoquia().getDescricao()+".xls";
            HSSFWorkbook workbook=new HSSFWorkbook();
            
            for(EncontroCirculo ec : listaEncontroCirculo){
                List<EncontroCirculoSeguimista> listEncontroCirculoSeguimistaTemp = encontroCirculoSeguimistaService.findSeguimistasCirculo(ec);
                
                HSSFSheet sheet =  workbook.createSheet(ec.getCirculo().getCor()+" - "+ec.getCirculo().getApostolo());

                // criando as linhas
                HSSFRow rowhead=   sheet.createRow((short)0);
                rowhead.createCell(0).setCellValue("Nome");            
                rowhead.createCell(1).setCellValue("Telefones");
                rowhead.createCell(2).setCellValue("Endereço");
                rowhead.createCell(3).setCellValue("E-mail");
                rowhead.createCell(4).setCellValue("Data de Nascimento");

                int col = 1;
                for(EncontroCirculoSeguimista i : listEncontroCirculoSeguimistaTemp){
                    HSSFRow row = sheet.createRow((short)col);

                    row.createCell(0).setCellValue(i.getSeguimista().getParticipante().getNome());
                    row.createCell(1).setCellValue(this.retornaTelefone(i.getSeguimista().getParticipante()));
                    if(i.getSeguimista().getParticipante().getEndereco() == null){
                        row.createCell(2).setCellValue("");
                    }else{
                       row.createCell(2).setCellValue(i.getSeguimista().getParticipante().getEndereco());
                    }
                    if(i.getSeguimista().getParticipante().getEmail() == null){
                        row.createCell(3).setCellValue("");
                    }else{
                       row.createCell(3).setCellValue(i.getSeguimista().getParticipante().getEmail());
                    }
                    row.createCell(4).setCellValue(ListaAnos.getStringFromDate(i.getSeguimista().getParticipante().getDataNascimento()));
                    col++;
                }
            }
            FileOutputStream fileOut =  new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            
            InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/upload/Círculos - "+listaEncontroCirculo.get(0).getEncontro().getDescricao() + " - " + listaEncontroCirculo.get(0).getEncontro().getParoquia().getDescricao()+".xls");
            file = new DefaultStreamedContent(stream, "application/xls", "Círculos - "+listaEncontroCirculo.get(0).getEncontro().getDescricao() + " - " + listaEncontroCirculo.get(0).getEncontro().getParoquia().getDescricao()+".xls");
            
            //System.out.println("Seu arquivo excel foi gerado!");

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
    
    //GETTERS AND SETTERS
    public EncontroCirculoService getEncontroCirculoService() {
        return encontroCirculoService;
    }

    public void setEncontroCirculoService(EncontroCirculoService encontroCirculoService) {
        this.encontroCirculoService = encontroCirculoService;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }

    public EncontroCirculo getEncontroCirculo() {
        return encontroCirculo;
    }

    public void setEncontroCirculo(EncontroCirculo encontroCirculo) {
        this.encontroCirculo = encontroCirculo;
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
        if(encontroCirculo != null){
            listaSeguidores = seguidorService.findAllSeguidor();//findSeguidoresDisponiveis(encontroCirculo);
        }
        return listaSeguidores;
    }

    public void setListaSeguidores(List<Seguidor> listaSeguidores) {
        this.listaSeguidores = listaSeguidores;
    }

    public EncontroCirculoSeguimistaService getEncontroCirculoSeguimistaService() {
        return encontroCirculoSeguimistaService;
    }

    public void setEncontroCirculoSeguimistaService(EncontroCirculoSeguimistaService encontroCirculoSeguimistaService) {
        this.encontroCirculoSeguimistaService = encontroCirculoSeguimistaService;
    }

    public EncontroCirculoSeguimista getEncontroCirculoSeguimista() {
        return encontroCirculoSeguimista;
    }

    public void setEncontroCirculoSeguimista(EncontroCirculoSeguimista encontroCirculoSeguimista) {
        this.encontroCirculoSeguimista = encontroCirculoSeguimista;
    }

    public SeguimistaService getSeguimistaService() {
        return seguimistaService;
    }

    public void setSeguimistaService(SeguimistaService seguimistaService) {
        this.seguimistaService = seguimistaService;
    }

    public List<Seguimista> getListaSeguimista() {
        if(encontroCirculo != null){
            listaSeguimista = seguimistaService.findSeguimistasDisponiveis(encontroCirculo);//findSeguidoresDisponiveis(encontroCirculo);
        }
        return listaSeguimista;
    }

    public void setListaSeguimista(List<Seguimista> listaSeguimista) {
        this.listaSeguimista = listaSeguimista;
    }

    public List<Seguidor> getListaSeguidoresPadrinhos() {                 
        if(encontroCirculo != null){               
            listaSeguidoresPadrinhos = seguidorService.findSeguidoresPadrinhosDisponiveis(encontroCirculo, "M", false);
        }
        return listaSeguidoresPadrinhos;
    }

    public void setListaSeguidoresPadrinhos(List<Seguidor> listaSeguidoresPadrinhos) {
        this.listaSeguidoresPadrinhos = listaSeguidoresPadrinhos;
    }

    public List<Seguidor> getListaSeguidoresMadrinhas() {
        if(encontroCirculo != null){
            listaSeguidoresMadrinhas = seguidorService.findSeguidoresPadrinhosDisponiveis(encontroCirculo, "F", false);
        }
        return listaSeguidoresMadrinhas;
    }

    public void setListaSeguidoresMadrinhas(List<Seguidor> listaSeguidoresMadrinhas) {        
        this.listaSeguidoresMadrinhas = listaSeguidoresMadrinhas;
    }

    public List<Seguidor> getListaSeguidoresTios() {
        if(encontroCirculo != null){               
            listaSeguidoresTios = seguidorService.findSeguidoresPadrinhosDisponiveis(encontroCirculo, "M", true);
        }
        return listaSeguidoresTios;
    }

    public void setListaSeguidoresTios(List<Seguidor> listaSeguidoresTios) {
        this.listaSeguidoresTios = listaSeguidoresTios;
    }

    public List<Seguidor> getListaSeguidoresTias() {
        if(encontroCirculo != null){
            listaSeguidoresTias = seguidorService.findSeguidoresPadrinhosDisponiveis(encontroCirculo, "F", true);
        }
        return listaSeguidoresTias;
    }

    public void setListaSeguidoresTias(List<Seguidor> listaSeguidoresTias) {
        this.listaSeguidoresTias = listaSeguidoresTias;
    }
    
    public Seguidor getPadrinho() {
        return padrinho;
    }

    public void setPadrinho(Seguidor padrinho) {
        this.padrinho = padrinho;
    }

    public Seguidor getMadrinha() {
        return madrinha;
    }

    public void setMadrinha(Seguidor madrinha) {
        this.madrinha = madrinha;
    }

    public List<EncontroCirculo> getListaEncontroCirculo() {
        return listaEncontroCirculo;
    }

    public void setListaEncontroCirculo(List<EncontroCirculo> listaEncontroCirculo) {
        this.listaEncontroCirculo = listaEncontroCirculo;
    }

    public EncontroCirculo getEncontroCirculoSelecionado() {
        return encontroCirculoSelecionado;
    }

    public void setEncontroCirculoSelecionado(EncontroCirculo encontroCirculoSelecionado) {
        this.encontroCirculoSelecionado = encontroCirculoSelecionado;
    }

    public Seguidor getTio() {
        return tio;
    }

    public void setTio(Seguidor tio) {
        this.tio = tio;
    }

    public Seguidor getTia() {
        return tia;
    }

    public void setTia(Seguidor tia) {
        this.tia = tia;
    }

    public StreamedContent getFile() {
        this.baixarCSV();
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public StreamedContent getFileTodosCirculos() {
        this.baixarCSVTodosCirculos();
        return fileTodosCirculos;
    }

    public void setFileTodosCirculos(StreamedContent fileTodosCirculos) {
        this.fileTodosCirculos = fileTodosCirculos;
    }

}
