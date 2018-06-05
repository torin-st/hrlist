/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.service.ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import com.slyadz.hrlist.entity.Employee;
import com.slyadz.hrlist.service.persistence.EmployeeDAO;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;

/**
 *
 * @author A.G. Slyadz
 */
@WebService
public class EmployeeWS {

    @EJB
    private EmployeeDAO employeeDAO;

    /**
     * Delete employee from db
     *
     * @param id - employee's id
     * @return boolean true or false
     */
    @WebMethod
    public boolean deleteEmployee(@WebParam(name = "employeeId") long id) {
        boolean result = false;

        Employee employee = null;
        if ((employee = employeeDAO.findById(id)) == null) {
            System.out.println("Can't find Employee with id = " + id);
        } else {
            employeeDAO.delete(employee);
            result = true;
        }
        return result;
    }
    
     /**
     * Create employee in db
     *
     * @param employee - Employee instance
     * @return boolean true or false
     */
    @WebMethod
    public boolean createEmployee(@WebParam(name = "employee") Employee employee) {
        boolean result = false;

        System.out.println(employee);
        if (employeeDAO.create(employee) == 0) {
            System.out.println("Can't create Employee: " + employee);
        } else {
            result = true;
        }
        return result;
    }

    /**
     * Gets all employees from db
     *
     * @return List<Employee>
     */
    @WebMethod
    public List<Employee> getAllEmployees() {
        List<Employee> result = (List<Employee>) employeeDAO.findAll();
        System.out.println(result);
        return result;
    }
    
    /**
     * Create another Employee
     *
     * @return List<Employee>
     */
    @WebMethod
    public boolean test() {
        Employee employee = new Employee("Andrey" + (int)(Math.random() * 10) , new Date(System.currentTimeMillis()), 10000.0f);
        
        boolean result = false;

        if (employeeDAO.create(employee) == 0) {
            System.out.println("Can't create Employee: " + employee);
        } else {
            result = true;
        }
        
        return result;
    }    
}
