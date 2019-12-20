package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.client.web.interceptor.Loggable;
import com.slyadz.hrlist.entity.Department;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * Backing bean for new Department creation
 */
@Named
@ViewScoped
@Loggable
public class DepartmentManager implements Serializable {
    private Department department;

    public DepartmentManager() {
        department = new Department();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
