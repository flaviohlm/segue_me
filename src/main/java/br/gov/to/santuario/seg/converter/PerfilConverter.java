package br.gov.to.santuario.seg.converter;

import br.gov.to.santuario.seg.domain.Perfil;
import br.gov.to.santuario.seg.service.PerfilService;
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
@Service(value = "perfilConverter")
public class PerfilConverter implements Converter{
    @Autowired
    private PerfilService perfilService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {   
        if(!string.trim().equals("")){
            try{
                return (Perfil) perfilService.findOnePerfil(Integer.valueOf(string));
            }catch(NumberFormatException e){
                 throw new ConverterException("Perfil não encontrado. Mensagem: " + e.getMessage());
            }
            
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || o.equals("")){
            return "";
        }else{
            return String.valueOf(((Perfil) o).getId());
        }
    }
}
