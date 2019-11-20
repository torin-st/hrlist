package com.slyadz.hrlist.service.persistence;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import com.slyadz.hrlist.service.rs.DepartmentRS;
import com.slyadz.hrlist.service.rs.EmployeeRS;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *Prepopulate DB with a test data.
 * 
 * @author A. Slyadz
 */
@Singleton
@Startup
public class DBPopulator implements Serializable {
//    @Inject
//    private DepartmentRS departmentRS;
//    @Inject
//    private EmployeeRS employeeRS;
    @EJB
    private DepartmentDAO departmentDAO;
    
    @PreDestroy
    private void clear() {
        System.out.println("******************** DBPopulator clear ********************");                                
    }    
    
    @PostConstruct
    private void populateDB() {
        System.out.println("******************** DBPopulator ********************");                
        Department d1 = new Department("Отдел продаж");
        try {
            departmentDAO.create(d1);
        } catch (Exception e) {
            System.out.println("error while populating DB: " + e.getMessage());
        }    
//        Department d2 = new Department("Отдел закупок");
//        Department d3 = new Department("Бухгалтерия");
//        Department d4 = new Department("Администрация");   
//        departmentRS.create(d1);
//        departmentRS.create(d2);        
//        departmentRS.create(d3);        
//        departmentRS.create(d4);                
        //d1 employees
//        Employee e1 = new Employee("Иванов Иван Иванович", new Date(System.currentTimeMillis()), 10000f);
//        e1.setDepartment(d1);
//        employeeRS.create(e1);
//        Employee e11 = new Employee("Иванова Иванка Ивановна", new Date(System.currentTimeMillis()), 5000f);
//        e11.setDepartment(d1);
//        employeeRS.create(e11);
//        //d2 employees        
//        Employee e2 = new Employee("Петров Петр Петрович", new Date(System.currentTimeMillis()), 10000f);
//        e2.setDepartment(d2);
//        employeeRS.create(e2);        
//        Employee e21 = new Employee("Петрова Петра Петровна", new Date(System.currentTimeMillis()), 15000f);
//        e21.setDepartment(d2);
//        employeeRS.create(e21);                
//        //d3 employees        
//        Employee e3 = new Employee("Сидоров Сидор Сидорович", new Date(System.currentTimeMillis()), 10000f);
//        e3.setDepartment(d3);
//        employeeRS.create(e3);
//        //d4 employees                
//        Employee e4 = new Employee("Андреев Андрей Андреевич", new Date(System.currentTimeMillis()), 10000f);
//        e4.setDepartment(d4);
//        employeeRS.create(e4);                    
    }

}
