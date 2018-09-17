package com.slyadz.hrlist.client.web.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Workaround for showing messanables messages to be rendered on different pages from which they were set.
 * To produce this behavior, this class acts as a <code>PhaseListener</code>.
 *
 * This is performed by moving the FacesMessage objects:
 *
 * <li>After each phase where messages may be added, this moves the messages
 * from the page-scoped FacesContext to the session-scoped session map.
 * </li>
 * <li>Before messages are rendered, this moves the messages from the
 * session-scoped session map back to the page-scoped FacesContext.
 * </li>
 * Only messages that are not associated with a particular component are ever
 * moved. These are the only messages that can be rendered on a page that is
 * different from where they originated.
 *
 * To enable this behaviour, add a <code>lifecycle</code> block to your
 * faces-config.xml file. That block should contain a single
 * <code>phase-listener</code> block containing the fully-qualified classname of
 * this file.
 *
 * EDIT: This code was minimally modified by Max Kuipers to address some of the
 * Java 1.6 compiler warnings. All code was originally written by Jesse Wilson.
 *
 * @author <a href="mailto:jesse@odel.on.ca">Jesse Wilson</a>
 * @author <a href="mailto:mkuipers@sourceallies.com">Max Kuipers</a>
 */
public class MessageHandler implements PhaseListener {

    private static final long serialVersionUID = 1L;

    /**
     * a name to save messages in the session under
     */
    private static final String sessionToken = "MULTI_PAGE_MESSAGES_SUPPORT";

    /**
     * Return the identifier of the request processing phase during which this
     * listener is interested in processing PhaseEvent events.
     * @return 
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    /**
     * Handle a notification that the processing for a particular phase of the
     * request processing lifecycle is about to begin.
     * @param event
     */
    @Override
    public void beforePhase(PhaseEvent event) {
        
        if (event.getPhaseId() == PhaseId.RESTORE_VIEW){
            System.out.println("ServletPath: " + event.getFacesContext().getExternalContext().getRequestServletPath());
            HttpServletRequest request = (HttpServletRequest) event.getFacesContext().getExternalContext().getRequest();        
            System.out.println("Method: " + request.getMethod());                    
        }
        
        System.out.println("before: " + event.getPhaseId());
        
        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            FacesContext facesContext = event.getFacesContext();
            restoreMessages(facesContext);
        } 
    }

    /**
     * Handle a notification that the processing for a particular phase has just
     * been completed.
     * @param event
     */
    @Override
    public void afterPhase(PhaseEvent event) {
        System.out.println("after: " + event.getPhaseId());
        if (event.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES
                || event.getPhaseId() == PhaseId.PROCESS_VALIDATIONS
                || event.getPhaseId() == PhaseId.INVOKE_APPLICATION) {        
            FacesContext facesContext = event.getFacesContext();
            saveMessages(facesContext);
        }
               
        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            System.out.println("isValidationFailed: " + event.getFacesContext().isValidationFailed());            
            System.out.println("-----------------------------------------------------------------------------------------");
        }         
    }

    /**
     * Remove the messages that are not associated with any particular component
     * from the faces context and store them to the user's session.
     *
     * @return the number of removed messages.
     */
    private int saveMessages(FacesContext facesContext) {
        // remove messages from the context
        List<FacesMessage> messages = new ArrayList<>();
        for (Iterator<FacesMessage> i = facesContext.getMessages(null); i.hasNext();) {
            messages.add((FacesMessage)i.next());
            i.remove();
        }
        // store them in the session
        if (messages.isEmpty()) {
            return 0;
        }
        Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
        // if there already are messages
        @SuppressWarnings("unchecked")
        List<FacesMessage> existingMessages = (List<FacesMessage>) sessionMap.get(sessionToken);
        if (existingMessages != null) {
            existingMessages.addAll(messages);
        } else {
            sessionMap.put(sessionToken, messages); // if these are the first messages
        }

        return messages.size();
    }

    /**
     * Remove the messages that are not associated with any particular component
     * from the user's session and add them to the faces context.
     *
     * @return the number of removed messages.
     */
    private int restoreMessages(FacesContext facesContext) {
        // remove messages from the session
        Map sessionMap = facesContext.getExternalContext().getSessionMap();
        @SuppressWarnings("unchecked")
        List messages = (List) sessionMap.remove(sessionToken);
        // store them in the context
        if (messages == null) {
            return 0;
        }
        int restoredCount = messages.size();
        for (Iterator i = messages.iterator(); i.hasNext();) {
            facesContext.addMessage(null, (FacesMessage) i.next());
        }

        return restoredCount;
    }
}
