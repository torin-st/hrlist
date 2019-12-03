package com.slyadz.hrlist.service.persistence;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Parent class for all DAO-classes
 *
  * @param <T> entity type
 */
public abstract class AbstractDAO<T> {

    private Class<T> entityClass;
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;

    /**
     * Constructor
     *
     * @param entityClass entity class
     * @param PU persistence unit name
     */
    public void init(Class<T> entityClass, String PU) {
        try {
            this.entityClass = entityClass;
            this.emf = Persistence.createEntityManagerFactory(PU);
            this.em = emf.createEntityManager();
            this.et = em.getTransaction();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Gets the value of entityClass
     *
     * @return the value of entityClass
     */
    private Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * Gets the value of emf
     *
     * @return the value of emf
     */
    protected EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * Gets the value of em
     *
     * @return the value of em
     */
    protected EntityManager getEm() {
        return em;
    }

    /**
     * Gets the value of et
     *
     * @return the value of et
     */
    protected EntityTransaction getEt() {
        return et;
    }

    /**
     * Persist the entity to db
     *
     * @param entity
     * @return entity's Id
     * @throws java.io.IOException
     */
    abstract public Long create(T entity) throws IOException;

    /**
     * Fetches item from DB by it's Id
     *
     * @param id item's Id
     * @return item's entity
     * @throws java.io.IOException
     */
    public T findById(Long id) throws IOException {
        T result = null;

        try {
            getEt().begin();
            result = getEm().find(getEntityClass(), id);
            getEt().commit();
        } catch (Exception e) {
            getEt().rollback();
            throw new IOException(e.getMessage());
        }
        return result;
    }

    /**
     * Fetches all items from DB
     *
     * @return items from db
     * @throws java.io.IOException
     */
    public List<T> findAll() throws IOException {
        List<T> result = null;

        try {
            CriteriaQuery cq = getEm().getCriteriaBuilder().createQuery();
            cq.select(cq.from(getEntityClass()));
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
     * Updates entity to DB
     *
     * @param entity
     * @throws java.io.IOException
     */
    public void update(T entity) throws IOException {
        try {
            getEt().begin();
            getEm().merge(entity);
            getEt().commit();
        } catch (Exception e) {
            getEt().rollback();
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Removes entity from DB
     *
     * @param entity
     * @throws java.io.IOException
     */
    public void delete(T entity) throws IOException {
        try {
            getEt().begin();
            getEm().remove(getEm().merge(entity));
            getEt().commit();
        } catch (Exception e) {
            getEt().rollback();
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Closes EntityManager and EntityMangerFactory if they are not null
     */
    protected void clean() {
        if (getEm() != null) {
            getEm().close();
        }
        if (getEmf() != null) {
            if (getEmf().isOpen()) {
                getEmf().close();
            }
        }
    }

}
