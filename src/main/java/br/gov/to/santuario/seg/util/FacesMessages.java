package br.gov.to.santuario.seg.util;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 *
 * @author ana.dias
 */
public class FacesMessages implements Serializable{

    private void add(String message, Severity severity) {
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(message,"");
        msg.setSeverity(severity);
        
        context.addMessage("", msg);
    }

    public void info(String message) {
            add(message, FacesMessage.SEVERITY_INFO);
    }

    public void error(String message) {
            add(message, FacesMessage.SEVERITY_ERROR);
    }
}
