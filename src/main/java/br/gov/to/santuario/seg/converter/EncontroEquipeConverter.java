package br.gov.to.santuario.seg.converter;

import br.gov.to.santuario.ejc.domain.EncontroEquipe;
import br.gov.to.santuario.ejc.service.EncontroEquipeService;
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
@Service(value = "encontroEquipeConverter")
public class EncontroEquipeConverter implements Converter{
    @Autowired
    private EncontroEquipeService encontroEquipeService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {   
        if(!string.trim().equals("")){
            try{
                return (EncontroEquipe) encontroEquipeService.findOneEncontroEquipe(Integer.valueOf(string));
            }catch(NumberFormatException e){
                 throw new ConverterException("EncontroEquipe não encontrado. Mensagem: " + e.getMessage());
            }
            
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || o.equals("")){
            return "";
        }else{
            return String.valueOf(((EncontroEquipe) o).getId());
        }
    }
}
