/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author A.G. Slyadz
 */
@Entity
@XmlRootElement
public class Employee implements Serializable {

    private static final long serialVersionUID = 2118724784265319992L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(max = 100)
    @NotNull
    private String name;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthday;
    private Float salary;

    public Employee() {
    }

    public Employee(String name, Date birthday, Float salary) {
        this.name = name;
        this.birthday = birthday;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
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
        hash += (name != null ? name.hashCode() : 0);
        hash += (birthday != null ? birthday.hashCode() : 0);
        hash += (salary != null ? salary.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        boolean result = true;

        if (!(object instanceof Employee)) {
            result = false;
        }

        Employee other = (Employee) object;
        if (result && !this.id.equals(other.id)) {
            result = false;
        }
        if (result && !this.name.equals(other.name)) {
            result = false;
        }
        if (result && !this.birthday.equals(other.birthday)) {
            result = false;
        }
        if (result && !this.salary.equals(other.salary)) {
            result = false;
        }
        return result;
    }
    
    @Override
    public String toString() {
        return "com.slyadz.hrlist.entity.Employee[id=" + id + ", name=" + name
                + ", birtday=" + birthday + ", salary=" + salary + "]";
    }

}
