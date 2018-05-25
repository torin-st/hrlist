/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import com.slyadz.hrlist.entity.Employee;
import com.slyadz.hrlist.service.ejb.EmployeeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author A.G. Slyadz
 */
@WebService
public class EmployeeWS {
    
    @EJB
    private EmployeeFacade employeeFacade;

    /**
     * This is a sample web service operation
     * @param txt
     * @return "Hello txt !" string
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
     /**
     * Gets all employees from db
     * @return List<Employee>
     */
    @WebMethod
    public List<Employee> getAllEmployees() {
        List<Employee> result = (List<Employee>)employeeFacade.findAll();
        System.out.println(result);
        return result;
    }
}
