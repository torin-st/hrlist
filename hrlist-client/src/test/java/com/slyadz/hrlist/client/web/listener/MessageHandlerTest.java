package com.slyadz.hrlist.client.web.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Perform module tests for the MessageHandler class
 */
public class MessageHandlerTest {

    public MessageHandlerTest() {
    }

    /**
     * Test phase's correctness for messages' restoring.
     */
    @Test
    public void testBeforePhaseRenderResponsePhase() {
        System.out.println("testBeforePhaseRenderResponsePhase");
        PhaseEvent eventMock = mock(PhaseEvent.class);
        FacesContext facesContextMock = mock(FacesContext.class);
        doReturn(PhaseId.RENDER_RESPONSE).when(eventMock).getPhaseId();
        doReturn(facesContextMock).when(eventMock).getFacesContext();
        MessageHandler instance = new MessageHandler();
        MessageHandler instanceSpy = spy(instance);
        doReturn(0).when(instanceSpy).restoreMessages(facesContextMock);
        instanceSpy.beforePhase(eventMock);
        verify(eventMock).getFacesContext();
    }

    /**
     * Test phase's correctness for messages' restoring.
     */
    @Test
    public void testBeforePhaseRenderInvalidPhase() {
        System.out.println("testBeforePhaseRenderInvalidPhase");
        PhaseEvent eventMock = mock(PhaseEvent.class);
        MessageHandler instance = new MessageHandler();
        doReturn(PhaseId.ANY_PHASE).when(eventMock).getPhaseId();
        instance.beforePhase(eventMock);
        verify(eventMock, times(0)).getFacesContext();
        doReturn(PhaseId.RESTORE_VIEW).when(eventMock).getPhaseId();
        instance.beforePhase(eventMock);
        verify(eventMock, times(0)).getFacesContext();
        doReturn(PhaseId.APPLY_REQUEST_VALUES).when(eventMock).getPhaseId();
        instance.beforePhase(eventMock);
        verify(eventMock, times(0)).getFacesContext();
        doReturn(PhaseId.PROCESS_VALIDATIONS).when(eventMock).getPhaseId();
        instance.beforePhase(eventMock);
        verify(eventMock, times(0)).getFacesContext();
        doReturn(PhaseId.UPDATE_MODEL_VALUES).when(eventMock).getPhaseId();
        instance.beforePhase(eventMock);
        verify(eventMock, times(0)).getFacesContext();
        doReturn(PhaseId.INVOKE_APPLICATION).when(eventMock).getPhaseId();
        instance.beforePhase(eventMock);
        verify(eventMock, times(0)).getFacesContext();
    }

    /**
     * Test phase's correctness for messages' saving.
     */
    @Test
    public void testAfterPhaseRenderCorrectPhase() {
        System.out.println("testAfterPhaseRenderCorrectPhase");
        PhaseEvent eventMock = mock(PhaseEvent.class);
        FacesContext facesContextMock = mock(FacesContext.class);
        doReturn(facesContextMock).when(eventMock).getFacesContext();
        MessageHandler instance = new MessageHandler();
        MessageHandler instanceSpy = spy(instance);
        doReturn(0).when(instanceSpy).saveMessages(facesContextMock);
        doReturn(PhaseId.APPLY_REQUEST_VALUES).when(eventMock).getPhaseId();
        instanceSpy.afterPhase(eventMock);
        doReturn(PhaseId.PROCESS_VALIDATIONS).when(eventMock).getPhaseId();
        instanceSpy.afterPhase(eventMock);
        doReturn(PhaseId.INVOKE_APPLICATION).when(eventMock).getPhaseId();
        instanceSpy.afterPhase(eventMock);
        verify(eventMock, times(3)).getFacesContext();
    }

    /**
     * Test phase's correctness for messages' restoring.
     */
    @Test
    public void testAfterPhaseRenderInvalidPhase() {
        System.out.println("testAfterPhaseRenderInvalidPhase");
        PhaseEvent eventMock = mock(PhaseEvent.class);
        MessageHandler instance = new MessageHandler();
        doReturn(PhaseId.ANY_PHASE).when(eventMock).getPhaseId();
        instance.afterPhase(eventMock);
        doReturn(PhaseId.RESTORE_VIEW).when(eventMock).getPhaseId();
        instance.afterPhase(eventMock);
        doReturn(PhaseId.UPDATE_MODEL_VALUES).when(eventMock).getPhaseId();
        instance.afterPhase(eventMock);
        verify(eventMock, times(0)).getFacesContext();
    }
    
