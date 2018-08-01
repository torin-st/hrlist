/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.entity.Department;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private com.slyadz.hrlist.service.ws.Department department;

    public List<com.slyadz.hrlist.service.ws.Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<com.slyadz.hrlist.service.ws.Department> departments) {
        this.departments = departments;
    }
    private List<com.slyadz.hrlist.service.ws.Department> departments;

    public DepartmentBean() {
    }

    public com.slyadz.hrlist.service.ws.Department getDepartment() {
        return department;
    }

    public void setDepartment(com.slyadz.hrlist.service.ws.Department department) {
        this.department = department;
    }

    @PostConstruct
    private void init() {
        client = ClientBuilder.newClient();
        department = new com.slyadz.hrlist.service.ws.Department();
        departments = getAllDepartments();
        System.out.println("DepartmentBean: postConstruct");
    }

    @PreDestroy
    private void clean() {
        client.close();
        System.out.println("DepartmentBean: preDestroy");        
    }
    
    public String prepareDepartment(){
        String navigation = "departmentPrepared";
        setDepartment(new com.slyadz.hrlist.service.ws.Department());
        return navigation;
    }

    public Department getDepartmentById(Long departmentId) {
        return client.target(serviceURL)
                .path(departmentId.toString())
                .request(MediaType.APPLICATION_XML)
                .get(Department.class);
    }    
    
    public Department getDepartmentByName(String departmentName) {
        return client.target(serviceURL)
                .path("/getbyname/" + departmentName)
                .request(MediaType.APPLICATION_XML)
                .get(Department.class);
    }
    
//    public List<Department> getAllDepartments() {
//        return client.target(serviceURL)
//                .path("/")
//                .request(MediaType.APPLICATION_XML)
//                .get(new GenericType<List<Department>>() {
//                });
//    }
    public List<com.slyadz.hrlist.service.ws.Department> getAllDepartments() {
        ArrayList<com.slyadz.hrlist.entity.Department> mock = client.target(serviceURL)
                .path("/")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<ArrayList<com.slyadz.hrlist.entity.Department>>() {
                });
        List<com.slyadz.hrlist.service.ws.Department> result = new ArrayList<>();
        for (int i = 0; i < mock.size(); i++) {
            com.slyadz.hrlist.entity.Department get = mock.get(i);
            com.slyadz.hrlist.service.ws.Department t = new com.slyadz.hrlist.service.ws.Department();
            t.setId(get.getId());
            t.setName(get.getName());
            result.add(t);            
        }
        return result;
    }

    public String createDepartment(Department department) {
        if (department == null) {
            return "departmentError";
        }
        String navigation;
        Response response = client.target(serviceURL)
                .path("/")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(department, MediaType.APPLICATION_XML),
                        Response.class);
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            navigation = "departmentCreated";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Couldn't create department."));
            navigation = "departmentError";
        }
        return navigation;
    }

    public String deleteDepartment() {
        String navigation = "departmentError";
        Department department = (Department) FacesContext.getCurrentInstance()
                .getExternalContext().getRequestMap().get("department");
        if (department == null) {
            return navigation;
        }

        Response response = client.target(serviceURL)
                        .path(department.getId().toString())
                        .request()
                        .delete();
        if (Response.Status.NO_CONTENT.getStatusCode() == response.getStatus()) {
            navigation = "departmentDeleted";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                    new FacesMessage("Couldn't delete department."));
            navigation = "departmentError";
        }
        return navigation;
    }

    public String updateDepartment(Department department) {
        String navigation = "departmentError";
        if (department == null) {
            return navigation;
        }

        Response response
                = client.target(serviceURL)
                        .path("/")
                        .request()
                        .put(Entity.entity(department, MediaType.APPLICATION_XML), Response.class);
        if (Response.Status.SEE_OTHER.getStatusCode() == response.getStatus()) {
            navigation = "departmentUpdated";
        } else {
            System.out.println("==" + response.getStatus());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                    new FacesMessage("Couldn't update department."));
        }
        return navigation;
    }
}
