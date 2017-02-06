package br.gov.to.santuario.seg.converter;

import br.gov.to.santuario.ejc.domain.Palestra;
import br.gov.to.santuario.ejc.service.PalestraService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author flavio.madureira
 */
@Service(value = "palestraConverter")
public class PalestraConverter implements Converter{
    @Autowired
    private PalestraService palestraService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {   
        if(!string.trim().equals("")){
            try{
                return (Palestra) palestraService.findOnePalestras(Integer.valueOf(string));
            }catch(NumberFormatException e){
                 throw new ConverterException("Palestra não encontrado. Mensagem: " + e.getMessage());
            }
            
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || o.equals("")){
            return "";
        }else{
            return String.valueOf(((Palestra) o).getId());
        }
    }
}
