package com.slyadz.hrlist.service.persistence;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import com.slyadz.hrlist.entity.Employee_;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * DAO class for Employee entity
 */
@Stateless
public class EmployeeDAO extends AbstractDAO<Employee> {

    @Inject
    @PersistenceUnitName
    private NameReturner nameReturner;

    public NameReturner getNameReturner() {
        return nameReturner;
    }

    public void setNameReturner(NameReturner nameReturner) {
        this.nameReturner = nameReturner;
    }

    /**
     * Constructor
     */
    public EmployeeDAO() {
    }

    @PostConstruct
    private void init() {
        super.init(Employee.class, getNameReturner().getPersistenceUnitName());
    }

    /**
     * Persist the department to db
     *
     * @param employee
     * @return department's Id
     * @throws java.io.IOException
     */
    @Override
    public Long create(Employee employee) throws IOException {
        try {
            getEt().begin();
            employee.setDepartment(getEm().find(Department.class, employee.getDepartment().getId()));
            getEm().persist(employee);
            getEt().commit();
        } catch (Exception e) {
            getEt().rollback();
            throw new IOException(e.getMessage());
        }
        return employee.getId();
    }

     /**
     * Fetches items by birthday range
     *
     * @param fromDate
     * @param tillDate
     * @return list of employees by id
     * @throws java.io.IOException
     */    
    public List<Employee> findByBirthdayRange(Date fromDate, Date tillDate) throws IOException {
        List <Employee> result = null;
        try {
            CriteriaBuilder cb = getEm().getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> e = cq.from(Employee.class);
            cq.select(e);
            cq.where( cb.between(e.<Date>get(Employee_.birthday), fromDate, tillDate) );
            getEt().begin();
            result = getEm().createQuery(cq).getResultList();
            getEt().commit();
        } catch (Exception e) {
            getEt().rollback();
            throw new IOException(e.getMessage());
        }
        return result;
    }    
    /**
     * Closes EntityManager and EntityMangerFactory if they are not null.
     */
    @PreDestroy
    @Override
    protected void clean() {
        super.clean();
    }

}
