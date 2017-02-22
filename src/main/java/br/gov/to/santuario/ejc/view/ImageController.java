package br.gov.to.santuario.ejc.view;

import br.gov.to.santuario.ejc.domain.Paroquia;
import br.gov.to.santuario.ejc.service.ParoquiaService;
import br.gov.to.santuario.seg.util.FacesMessages;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author flavio.madureira
 */
@ManagedBean
@SessionScoped
public class ImageController implements Serializable {
    
    @ManagedProperty(value = "#{paroquiaService}")
    private ParoquiaService paroquiaService;
    
    private FacesMessages messages = new FacesMessages();
    
    private Integer cursoId;
    private Paroquia curso;
    

    public StreamedContent getGraphicImageIndex() {
        
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String cursoIdd = context.getExternalContext().getRequestParameterMap().get("pid");
            Paroquia curso = paroquiaService.findOneParoquia(Integer.valueOf(cursoIdd));
            return new DefaultStreamedContent(new ByteArrayInputStream(curso.getImagem()));
        }
    }

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public FacesMessages getMessages() {
        return messages;
    }

    public void setMessages(FacesMessages messages) {
        this.messages = messages;
    }
    
    public StreamedContent getGraphicCurso() {
        
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            
            String cursoIdd = context.getExternalContext().getRequestParameterMap().get("idCurso");
            Paroquia curso = paroquiaService.findOneParoquia(Integer.valueOf(cursoIdd));

            return new DefaultStreamedContent(new ByteArrayInputStream(curso.getImagem()));
        }

    }

    public StreamedContent getGraphicCroppedCurso() throws FileNotFoundException {
        
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            String path = context.getExternalContext().getRequestParameterMap().get("path");
            File file2 = new File(path);
            InputStream input = new FileInputStream(file2);
            return new DefaultStreamedContent(input);
        }
    }

    public ParoquiaService getParoquiaService() {
        return paroquiaService;
    }

    public void setParoquiaService(ParoquiaService paroquiaService) {
        this.paroquiaService = paroquiaService;
    }

    public Paroquia getCurso() {
        return curso;
    }

    public void setCurso(Paroquia curso) {
        this.curso = curso;
    }
    
}
