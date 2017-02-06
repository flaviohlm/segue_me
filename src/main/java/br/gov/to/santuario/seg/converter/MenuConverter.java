package br.gov.to.santuario.seg.converter;

import br.gov.to.santuario.seg.domain.Menu;
import br.gov.to.santuario.seg.service.MenuService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author flavio.madureira
 */
@Service(value = "menuConverter")
@FacesConverter(forClass = Menu.class)
public class MenuConverter implements Converter{ 
    
    @Autowired
    private MenuService menuService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {   
        if(!string.trim().equals("")){
            try{
                return (Menu) menuService.findOneMenu(Integer.valueOf(string));
            }catch(NumberFormatException e){
                 throw new ConverterException("Menu não encontrado. Mensagem: " + e.getMessage());
            }
            
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null || o.equals("")){
            return "";
        }else{
            return String.valueOf(((Menu) o).getId());
        }
    }

    
}
