package com.slyadz.hrlist.service.persistence;

import javax.enterprise.inject.Alternative;

/**
 * Return alternative persistent unit name for integration test
 */
@PersistenceUnitName @Alternative
public class HrlistIT implements NameReturner {
    
    @Override
    public String getPersistenceUnitName() {
        return "hrlistIT";
    }
        
}
