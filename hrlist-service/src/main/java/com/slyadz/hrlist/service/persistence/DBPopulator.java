package com.slyadz.hrlist.service.persistence;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

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
//    @EJB
//    private DepartmentDAO departmentDAO;
//    @EJB
//    private EmployeeDAO employeeDAO;

//    public DepartmentDAO getDepartmentDAO() {
//        return departmentDAO;
//    }
//
//    public EmployeeDAO getEmployeeDAO() {
//        return employeeDAO;
//    }

    @PreDestroy
    private void clear() {
        logger.info("clear()");
    }

    @PostConstruct
    private void populateDB() {
        logger.info("populateDB()");
        
        this.emf = Persistence.createEntityManagerFactory("hrlist");
        this.em = emf.createEntityManager();
        this.et = em.getTransaction();

        try {
            et.begin();
            //departments
            Department d1 = new Department("Отдел продаж");
            Department d2 = new Department("Отдел закупок");
            Department d3 = new Department("Бухгалтерия");
            Department d4 = new Department("Администрация");
            em.persist(d1);
            em.persist(d2);
            em.persist(d3);
            em.persist(d4);
            //employees
            //d1 employees        
            Employee e1 = new Employee("Иванов Иван Иванович", new Date(System.currentTimeMillis()), 10000f);
            e1.setDepartment(d1);
            em.persist(e1);
            Employee e11 = new Employee("Иванова Иванка Ивановна", new Date(System.currentTimeMillis()), 5000f);
            e11.setDepartment(d1);
            em.persist(e11);
            //d2 employees        
            Employee e2 = new Employee("Петров Петр Петрович", new Date(System.currentTimeMillis()), 10000f);
            e2.setDepartment(d2);
            em.persist(e2);
            Employee e21 = new Employee("Петрова Петра Петровна", new Date(System.currentTimeMillis()), 15000f);
            e21.setDepartment(d2);
            em.persist(e21);
            //d3 employees        
            Employee e3 = new Employee("Сидоров Сидор Сидорович", new Date(System.currentTimeMillis()), 10000f);
            e3.setDepartment(d3);
            em.persist(e3);
            //d4 employees                
            Employee e4 = new Employee("Андреев Андрей Андреевич", new Date(System.currentTimeMillis()), 10000f);
            e4.setDepartment(d4);
            em.persist(e4);            
            et.commit();
//            //departments
//            Department d1 = new Department("Отдел продаж");
//            Department d2 = new Department("Отдел закупок");
//            Department d3 = new Department("Бухгалтерия");
//            Department d4 = new Department("Администрация");
//            getDepartmentDAO().create(d1);
//            getDepartmentDAO().create(d2);
//            getDepartmentDAO().create(d3);
//            getDepartmentDAO().create(d4);
//            //employees
//            //d1 employees        
//            Employee e1 = new Employee("Иванов Иван Иванович", new Date(System.currentTimeMillis()), 10000f);
//            e1.setDepartment(d1);
//            getEmployeeDAO().create(e1);
//            Employee e11 = new Employee("Иванова Иванка Ивановна", new Date(System.currentTimeMillis()), 5000f);
//            e11.setDepartment(d1);
//            getEmployeeDAO().create(e11);
//            //d2 employees        
//            Employee e2 = new Employee("Петров Петр Петрович", new Date(System.currentTimeMillis()), 10000f);
//            e2.setDepartment(d2);
//            getEmployeeDAO().create(e2);
//            Employee e21 = new Employee("Петрова Петра Петровна", new Date(System.currentTimeMillis()), 15000f);
//            e21.setDepartment(d2);
//            getEmployeeDAO().create(e21);
//            //d3 employees        
//            Employee e3 = new Employee("Сидоров Сидор Сидорович", new Date(System.currentTimeMillis()), 10000f);
//            e3.setDepartment(d3);
//            getEmployeeDAO().create(e3);
//            //d4 employees                
//            Employee e4 = new Employee("Андреев Андрей Андреевич", new Date(System.currentTimeMillis()), 10000f);
//            e4.setDepartment(d4);
//            getEmployeeDAO().create(e4);
        } catch (Exception e) {
            logger.severe("error while populating DB: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

}
