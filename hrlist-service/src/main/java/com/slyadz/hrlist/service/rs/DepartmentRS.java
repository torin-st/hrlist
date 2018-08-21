package com.slyadz.hrlist.service.rs;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.service.persistence.DepartmentDAO;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Performs CRUD-operations with Department entities.
 *
 * @author A.G. Slyadz
 */
@Stateless
@Path("departments")
public class DepartmentRS implements Serializable {
    
    @Inject
    public DepartmentDAO ddao;

    public DepartmentDAO getDdao() {
        return ddao;
    }

    public void setDdao(DepartmentDAO ddao) {
        this.ddao = ddao;
    }
    
    /**
     * Creates a Department
     *
     * @param department
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
     * null
     * @return Response
     */
    @POST
    @Path("/")
    public Response create(Department department) {
        try {
            Long departmentId = getDdao().create(department);
            return Response.created(URI.create("/" + departmentId)).build();
        } catch (Exception e) {
            System.out.println("create error: " + e.getMessage());
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }    
    
     /**
     * Get department by Id
     *
     * @param departmentId
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
     * null
     * @return Department
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Department findById(@PathParam("id") Long departmentId) {
        Department result = null;
        try {
            result = getDdao().findById(departmentId);
            if (result == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println("findById: " + e.getMessage());
        }
        return result;
    }
    
    /**
     * Fetches all items from DB
     *
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
     * null
     *
     * @return all departments
     */
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_XML})
    public List<Department> findAll() {
        List<Department> result = null;

        try {
            result = getDdao().findAll();
            if (result == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println("findAll(): " + e.getMessage());
        }
        
        return result;        
    }
    
    /**
     * Updates the department
     *
     * @param department
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if can't find
     * old department
     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
     * if can't merge
     * @return Response
     */
    @PUT
    @Path("/")
    @Consumes({MediaType.APPLICATION_XML})
    public Response update(Department department) {
        if (department == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        try {
            getDdao().update(department);
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.ok().status(303).build();
    } 
    
    /**
     * Delete the department
     *
     * @param departmentId department's Id
     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
     * if can't remove
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long departmentId) {
        if (departmentId == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }        
        try {
            getDdao().delete(findById(departmentId));
        } catch (Exception e) {
            System.out.println("Error while calling removeDepartment(): " + e.getMessage());
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
      
}

///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.slyadz.hrlist.service.rs;
//
//import com.slyadz.hrlist.entity.Department;
//import com.slyadz.hrlist.entity.Department_;
//import java.io.Serializable;
//import java.net.URI;
//import java.util.List;
//import javax.annotation.PreDestroy;
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import javax.persistence.metamodel.EntityType;
//import javax.persistence.metamodel.Metamodel;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
///**
// * Performs CRUD-operations with Department entities.
// *
// * @author A.G. Slyadz
// */
//@Stateless
//@Path("departments")
//public class DepartmentRS implements Serializable {
//
//    private EntityManagerFactory emf;
//    private EntityManager em;
//    private EntityTransaction et;
//
//    public DepartmentRS() {
//        emf = Persistence.createEntityManagerFactory("hrlist");
//        em = emf.createEntityManager();
//        et = em.getTransaction();
//    }
//
//    public void setEntityManagerFactory(EntityManagerFactory emf) {
//        this.emf = emf;
//        em = emf.createEntityManager();
//        et = em.getTransaction();
//    }
//
//    public EntityManager getEntityManager() {
//        return em;
//    }
//    @PreDestroy
//    private void clean() {
//        if (em != null) {
//            em.close();
//        }
//        if (emf != null) {
//            emf.close();
//        }
//    }
//    /**
//     * Get all departments
//     *
//     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
//     * null
//     *
//     * @return List<Department> - all departments
//     */
//    @GET
//    @Path("/")
//    @Produces({MediaType.APPLICATION_XML})
//    public List<Department> getAllDepartments() {
//        List<Department> result = null;
//
//        try {
//            result = findAllDepartments();
//            if (result == null) {
//                throw new WebApplicationException(Response.Status.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            System.out.println("Error while calling findAllDepartments(): " + e.getMessage());
//        }
//        
//        return result;
//        
//    }
//    /**
//     * Get department by Id
//     *
//     * @param: Long departmentId
//     *
//     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
//     * null
//     *
//     * @return Department
//     */
//    @GET
//    @Path("{id}")
//    @Produces({MediaType.APPLICATION_XML})
//    public Department getDepartmentById(@PathParam("id") Long departmentId) {
//        Department result = null;
//        try {
//            result = findDepartmentById(departmentId);
//            if (result == null) {
//                throw new WebApplicationException(Response.Status.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            System.out.println("Error while calling findDepartmentById(): " + e.getMessage());
//        }
//        return result;
//    }
//    /**
//     * Get department by name
//     *
//     * @param departmentName - the name of the department
//     *
//     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
//     * null
//     *
//     * @return Department
//     */
//    @GET
//    @Path("/getbyname/{departmentName}")
//    @Produces({MediaType.APPLICATION_XML})
//    public Department getDepartmentByName(@PathParam("departmentName") String departmentName) {
//        Department result = null;
//        try {
//            result = findDepartmentByName(departmentName);
//            if (result == null) {
//                throw new WebApplicationException(Response.Status.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            System.out.println("Error while calling findDepartmentByName(): " + e.getMessage());
//        }
//        return result;
//    }
//    
//    /**
//     * Create a Department
//     *
//     * @param department
//     *
//     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
//     * null
//     *
//     * @return Response
//     */
//    @POST
//    @Path("/")
//    public Response createDepartment(Department department) {
//        try {
//            Long departmenId = persistDepartment(department);
//            return Response.created(URI.create("/" + departmenId)).build();
//        } catch (Exception e) {
//            System.out.println("Error while calling persistDepartment(): " + e.getMessage());
//            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
//        }
//    }
//    /**
//     * Delete a Department
//     *
//     * @param departmentId
//     *
//     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
//     * if can't remove
//     */
//    @DELETE
//    @Path("{id}")
//    public void deleteDepartment(@PathParam("id") Long departmentId) {
//        try {
//            removeDepartment(departmentId);
//        } catch (Exception e) {
//            System.out.println("Error while calling removeDepartment(): " + e.getMessage());
//            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Update a Department
//     *
//     * @param department
//     *
//     * @throws WebApplicationException(Response.Status.NOT_FOUND) if can't find
//     * old department
//     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
//     * if can't merge
//     * 
//     * @return Response
//     */
//    @PUT
//    @Path("/")
//    @Consumes({MediaType.APPLICATION_XML})
//    public Response updateDepartment(Department department) {
//        if (department == null) {
//            throw new WebApplicationException(Response.Status.NOT_FOUND);
//        }
//        try {
//            mergeDepartment(department);
//        } catch (Exception e) {
//            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
//        }
//        return Response.ok().status(303).build();
//    }
//
//    //Criteria query with a static metamodel
//    //
//    private List<Department> findAllDepartments() {
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
//        Root<Department> department = cq.from(Department.class);
//        cq.select(department);
//        cq.orderBy(cb.asc(department.get(Department_.id)));
//        TypedQuery<Department> q = getEntityManager().createQuery(cq);
//        return q.getResultList();        
//    }
//
//    //Criteria query with a dynamic metamodel
//    //
//    private Department findDepartmentById(Long departmentId) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        Metamodel m = em.getMetamodel();
//        EntityType<Department> Dep_ = m.entity(Department.class);
//        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
//        Root<Department> department = cq.from(Department.class);
//        cq.select(department);
//        cq.where(cb.equal(department.get(Dep_.getSingularAttribute("id")), departmentId));
//        TypedQuery<Department> q = em.createQuery(cq);
//        return q.getSingleResult();
//    }
//
//    //Criteria query with a dynamic metamodel
//    //
//    private Department findDepartmentByName(String departmentName) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        Metamodel m = em.getMetamodel();
//        EntityType<Department> Dep_ = m.entity(Department.class);
//        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
//        Root<Department> department = cq.from(Department.class);
//        cq.select(department);
//        cq.where(cb.equal(department.get(Dep_.getSingularAttribute("name")), departmentName));
//        TypedQuery<Department> q = em.createQuery(cq);
//        return q.getSingleResult();
//    }
//    //Persisting new object to DB
//    //
//    private Long persistDepartment(Department department) throws Exception {
//        try {
//            et.begin();
//            em.persist(department);
//            et.commit();
//        } catch (Exception e) {
//            System.out.println("Error while calling persistDepartment(): " + e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//        return department.getId();
//    }
//
//    private boolean removeDepartment(Long departmentId) throws Exception {
//        Department department;
//
//        try {
//            department = em.find(Department.class, departmentId);
//            et.begin();
//            em.remove(department);
//            et.commit();
//        } catch (Exception e) {
//            System.out.println("Error while calling removeDepartment(): " + e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//        return true;
//    }
//
//    private void mergeDepartment(Department department) throws Exception {
//        try {
//            et.begin();
//            em.merge(department);
//            et.commit();
//        } catch (Exception e) {
//            System.out.println("Error while calling mergeDepartment(): " + e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//    }
//}
