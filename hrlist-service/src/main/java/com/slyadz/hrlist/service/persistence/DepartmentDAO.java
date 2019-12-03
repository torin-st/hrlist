package com.slyadz.hrlist.service.persistence;

import com.slyadz.hrlist.entity.Department;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * DAO class for Department entity
 */
@Stateless
public class DepartmentDAO extends AbstractDAO<Department> {

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
    public DepartmentDAO() {
    }

    @PostConstruct
    private void init() {
        super.init(Department.class, getNameReturner().getPersistenceUnitName());
    }

    /**
     * Persist the department to db
     *
     * @param department
     * @return department's Id
     * @throws java.io.IOException
     */
    @Override
    public Long create(Department department) throws IOException {
        try {
            getEt().begin();
            getEm().persist(department);
            getEt().commit();
        } catch (Exception e) {
            getEt().rollback();
            throw new IOException(e.getMessage());
        }
        return department.getId();
    }

    /**
     * Closes EntityManager and EntityMangerFactory if they are not null
     */
    @PreDestroy
    @Override
    protected void clean() {
        super.clean();
    }

}
