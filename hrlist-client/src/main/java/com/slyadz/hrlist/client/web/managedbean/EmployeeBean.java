/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author A.G. Slyadz
 */
@Named
@SessionScoped
public class EmployeeBean implements Serializable {

    private final String serviceURL = "http://localhost:8080/hrlist-service/api/employees";
    private Client client;
    private Employee employee;
    @Inject
    private DepartmentBean departmentBean;
    
    public EmployeeBean() {}

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @PostConstruct
    private void init() {
        setClient(ClientBuilder.newClient());
        setEmployee(new Employee());
    }

    @PreDestroy
    private void clean() {
        client.close();
    }

    public String prepare() {
        String navigation = "employeePrepared";
        setEmployee(new Employee());
        return navigation;
    }

    public List<Employee> findAll() {

        return client.target(serviceURL)
                .path("/")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Employee>>() {
                });
        //EmployeeWS employeeWS = employeeWSService.getEmployeeWSPort();
        //return employeeWS.getAllEmployees();

    }

    public List<Employee> findByDepartment(Department department) {

        if (department == null) {
            throw new NullPointerException("department is null!");
        }

        //EmployeeWS employeeWS = employeeWSService.getEmployeeWSPort();
        //return employeeWS.getEmployeesByDepartment(department.getId());
        return client.target(serviceURL)
                .path("/find_by_department_id/" + department.getId().toString())
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Employee>>(){});        
    }

    public String create(Employee employee) {
        String navigation = "employeeError";

        if (employee == null) {
            return navigation;
        }

        //EmployeeWS employeeWS = employeeWSService.getEmployeeWSPort();
        //boolean result = false;
        //result = employeeWS.createEmployee(employee);
        Response response = client.target(serviceURL)
                .path("/")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(employee, MediaType.APPLICATION_XML),
                        Response.class);

        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            navigation = "employeeCreated";
            departmentBean.refreshAverageSalary();            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Server couldn't have deleted an employee with code " + response.getStatus()));
        }
        return navigation;
    }

    public String delete() {
        String navigation = "employeeError";
        Employee employee = (Employee) FacesContext.getCurrentInstance()
                .getExternalContext().getRequestMap().get("employee");
        if (employee == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Couldn't delete employee - it's null."));
            return navigation;
        }

//        EmployeeWS employeeWS = employeeWSService.getEmployeeWSPort();
//        boolean result = false;
//        result = employeeWS.deleteEmployee(employee.getId());
        Response response = client.target(serviceURL)
                .path(employee.getId().toString())
                .request()
                .delete();
        if (Response.Status.NO_CONTENT.getStatusCode() == response.getStatus()) {
            navigation = "employeeDeleted";
            departmentBean.refreshAverageSalary();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Server could't delete employee."));
        }
        return navigation;
    }

    public String update(Employee employee) {
        String navigation = "employeeError";
        if (employee == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Couldn't update employee - it's null."));
            return navigation;
        }

//        EmployeeWS employeeWS = employeeWSService.getEmployeeWSPort();
//        boolean result = false;
//        result = employeeWS.updateEmployee(employee);
        Response response
                = client.target(serviceURL)
                        .path("/")
                        .request()
                        .put(Entity.entity(employee, MediaType.APPLICATION_XML), Response.class);

        if (Response.Status.SEE_OTHER.getStatusCode() == response.getStatus()) {
            navigation = "employeeUpdated";
            departmentBean.refreshAverageSalary();            
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Server could't update employee."));
        }

        return navigation;
    }
}
