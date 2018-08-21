package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class DepartmentBean implements Serializable {

    private final String serviceURL = "http://localhost:8080/hrlist-service/api/departments";
    private Client client;
    private Department department;
    private List<Department> departments;
    private Map<Department, Float> averageSalary;
    @Inject
    private EmployeeBean employeeBean;

    public DepartmentBean() {
    }

    public Map<Department, Float> getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Map<Department, Float> averageSalary) {
        this.averageSalary = averageSalary;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @PostConstruct
    private void init() {
        setClient(ClientBuilder.newClient());
        setDepartment(new Department());
        refreshDepartments();
        refreshAverageSalary();
    }

    @PreDestroy
    private void clean() {
        getClient().close();
    }

    public String prepare() {
        String navigation = "departmentPrepared";
        setDepartment(new Department());
        return navigation;
    }

    /**
     * Performs refreshing of departments. It's necessary after create, delete,
     * update operations.
     *
     */
    private void refreshDepartments() {
        setDepartments(findAll());
    }

     /**
     * Performs refreshing of average salary. It's necessary after create, delete,
     * update operations.
     *
     */
    void refreshAverageSalary() {
            HashMap<Department, Float> hashMap = new HashMap<>();
            //for all departmens, if too slow need refactoring for current department and futher with multithreading
            for(Department d : getDepartments()){
                Float avgSalary = 0f;
                int employeeCount = 0;
                //get all employees for current department
                List<Employee> es = employeeBean.findByDepartment(d);
                if (es == null) { // department has no employees
                   hashMap.put(d, 0f);
                   continue;
                }
                //count common salarty and employee count
                for(Employee e : es){
                    avgSalary += e.getSalary();
                    employeeCount++;
                }
                //count average salary and put it to the hash map
                avgSalary = avgSalary / employeeCount;
                hashMap.put(d, avgSalary);
            }
            
            setAverageSalary(hashMap);
    }

    public Department findById(Long departmentId) {
        return client.target(serviceURL)
                .path(departmentId.toString())
                .request(MediaType.APPLICATION_XML)
                .get(Department.class);
    }

    public List<Department> findAll() {
        return client.target(serviceURL)
                .path("/")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Department>>() {
                });
    }

    public String create(Department department) {
        String navigation = "departmentError";

        if (department == null) {
            return navigation;
        }

        Response response = client.target(serviceURL)
                .path("/")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(department, MediaType.APPLICATION_XML),
                        Response.class);
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            navigation = "departmentCreated";
            refreshDepartments();
            refreshAverageSalary();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Couldn't create department."));
        }
        return navigation;
    }

    public String delete() {
        String navigation = "departmentError";
        Department department = (Department) FacesContext.getCurrentInstance()
                .getExternalContext().getRequestMap().get("department");
        if (department == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Couldn't delete department - it's null."));
            return navigation;
        }

        Response response = client.target(serviceURL)
                .path(department.getId().toString())
                .request()
                .delete();
        if (Response.Status.NO_CONTENT.getStatusCode() == response.getStatus()) {
            navigation = "departmentDeleted";
            refreshDepartments();
            refreshAverageSalary();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Couldn't delete department."));
        }
        return navigation;
    }

    public String update(Department department) {
        String navigation = "departmentError";
        if (department == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Couldn't update department - it's null."));
            return navigation;
        }

        Response response = client.target(serviceURL)
                .path("/")
                .request()
                .put(Entity.entity(department, MediaType.APPLICATION_XML), Response.class);
        if (Response.Status.SEE_OTHER.getStatusCode() == response.getStatus()) {
            navigation = "departmentUpdated";
            refreshDepartments();
            refreshAverageSalary();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Couldn't update department."));
        }
        return navigation;
    }

    /**Finds average salary for current Department instance.
     *
     * @param department for search
     * @return average salary
     */
    public float findAvgSalary(Department department) {
        if (department == null){
            throw new NullPointerException("department is null!");
        }
        
        return getAverageSalary().entrySet().stream()
                .filter(x -> x.getKey().equals(department))
                .findFirst()
                .get()
                .getValue();
    }
}
