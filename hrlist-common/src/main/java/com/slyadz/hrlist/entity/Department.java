package com.slyadz.hrlist.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // the same object
        if (this == object) {
            return true;
        }
        // not the instance of the same class
        if (!(object instanceof Department)) {
            return false;
        }

        boolean result = true;        
        Department other = (Department) object;
        
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
        return result;
    }

    @Override
    public String toString() {
        return "com.slyadz.hrlist.entity.Department[id=" + id + ", name=" + name + "]";
    }
}
