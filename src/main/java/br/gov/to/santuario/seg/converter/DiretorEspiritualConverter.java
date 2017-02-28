package br.gov.to.santuario.seg.converter;

import br.gov.to.santuario.ejc.domain.DiretorEspiritual;
import br.gov.to.santuario.ejc.service.DiretorEspiritualService;
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
@Service(value = "diretorEspiritualConverter")
public class DiretorEspiritualConverter implements Converter{
    @Autowired
    private DiretorEspiritualService diretorEspiritualService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {   
        if(!string.trim().equals("")){
            try{
                return (DiretorEspiritual) diretorEspiritualService.findOneDiretorEspiritual(Integer.valueOf(string));
            }catch(NumberFormatException e){
                 throw new ConverterException("DiretorEspiritual não encontrado. Mensagem: " + e.getMessage());
            }
            
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || o.equals("")){
            return "";
        }else{
            return String.valueOf(((DiretorEspiritual) o).getId());
        }
    }
}
