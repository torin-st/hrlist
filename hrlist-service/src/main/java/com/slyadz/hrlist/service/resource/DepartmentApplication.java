/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.service.resource;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import com.slyadz.hrlist.service.ejb.DepartmentService;

/**
 *
 * @A.G. Slyadz
 */
@ApplicationPath("api")
public class DepartmentApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(DepartmentService.class);
        return classes;
    }
    
}
