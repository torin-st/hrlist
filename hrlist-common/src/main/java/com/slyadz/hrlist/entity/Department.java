/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.entity;

import com.slyadz.hrlist.service.ws.Employee;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author A.G. Slyadz
 */
@Entity
@XmlRootElement
public class Department implements Serializable {

    private static final long serialVersionUID = -1142487944346587665L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(max = 30)
    @NotNull
    private String name;
    @OneToMany
    @JoinColumn(name = "DEPARTMENT_FK")
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += name.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        boolean result = true;

        if (!(object instanceof Department)) {
            result = false;
        }

        Department other = (Department) object;
        if (result && !this.id.equals(other.id)) {
            result = false;
        }
        if (result && !this.name.equals(other.name)) {
            result = false;
        }
        return result;
    }

    @Override
    public String toString() {
        return "com.slyadz.hrlist.entity.Department[id=" + id + ", name=" + name + "]";
    }

}
