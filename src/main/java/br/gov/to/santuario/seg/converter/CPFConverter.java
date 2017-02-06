package br.gov.to.santuario.seg.converter;

import br.com.caelum.stella.format.CPFFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author flavio.madureira
 */
@FacesConverter("CPFConverter")
public class CPFConverter implements Converter {     
 
    public static String formataCPF(String cpf) {  
      if (cpf == null || "".equals(cpf)) {  
        return "";  
      }  
      return new CPFFormatter().format(cpf);  
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
      CPFFormatter f = new CPFFormatter();  
      return f.format(o.toString());  
    }
}  
