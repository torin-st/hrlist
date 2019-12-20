package com.slyadz.hrlist.client.web.managedbean;

import java.security.Principal;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Perform module tests for the AuthBean class
 */
public class AuthBeanTest {
    
    public AuthBeanTest() {
    }

    /**
     * If getSession(false) equals null.
     */
    @Test
    public void testUserLogoutSessionIsNull() throws Exception {
        
        System.out.println("testUserLogoutSessionIsNull");
        AuthBean instance = new AuthBean();
        AuthBean instanceSpy = spy(instance);
        FacesContext facesContextMock = mock(FacesContext.class);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        doReturn(facesContextMock).when(instanceSpy).getContext();
        doReturn(externalContextMock).when(facesContextMock).getExternalContext();        
        doReturn(null).when(externalContextMock).getSession(false);
        doReturn("/hrlist-client").when(externalContextMock).getRequestContextPath();
        doNothing().when(externalContextMock).redirect("/hrlist-client/index.xhtml");
        instanceSpy.userLogout();
        verify(externalContextMock).redirect("/hrlist-client/index.xhtml");
    }

/**
     * If getSession(false) is not null.
     */
    @Test
    public void testUserLogoutSessionNotNull() throws Exception {
        
        System.out.println("testUserLogoutSessionNotNull");
        AuthBean instance = new AuthBean();
        AuthBean instanceSpy = spy(instance);
        FacesContext facesContextMock = mock(FacesContext.class);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        HttpSession sessionMock = mock(HttpSession.class);
        doReturn(facesContextMock).when(instanceSpy).getContext();
        doReturn(externalContextMock).when(facesContextMock).getExternalContext();        
        when(externalContextMock.getSession(false)).thenReturn(sessionMock, sessionMock, null);
        doReturn("/hrlist-client").when(externalContextMock).getRequestContextPath();
        doNothing().when(sessionMock).invalidate();
        doNothing().when(externalContextMock).redirect("/hrlist-client/index.xhtml");
        instanceSpy.userLogout();
        verify(sessionMock).invalidate();
    }    

    /**
     * If principal is null.
     */
    @Test
    public void testGetUserNamePrincipalIsNull() throws Exception {
        System.out.println("testGetUserNamePrincipalIsNull");
        AuthBean instance = new AuthBean();
        AuthBean instanceSpy = spy(instance);
        FacesContext facesContextMock = mock(FacesContext.class);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        doReturn(facesContextMock).when(instanceSpy).getContext();
        doReturn(externalContextMock).when(facesContextMock).getExternalContext();        
        doReturn(null).when(externalContextMock).getUserPrincipal();
        assertEquals("-", instanceSpy.getUserName());
    }

    /**
     * If principal is not null.
     */
    @Test
    public void testGetUserNamePrincipalNotNull() throws Exception {
        System.out.println("testGetUserNamePrincipalNotNull");
        AuthBean instance = new AuthBean();
        AuthBean instanceSpy = spy(instance);
        FacesContext facesContextMock = mock(FacesContext.class);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        Principal principalMock = mock(Principal.class);
        doReturn(facesContextMock).when(instanceSpy).getContext();
        doReturn(externalContextMock).when(facesContextMock).getExternalContext();        
        doReturn(principalMock).when(externalContextMock).getUserPrincipal();
        doReturn("test").when(principalMock).toString();
        assertEquals("test", instanceSpy.getUserName());
    }
    
}
