package com.slyadz.hrlist.client.web.managedbean;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * <p>
 * Abstract base class for managed beans to share utility methods.</p>
 *
 */
public abstract class AbstractBean {
    /**
     * @return the <code>FacesContext</code> instance for the current request.
     */
    protected FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }
    
    protected ExternalContext getExternalContext() {
        return getContext().getExternalContext();
    }

    protected String getLocalizedMessage(String key) {
        String text;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("com.slyadz.hrlist.client.web.message.message",
                    getContext().getViewRoot().getLocale());
            text = bundle.getString(key);
        } catch (Exception e) {
            text = "???" + key + "???";
        }
        return text;
    }
    /**
     * <p>
     * Add a localized message to the <code>FacesContext</code> for the current
     * request.</p>
     *
     * @param clientId Client identifier of the component this message relates
     * to, or <code>null</code> for global messages
     * @param key Message key of the message to include
     */
    protected void message(String clientId, String key) {
        // Look up the requested message text
        String text = getLocalizedMessage(key);
        // Construct and add a FacesMessage containing it
        getContext().addMessage(clientId, new FacesMessage(text));
    }

    /**
     * <p>
     * Add a localized message to the <code>FacesContext</code> for the current
     * request.</p>
     *
     * @param clientId Client identifier of the component this message relates
     * to, or <code>null</code> for global messages
     * @param key Message key of the message to include
     * @param params Substitution parameters for using the localized text as a
     * message format
     */
    protected void message(String clientId, String key, Object[] params) {
        // Look up the requested message text
        String text = getLocalizedMessage(key);
        // Perform the requested substitutions
        if ((params != null) && (params.length > 0)) {
            text = MessageFormat.format(text, params);
        }
        // Construct and add a FacesMessage containing it
        getContext().addMessage(clientId, new FacesMessage(text));
    }    
}
