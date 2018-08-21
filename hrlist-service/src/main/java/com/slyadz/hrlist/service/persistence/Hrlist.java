package com.slyadz.hrlist.service.persistence;

import javax.enterprise.inject.Default;

/**
 *
 * @author A.G. Slyadz
 */
@PersistenceUnitName @Default
public class Hrlist implements NameReturner {
    
    @Override
    public String getPersistenceUnitName() {
        return "hrlist";
    }
        
}
