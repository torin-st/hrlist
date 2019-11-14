package com.slyadz.hrlist.client.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Perform module tests for the NoCacheFilter class
 * 
 * @author A.G. Slyadz
 */
public class NoCacheFilterTest {
    
    public NoCacheFilterTest() {
    }

    /**
     * Test headers changing during the filter invocation
     * @throws java.lang.Exception
     */
    @Test
    public void testDoFilter() throws Exception {
        System.out.println("doFilter");
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);        
        FilterChain filterChainMock = mock(FilterChain.class);
        doReturn("/hrlist-client/new.xhtml").when(requestMock).getRequestURI();
        doReturn("/hrlist-client").when(requestMock).getContextPath();
        NoCacheFilter instance = new NoCacheFilter();
        instance.doFilter(requestMock, responseMock, filterChainMock);
        verify(responseMock).setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        verify(responseMock).setHeader("Pragma", "no-cache");
        verify(responseMock).setDateHeader("Expires", 0);
    }

}
