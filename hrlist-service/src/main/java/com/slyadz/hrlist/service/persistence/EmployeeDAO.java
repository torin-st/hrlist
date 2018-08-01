/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.service.persistence;

import com.slyadz.hrlist.entity.Department_;
import com.slyadz.hrlist.service.ws.Employee;
import com.slyadz.hrlist.service.ws.Employee_;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author user
 */
@Stateless
public class EmployeeDAO {//extends AbstractFacade<Employee> {

    //@PersistenceContext(unitName = "hrlist")
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;

    public EntityTransaction getEt() {
        return et;
    }

    public void setEt(EntityTransaction et) {
        this.et = et;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EmployeeDAO() {
        emf = Persistence.createEntityManagerFactory("hrlist");
        em = emf.createEntityManager();
        et = em.getTransaction();
    }

    public List<Employee> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEm().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Employee.class));
        getEt().begin();
        List <Employee> result = getEm().createQuery(cq).getResultList();
        getEt().commit();
        return result;
    }    
        
    public Employee findById(long id) {
        getEt().begin();
        Employee result = getEm().find(Employee.class, id);
        getEt().commit();
        return result;
    }    
    
    public List<Employee> findByDepartment(long departmentId) {
        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> e = cq.from(Employee.class);        
        cq.select(e);
        cq.where(cb.equal(e.get(Employee_.department).get(Department_.id), departmentId));
        getEt().begin();
        List <Employee> result = getEm().createQuery(cq).getResultList();
        getEt().commit();
        return result;
    }    

    public void delete(Employee entity) {
        getEt().begin();
        getEm().remove(getEm().merge(entity));
        getEt().commit();
    }
    
    public long create(Employee employee) {
        getEt().begin();
        getEm().persist(employee);
        getEt().commit();
        return employee.getId();
    }
    
    public void update(Employee entity) {
        getEt().begin();
        getEm().merge(entity);
        getEt().commit();
    }    
    
    @PreDestroy
    private void clean() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

}
