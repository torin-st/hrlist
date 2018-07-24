/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.service.persistence;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *Prepopulate DB with a test data.
 * 
 * @author A.G. Slyadz
 */
@Singleton
@Startup
public class DBPopulator implements Serializable {
//    @EJB
//    private DepartmentService departmentService;
    @EJB
    private EmployeeDAO employeeFacade;
    
    @PostConstruct
    private void populateDB() {
//        departmentService.createDepartment(new Department("Отдел продаж"));
//        departmentService.createDepartment(new Department("Отдел закупок"));
//        departmentService.createDepartment(new Department("Бухгалтерия"));
//        departmentService.createDepartment(new Department("Администрация"));
//          employeeFacade.create(new Employee("Иван Иванов", new Date(System.currentTimeMillis()), 27000f));
//          employeeFacade.create(new Employee("Петр Петров", new Date(System.currentTimeMillis()), 77000f));
         //employeeFacade.create(new Employee("Иванка Сидоров", new Date(System.currentTimeMillis()), 47000f));     
         //employeeFacade.myCreate(new Employee("Иванка Трамп", new Date(System.currentTimeMillis()), 27000f));
            
    }
    
//    @Schedule(second = "*/10", minute = "*", hour = "*", persistent = false)
//    public void printMessageOnTimer(){
//        System.out.println("Tik-tak!");
//    }
}
