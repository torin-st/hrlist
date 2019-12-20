package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.client.web.interceptor.Loggable;
import com.slyadz.hrlist.entity.Employee;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * Backing bean for new Employee creation
 */
@Named
@ViewScoped
@Loggable
public class EmployeeManager implements Serializable {
    private Employee employee;
    private List<Employee> employees;


    public EmployeeManager() {
        employee = new Employee();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
