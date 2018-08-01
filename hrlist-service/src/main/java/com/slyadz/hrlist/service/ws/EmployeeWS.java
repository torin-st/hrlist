/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.service.ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import com.slyadz.hrlist.service.persistence.EmployeeDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebParam;

/**
 *
 * @author A. Slyadz
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

        if (employee == null) {
            System.out.println("employee is null!");
            return false;
        }

        boolean result = false;

        if (employeeDAO.create(employee) == 0) {
            System.out.println("Can't create Employee: " + employee);
        } else {
            result = true;
        }
        return result;
    }
    
     /**
     * Update employee in db
     *
     * @param employee - Employee instance
     * @return boolean true or false
     */
    @WebMethod
    public boolean updateEmployee(@WebParam(name = "employee") Employee employee) {

        boolean result = false;
                
        if (employee == null) {
            System.out.println("Can't update employee - it's null");
        } else
        {
            employeeDAO.update(employee);
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
        return result;
    }

     /**
     * Select employees by department id
     *
     * @param departmentId - department id (long)
     * @return List<Employee>
     */
    @WebMethod
    public List<Employee> getEmployeesByDepartment(@WebParam(name = "departmentId") long departmentId) {

        if (departmentId <= 0) {
            throw new IllegalArgumentException("Department's id is below 0!");
        } else
        {
            return employeeDAO.findByDepartment(departmentId);
        }
        
    }
    
}
