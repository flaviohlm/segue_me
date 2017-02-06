package br.gov.to.santuario.seg.converter;

import br.gov.to.santuario.ejc.domain.Paroquia;
import br.gov.to.santuario.ejc.service.ParoquiaService;
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
@Service(value = "paroquiaConverter")
public class ParoquiaConverter implements Converter{
    @Autowired
    private ParoquiaService paroquiaService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {   
        if(!string.trim().equals("")){
            try{
                return (Paroquia) paroquiaService.findOneParoquia(Integer.valueOf(string));
            }catch(NumberFormatException e){
                 throw new ConverterException("Paroquia não encontrado. Mensagem: " + e.getMessage());
            }
            
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || o.equals("")){
            return "";
        }else{
            return String.valueOf(((Paroquia) o).getId());
        }
    }
}
