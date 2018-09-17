package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.client.web.interceptor.Loggable;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author A.G. Slyadz
 */
@Named
@SessionScoped
//@Loggable
public class AuthBean extends AbstractBean implements Serializable {
    public AuthBean() {
    }

    public void userLogout() throws IOException {
        ExternalContext externalContext = getExternalContext();
        while (externalContext.getSession(false) != null){
            HttpSession session = (HttpSession) externalContext.getSession(false);
            session.invalidate();
        }
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
    }

    public String getUserName() {
        String result = "-";
        Principal principal = getExternalContext().getUserPrincipal();
        if (principal != null) {
            result = principal.toString();
        }
        return result;
    }
}
