package com.slyadz.hrlist.service.persistence;

import javax.enterprise.inject.Alternative;

/**
 *
 */
@PersistenceUnitName @Alternative
public class HrlistIT implements NameReturner {
    
    @Override
    public String getPersistenceUnitName() {
        System.out.println("************HELLO***************************");
        return "hrlistIT";
    }
        
}
