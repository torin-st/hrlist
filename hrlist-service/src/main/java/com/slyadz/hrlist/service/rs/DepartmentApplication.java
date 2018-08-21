package com.slyadz.hrlist.service.rs;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author A.G. Slyadz
 */
@ApplicationPath("api")
public class DepartmentApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(DepartmentRS.class);
        classes.add(EmployeeRS.class);        
        return classes;
    }
    
}
