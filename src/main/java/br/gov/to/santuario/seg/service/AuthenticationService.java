package br.gov.to.santuario.seg.service;

import br.gov.to.santuario.seg.domain.Participante;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Service;

/**
 *
 * @author wellyngton.santos
 */
@Service
public class AuthenticationService {

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Inject
    @Qualifier("sas")
    private SessionAuthenticationStrategy sessionAuthenticationStrategy;

    @Autowired
    private SessionRegistry sessionRegistry;
    
    Authentication authenticate;

    public boolean login(String username, String password) {
        try {           
           UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            
           authenticate = authenticationManager.authenticate(token);

           if (authenticate.isAuthenticated()) {
               SecurityContextHolder.getContext().
                       setAuthentication(authenticate);
               return true;
           }
       } catch (AuthenticationException e) {
       }
       return false;
        /*try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            authenticate = authenticationManager.authenticate(token);
            
            HttpServletRequest httpReq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpServletResponse httpResp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            
            sessionAuthenticationStrategy.onAuthentication(authenticate, httpReq, httpResp);

            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                return true;
            }
        } catch (SessionAuthenticationException sae) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário já está logado no sistema!", ""));            
        } catch (AuthenticationException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou Senha inválidos!", ""));            
        }

        return false;*/
    }

    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);              
        invalidateSession();        
    }

    public Participante getUsuarioLogado() {
        return (Participante) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void invalidateSession() {        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);        
        session.invalidate();
        sessionRegistry.removeSessionInformation(session.getId());        
    }   

}
