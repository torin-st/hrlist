package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.client.web.interceptor.Loggable;
import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author A.G. Slyadz
 */
@Named
@SessionScoped
@Loggable
public class DepartmentBean extends AbstractEntityBean<Department> implements Serializable {

    private final static String SERVICE_URL = "http://localhost:8080/hrlist-service/api/departments";
    private Map<Department, Float> averageSalary;
    @Inject
    private EmployeeBean employeeBean;

    public DepartmentBean() {
    }

    @Override
    protected String getServiceURL() {
        return SERVICE_URL;
    }

    @Override
    protected String getOutcomePrefix() {
        return "Department";
    }

    @Override
    protected String getInstanceId(Department instance) {
        return instance.getId().toString();
    }

    public Map<Department, Float> getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Map<Department, Float> averageSalary) {
        this.averageSalary = averageSalary;
    }

    @PostConstruct
    private void init() {
        setClient(ClientBuilder.newClient());
        refreshEntities();
        refreshAverageSalary();
    }

    @PreDestroy
    private void clean() {
        getClient().close();
    }

    public String refresh(){
        employeeBean.refreshEntities();
        refreshEntities();
        refreshAverageSalary();
        return null;
    }
    /**
     * Create new department.
     *
     * @param department for creation
     * @return outcome for navigation
     */
    @Override
    public String create(Department department) {
        String result = super.create(department);
        if (result.equals("departmentCreated")) {
            refreshAverageSalary();
        }
        return result;
    }

    @Override
    public List<Department> findAll() {
        List<Department> result = null;
        //perform get request
        try {
            result = getClient().target(getServiceURL())
                    .path("/")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<Department>>() {
                    }); //Doesn't work in AbstractEntityBean - find out why
        } catch (Exception e) {
            message(null, "CouldNotFindAll" + getOutcomePrefix(), new Object[]{e.getMessage()});
            return null;
        }
        return result;
    }

    /**
     * Delete a department. Get it from request map.
     *
     * @param instance
     * @return outcome for navigation
     */
    @Override
    public String delete(Department instance) {
        String result = getOutcomePrefix().toLowerCase() + "Error";
        //check access
        Principal principal = getExternalContext().getUserPrincipal();
        if (principal == null) {
            result = "needLogin";
            return result;
        }
        if (instance == null) {
            message(null, "CouldNotDelete" + getOutcomePrefix(), new Object[]{getLocalizedMessage("InstanceIsNull")});
            return result;
        }        
        //check employees
        List<Employee> es = null;
        es = employeeBean.findByDepartment(instance);
        if (es != null && es.isEmpty() == false){
            message(null, "CouldNotDelete" + getOutcomePrefix(), new Object[]{getLocalizedMessage("DepartmentHasEmployees")});
            return result;            
        }
        
        result = super.delete(instance);
        if (result.equals("departmentDeleted")) {
            refreshAverageSalary();
        }
        return null;
    }

    /**
     * Update a particular department.
     *
     * @param department for update
     * @return outcome for navigation
     */
    @Override
    public String update(Department department) {
        String result = super.update(department);

        if (result.equals("departmentUpdated")) {
            refreshAverageSalary();
        }
        return result;
    }

    /**
     * Perform refreshing of average salary. It's necessary after create,
     * delete, update operations.
     *
     */
    void refreshAverageSalary() {
        if (getEntities() == null)
        {
            return;
        }
        
        HashMap<Department, Float> hashMap = new HashMap<>();
        //for all departmens, if too slow need refactoring for current department and futher with multithreading
        for (Department d : getEntities()) {
            float avgSalary = 0f;
            int employeeCount = 0;
            //get all employees for current department
            List<Employee> es = employeeBean.findByDepartment(d);
            if (es == null) { // department has no employees
                hashMap.put(d, 0.0f);
                continue;
            }
            if (es.isEmpty()) { // department has no employees
                hashMap.put(d, 0f);
                continue;
            }            
            //count common salarty and employee count
            for (Employee e : es) {
                avgSalary += e.getSalary();
                employeeCount++;
            }
            //count average salary and put it to the hash map
            avgSalary = avgSalary / employeeCount;
            hashMap.put(d, avgSalary);
        }
        setAverageSalary(hashMap);
    }

    /**
     * Find average salary for a particular department
     *
     * @param department for search
     * @return average salary
     */
    public float findAvgSalary(Department department) {
        if (department == null) {
            throw new NullPointerException("department is null!");
        }

        return getAverageSalary().entrySet().stream()
                .filter(x -> x.getKey().equals(department))
                .findFirst()
                .get()
                .getValue();
    }
    
}
