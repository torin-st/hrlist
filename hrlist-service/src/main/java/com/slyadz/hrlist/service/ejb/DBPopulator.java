///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.slyadz.hrlist.service.ejb;
//
//import com.slyadz.hrlist.entity.Department;
//import java.io.Serializable;
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.ejb.Singleton;
//import javax.ejb.Startup;
//
///**
// *Prepopulate DB with a test data.
// * 
// * @author A.G. Slyadz
// */
//@Singleton
//@Startup
//public class DBPopulator implements Serializable {
//    @EJB
//    private DepartmentService departmentService;
//    
//    @PostConstruct
//    private void populateDB() {
//        departmentService.createDepartment(new Department("Отдел продаж"));
//        departmentService.createDepartment(new Department("Отдел закупок"));
//        departmentService.createDepartment(new Department("Бухгалтерия"));
//        departmentService.createDepartment(new Department("Администрация"));
//    }
//    
////    @Schedule(second = "*/10", minute = "*", hour = "*", persistent = false)
////    public void printMessageOnTimer(){
////        System.out.println("Tik-tak!");
////    }
//}
