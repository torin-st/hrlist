package com.slyadz.hrlist.client.web.interceptor;

import java.util.logging.Level;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;
/**
 * 
 * @author A.G. Slyadz
 */
public class LoggingProducer {
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        Logger logger = Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
        logger.setLevel(Level.FINER);
        return logger;
    }
}
