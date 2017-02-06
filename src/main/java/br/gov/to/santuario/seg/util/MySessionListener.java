package br.gov.to.santuario.seg.util;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;

public class MySessionListener implements HttpSessionListener {

    @Autowired
    private SessionRegistry sessionRegistry;

    public MySessionListener() {

    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();

        if (sessionRegistry != null && sessionRegistry.getSessionInformation(session.getId()).isExpired()) {

            try {
                prepareLogoutInfoAndLogoutActiveUser(session);
            } catch (Exception e) {
            }
            
        }
    }

    /**
     * Clean your logout operations.
     *
     * @param httpSession
     */
    public void prepareLogoutInfoAndLogoutActiveUser(HttpSession httpSession) {
        httpSession.invalidate();
        sessionRegistry.removeSessionInformation(httpSession.getId());
    }
}
