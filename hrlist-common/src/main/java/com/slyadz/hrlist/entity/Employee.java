package com.slyadz.hrlist.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
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
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    public Employee() {
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee(String name, Date birthday, Float salary) {
        this.name = name;
        this.birthday = birthday;
        this.salary = salary;
    }

    public Employee(String name, Float salary) {
        this.name = name;
        this.salary = salary;
    }

    public Employee(String name) {
        this.name = name;
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
        hash += (department != null ? department.hashCode() : 0);        
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // the same object
        if (this == object) {
            return true;
        }
        // not the instance of the same class
        if (!(object instanceof Employee)) {
            return false;
        }

        boolean result = true;        
        Employee other = (Employee) object;
        
        if (other.id == null){
            result = (this.id == null);
        } else {
            result = (this.id != null ? this.id.equals(other.id) : false );
        }

        if (result && (other.name == null)){
            result = (this.name == null);
        } else {
            result = (this.name != null ? this.name.equals(other.name) : false );
        }

        if (result && (other.birthday == null)){
            result = (this.birthday == null);
        } else {
            result = (this.birthday != null ? this.birthday.equals(other.birthday) : false );
        }

        if (result && (other.salary == null)){
            result = (this.salary == null);
        } else {
            result = (this.salary != null ? this.salary.equals(other.salary) : false );
        }
        
        if (result && (other.department == null)){
            result = (this.department == null);
        } else {
            result = (this.department != null ? this.department.equals(other.department) : false );
        }        
        
        return result;
    }

    @Override
    public String toString() {
        return "com.slyadz.hrlist.entity.Employee[id=" + id + ", dep=" + department + "]";
    }
}
