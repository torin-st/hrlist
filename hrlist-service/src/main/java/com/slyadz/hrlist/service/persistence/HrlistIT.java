package com.slyadz.hrlist.service.persistence;

import javax.enterprise.inject.Alternative;

/**
 *
 * @author A.G. Slyadz
 */
@PersistenceUnitName @Alternative
public class HrlistIT implements NameReturner {
    
    @Override
    public String getPersistenceUnitName() {
        return "hrlistIT";
    }
        
}
