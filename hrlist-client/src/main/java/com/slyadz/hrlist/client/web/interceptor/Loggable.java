package com.slyadz.hrlist.client.web.interceptor;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

/**
 * An interface to mark a class for logging
 * 
 * @author A.G. Slyadz
 */
@InterceptorBinding
@Retention(RUNTIME)
@Target({TYPE})
public @interface Loggable {
}
