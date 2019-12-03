package com.slyadz.hrlist.service.persistence;

import javax.enterprise.inject.Default;

/**
 * Return persistent unit name
 */
@PersistenceUnitName @Default
public class Hrlist implements NameReturner {
    
    @Override
    public String getPersistenceUnitName() {
        return "hrlist";
    }
        
}
