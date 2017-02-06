package br.gov.to.santuario.seg.converter;

import br.gov.to.santuario.ejc.domain.Seguidor;
import br.gov.to.santuario.ejc.service.SeguidorService;
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
@Service(value = "seguidorConverter")
public class SeguidorConverter implements Converter{
    @Autowired
    private SeguidorService seguidorService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {   
        if(!string.trim().equals("")){
            try{
                return (Seguidor) seguidorService.findOneSeguidor(Integer.valueOf(string));
            }catch(NumberFormatException e){
                 throw new ConverterException("Seguidor não encontrado. Mensagem: " + e.getMessage());
            }
            
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || o.equals("")){
            return "";
        }else{
            return String.valueOf(((Seguidor) o).getId());
        }
    }
}
