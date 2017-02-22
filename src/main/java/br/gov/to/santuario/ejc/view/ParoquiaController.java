package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Paroquia;
import br.gov.to.santuario.ejc.service.ParoquiaService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

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
    private StreamedContent graphicImage;
    private Paroquia paroquiaSelecionada;

    public void salvar(){
        try{
            paroquiaService.saveParoquia(paroquia);
            paroquia = new Paroquia();
            messages.info("Dados salvo com sucesso!");
        }catch(Exception e){
            messages.error("Erro ao salvar os dados!");
            e.printStackTrace();
        } 
    }
    
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
    
    public void onCellEdit(CellEditEvent event) {
        DataTable s = (DataTable) event.getSource();
        Paroquia obj = (Paroquia) s.getRowData();
        
        try{
            paroquiaService.saveParoquia(obj);
        }catch(Exception e){
            messages.error("Erro ao salvar os dados!");
            e.printStackTrace();
        } 
    }
    
    public void handleFileUpload(FileUploadEvent event) throws IOException {                      
        UploadedFile capaCurso = event.getFile(); 
        try{
            InputStream is = capaCurso.getInputstream();
            byte[]capa = new byte[(int)capaCurso.getSize()];
            int offset = 0;
            int numRead = 0;
            while (offset < capa.length&& (numRead=is.read(capa, offset, capa.length-offset)) >= 0) {
                offset += numRead;
            }
            paroquia.setImagem(capa);
//            is = new ByteArrayInputStream(paroquia.getImagem());                //lembrar de comentar depois pra ver se funciona sem isso
//            graphicImage = new DefaultStreamedContent(is);
        }catch(Exception e){
            e.printStackTrace();
        }
    }        
    
    public StreamedContent download(Paroquia anexo) throws FileNotFoundException {  
                
        InputStream stream = new ByteArrayInputStream(anexo.getImagem()); 
        
//        String tipo = "application/pdf";
//        if(anexo.getExtensao().equals("doc")){
//            tipo = "application/msword";
//        }
//        if(anexo.getExtensao().equals("docx")){
//            tipo = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
//        }
        
        StreamedContent file = new DefaultStreamedContent(stream, "image/png", anexo.getDescricao());
        
        return file;
    }
    
    public void selecionar(SelectEvent event) {       
        this.paroquia = (Paroquia) event.getObject();  
        System.out.println("SELECIONANDO: "+paroquia.getDescricao());
        System.out.println("SELECIONANDO OUTRA: "+paroquiaSelecionada.getDescricao());
        if(paroquia.getImagem() != null){
            InputStream is = new ByteArrayInputStream(paroquia.getImagem());
            graphicImage = new DefaultStreamedContent(is);
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
    
    public StreamedContent getGraphicImage() {
        try{      
            if(paroquia.getImagem() != null){
                InputStream is = new ByteArrayInputStream(paroquia.getImagem());
                graphicImage = new DefaultStreamedContent(is);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return graphicImage;
    }

    public void setGraphicImage(StreamedContent graphicImage) {
        this.graphicImage = graphicImage;
    }

    public Paroquia getParoquiaSelecionada() {
        return paroquiaSelecionada;
    }

    public void setParoquiaSelecionada(Paroquia paroquiaSelecionada) {
        this.paroquiaSelecionada = paroquiaSelecionada;
    }    
}
