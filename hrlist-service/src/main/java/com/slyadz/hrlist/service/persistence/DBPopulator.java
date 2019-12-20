package com.slyadz.hrlist.service.persistence;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Prepopulate DB with a test data.
 */
@Singleton
@Startup
public class DBPopulator implements Serializable {

    private final static Logger logger = Logger.getLogger(DBPopulator.class.getCanonicalName());
    
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;
    @Inject
    @PersistenceUnitName
    private NameReturner nameReturner;

    public NameReturner getNameReturner() {
        return nameReturner;
    }

    public void setNameReturner(NameReturner nameReturner) {
        this.nameReturner = nameReturner;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EntityTransaction getEt() {
        return et;
    }

    public void setEt(EntityTransaction et) {
        this.et = et;
    }

    @PostConstruct
    private void populateDB() {
        setEmf(Persistence.createEntityManagerFactory(getNameReturner().getPersistenceUnitName()));
        setEm(emf.createEntityManager());
        setEt(em.getTransaction());

        try {
            getEt().begin();
                //departments
                Department d1 = new Department("Отдел продаж");
                Department d2 = new Department("Отдел закупок");
                Department d3 = new Department("Бухгалтерия");
                Department d4 = new Department("Администрация");
                getEm().persist(d1);
                getEm().persist(d2);
                getEm().persist(d3);
                getEm().persist(d4);
                //employees
                //d1 employees        
                Employee e1 = new Employee("Иванов Иван Иванович", new Date(System.currentTimeMillis()), 10000f);
                e1.setDepartment(d1);
                getEm().persist(e1);
                Employee e11 = new Employee("Иванова Иванка Ивановна", new Date(System.currentTimeMillis()), 5000f);
                e11.setDepartment(d1);
                getEm().persist(e11);
                //d2 employees        
                Employee e2 = new Employee("Петров Петр Петрович", new Date(System.currentTimeMillis()), 10000f);
                e2.setDepartment(d2);
                getEm().persist(e2);
                Employee e21 = new Employee("Петрова Петра Петровна", new Date(System.currentTimeMillis()), 15000f);
                e21.setDepartment(d2);
                getEm().persist(e21);
                //d3 employees        
                Employee e3 = new Employee("Сидоров Сидор Сидорович", new Date(System.currentTimeMillis()), 10000f);
                e3.setDepartment(d3);
                getEm().persist(e3);
                //d4 employees                
                Employee e4 = new Employee("Андреев Андрей Андреевич", new Date(System.currentTimeMillis()), 10000f);
                e4.setDepartment(d4);
                getEm().persist(e4);            
            getEt().commit();
        } catch (Exception e) {
            logger.severe("error while populating DB: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

}
