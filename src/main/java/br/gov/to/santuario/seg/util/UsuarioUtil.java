package br.gov.to.santuario.seg.util;

import br.gov.to.santuario.seg.domain.Participante;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author flavio.madureira
 */
public class UsuarioUtil {
    
    public static Integer getId(){
        Participante usuario = (Participante) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
        return usuario.getId();
    }
        
    
    public static Participante getUsuario() {
        Participante usuario = (Participante) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
        return usuario;
    }     
}
