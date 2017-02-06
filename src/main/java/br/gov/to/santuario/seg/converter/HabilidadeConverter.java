package br.gov.to.santuario.seg.converter;

import br.gov.to.santuario.ejc.domain.Habilidade;
import br.gov.to.santuario.ejc.service.HabilidadeService;
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
@Service(value = "habilidadeConverter")
public class HabilidadeConverter implements Converter{
    @Autowired
    private HabilidadeService habilidadeService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {   
        if(!string.trim().equals("")){
            try{
                return (Habilidade) habilidadeService.findOneHabilidade(Integer.valueOf(string));
            }catch(NumberFormatException e){
                 throw new ConverterException("Habilidade não encontrado. Mensagem: " + e.getMessage());
            }
            
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || o.equals("")){
            return "";
        }else{
            return String.valueOf(((Habilidade) o).getId());
        }
    }
}