    /**
     * If messages-list is empty
     */
    @Test
    public void testSaveMessagesIsEmpty() throws Exception {
        System.out.println("testSaveMessagesIsEmpty");
        FacesContext facesContextMock = mock(FacesContext.class);
        Iterator iMock = mock(Iterator.class);
        doReturn(iMock).when(facesContextMock).getMessages(null);        
        when(iMock.hasNext()).thenReturn(Boolean.FALSE);
        MessageHandler instance = new MessageHandler();        
        assertEquals(0, instance.saveMessages(facesContextMock));
        assertNotEquals(1, instance.saveMessages(facesContextMock));
    }    

    /**
     * If messages-list contains 1 message and session map exists
     */
    @Test
    public void testSaveMessagesOneMessageSessionExists() throws Exception {
        System.out.println("testSaveMessagesOneMessageSessionExists");
        FacesContext facesContextMock = mock(FacesContext.class);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        Iterator iMock = mock(Iterator.class);
        doReturn(iMock).when(facesContextMock).getMessages(null);        
        doReturn(externalContextMock).when(facesContextMock).getExternalContext();        
        Map<String, Object> sessionMap = new HashMap<>();
        sessionMap.put(MessageHandler.sessionToken, new ArrayList<FacesMessage>());
        doReturn(sessionMap).when(externalContextMock).getSessionMap();
        when(iMock.hasNext()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(iMock.next()).thenReturn(new FacesMessage());        
        MessageHandler instance = new MessageHandler();        
        assertEquals(1, instance.saveMessages(facesContextMock));
    }        

    /**
     * If messages-list contains 1 message and session map doesn't exist
     */
    @Test
    public void testSaveMessagesOneMessageSessionNotExists() throws Exception {
        System.out.println("testSaveMessagesSessionNotExists");
        FacesContext facesContextMock = mock(FacesContext.class);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        Iterator iMock = mock(Iterator.class);
        doReturn(iMock).when(facesContextMock).getMessages(null);        
        doReturn(externalContextMock).when(facesContextMock).getExternalContext();        
        Map<String, Object> sessionMap = new HashMap<>();
        doReturn(sessionMap).when(externalContextMock).getSessionMap();
        when(iMock.hasNext()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(iMock.next()).thenReturn(new FacesMessage());        
        MessageHandler instance = new MessageHandler();        
        assertEquals(1, instance.saveMessages(facesContextMock));
        assertNotNull(sessionMap.get(MessageHandler.sessionToken));
    }        
    
    /**
     * If messages-list is null
     */
    @Test
    public void testRestoreMessagesIsNull() throws Exception {
        System.out.println("testRestoreMessagesIsNull");
        FacesContext facesContextMock = mock(FacesContext.class);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        doReturn(externalContextMock).when(facesContextMock).getExternalContext();        
        Map<String, Object> sessionMap = new HashMap<>();
        doReturn(sessionMap).when(externalContextMock).getSessionMap();
        MessageHandler instance = new MessageHandler();        
        assertEquals(0, instance.restoreMessages(facesContextMock));
    }        

    /**
     * If messages-list contains one message
     */
    @Test
    public void testRestoreMessagesNotNull() throws Exception {
        System.out.println("testRestoreMessagesNotNull");
        FacesContext facesContextMock = mock(FacesContext.class);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        doReturn(externalContextMock).when(facesContextMock).getExternalContext();        
        Map<String, Object> sessionMap = new HashMap<>();
        FacesMessage facesMessage = new FacesMessage();
        List<FacesMessage> list = new ArrayList<FacesMessage>();
        list.add(facesMessage);
        sessionMap.put( MessageHandler.sessionToken, list);
        doReturn(sessionMap).when(externalContextMock).getSessionMap();
        doNothing().when(facesContextMock).addMessage(null, facesMessage);
        MessageHandler instance = new MessageHandler();        
        assertEquals(1, instance.restoreMessages(facesContextMock));
        verify(facesContextMock).addMessage(null, facesMessage);
    }        
        
}
