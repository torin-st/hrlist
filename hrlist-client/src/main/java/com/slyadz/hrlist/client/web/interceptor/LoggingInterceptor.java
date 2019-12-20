package com.slyadz.hrlist.client.web.interceptor;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Interceptor-class for logging business methods
 */
@Interceptor
@Loggable
public class LoggingInterceptor implements Serializable {

    @Inject
    private Logger logger;

    @AroundConstruct
    private void logConstructor(InvocationContext ic) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("entering ").append(ic.getConstructor().getDeclaringClass().getSimpleName()).append(" ").append(ic.getConstructor().getName());
        logger.finer(sb.toString());
        
        try {
            ic.proceed();
        } finally {
            //logger.finer("exiting");
        }
    }
    
    @PostConstruct
    private void logPostConstruct(InvocationContext ic) {
        StringBuilder sb = new StringBuilder();
        sb.append("entering ").append(ic.getTarget().getClass().getSimpleName()).append(" PostConstruct");
        logger.finer(sb.toString());
        
        try {
            ic.proceed();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        finally {
            //logger.finer("exiting");
        }
    }    
    
    @PreDestroy
    private void logPreDestroy(InvocationContext ic) {
        StringBuilder sb = new StringBuilder();
        sb.append("entering ").append(ic.getTarget().getClass().getSimpleName()).append(" PreDestroy");
        logger.finer(sb.toString());
        
        try {
            ic.proceed();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        finally {
            //logger.finer("exiting");
        }
    }        

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("entering ").append(ic.getTarget().getClass().getSimpleName()).append(" ").append(ic.getMethod().getName());
        logger.finer(sb.toString());
        
        try {
            return ic.proceed();
        } finally {
            //logger.finer("exiting");
        }
    }
}
