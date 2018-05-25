/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.service.ejb;

import com.slyadz.hrlist.entity.Employee;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author user
 */
@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> {

    //@PersistenceContext(unitName = "hrlist")
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
        emf = Persistence.createEntityManagerFactory("hrlist");
        em = emf.createEntityManager();
        et = em.getTransaction();
    }

    @PreDestroy
    private void clean() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
    
}
