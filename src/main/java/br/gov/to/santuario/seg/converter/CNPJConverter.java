package br.gov.to.santuario.seg.converter;

import br.com.caelum.stella.format.CNPJFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author flavio.madureira
 */
@FacesConverter("CNPJConverter")
public class CNPJConverter implements Converter { 
    
    public static String formataCNPJ(String cnpj) {  
      if (cnpj == null || "".equals(cnpj)) {  
        return "";  
      }  
      return new CNPJFormatter().format(cnpj);  
    } 

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return string;  
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {  
        return "";  
      }  
      CNPJFormatter f = new CNPJFormatter();  
      return f.format(o.toString());  
    }
}
