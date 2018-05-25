/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.client.web.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author A.G. Slyadz
 */
@Named
@SessionScoped
public class AuthBean implements Serializable {

    public AuthBean() {
    }

    public void userLogout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        while (externalContext.getSession(false) != null){
            HttpSession session = (HttpSession) externalContext.getSession(false);
            session.invalidate();
        }
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
    }

    public String getUserName() {
        String result = "-";
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal != null) {
            result = principal.toString();
        }
        return result;
    }

}
