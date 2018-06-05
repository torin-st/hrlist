/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.service.ws.Employee;
import com.slyadz.hrlist.service.ws.EmployeeWS;
import com.slyadz.hrlist.service.ws.EmployeeWSService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;


/**
 *
 * @author A.G. Slyadz
 */
@Named
@SessionScoped
public class EmployeeBean implements Serializable {

    private Employee employee;
    @WebServiceRef
    private EmployeeWSService employeeWSService;

    public EmployeeBean() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @PostConstruct
    private void init() {
        employee = new Employee();
    }

    @PreDestroy
    private void clean() {
    }
    
    public String prepareEmployee(){
        String navigation = "employeePrepared";
        setEmployee(new Employee());
        return navigation;
    }

    public List<Employee> getAllEmployees() {
                
        EmployeeWS employeeWS = employeeWSService.getEmployeeWSPort();
        return employeeWS.getAllEmployees();

    }

    public String createEmployee(Employee employee) {
        
        employee = new Employee();
        employee.setName("Andrey" + (int)(Math.random() * 10));
        employee.setBirthday(new Date(System.currentTimeMillis()));
        employee.setSalary(10000.0f);
        
        String navigation = "employeeError";

        if (employee == null) {
            return navigation;
        }
        
        EmployeeWS employeeWS = employeeWSService.getEmployeeWSPort();
        boolean result = false;
        result = employeeWS.createEmployee(employee);
        
        if (result) {
            navigation = "employeeCreated";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Couldn't create employee."));
        }
        
        return navigation;
    }

    public String deleteEmployee() {
        
        String navigation = "employeeError";
        Employee employee = (Employee) FacesContext.getCurrentInstance()
                .getExternalContext().getRequestMap().get("employee");
        if (employee == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Couldn't delete employee, employee == null."));            
            return navigation;
        }

        EmployeeWS employeeWS = employeeWSService.getEmployeeWSPort();
        boolean result = false;
        result = employeeWS.deleteEmployee(employee.getId());

        if (result) {
            navigation = "employeeDeleted";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Server could't delete employee."));
            navigation = "employeeError";
        }
        return navigation;
    }

//    public String updateDepartment(Department department) {
//        String navigation = "departmentError";
//        if (department == null) {
//            return navigation;
//        }
//
//        Response response
//                = client.target(serviceURL)
//                        .path("/")
//                        .request()
//                        .put(Entity.entity(department, MediaType.APPLICATION_XML), Response.class);
//        if (Response.Status.SEE_OTHER.getStatusCode() == response.getStatus()) {
//            navigation = "departmentUpdated";
//        } else {
//            System.out.println("==" + response.getStatus());
//            FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(null,
//                    new FacesMessage("Couldn't update department."));
//        }
//        return navigation;
//    }
}
