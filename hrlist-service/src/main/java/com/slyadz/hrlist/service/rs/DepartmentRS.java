package com.slyadz.hrlist.service.rs;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.service.persistence.DepartmentDAO;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
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
//@Stateless
@Path("departments")
public class DepartmentRS implements Serializable {

    @EJB
    public DepartmentDAO ddao;
    
    @PostConstruct
    private void init() {
        System.out.println("******************** DepartmentRS ********************");                                
    }    

    @PreDestroy
    private void clear() {
        System.out.println("******************** DepartmentRS clear ********************");                                
    }    
    
    /**
     * Get a ddao value
     *
     * @return ddao value
     */
    public DepartmentDAO getDdao() {
        return ddao;
    }

    /**
     * Set a ddao value
     *
     * @param ddao
     */
    public void setDdao(DepartmentDAO ddao) {
        this.ddao = ddao;
    }

    @GET
    @Path("test")
    @Produces({MediaType.TEXT_PLAIN})
    public String test() {
        return "Hello, world!";
    }    
    /**
     * Create a new Department
     *
     * @param department
     * @throws WebApplicationException("department can not be null",
     * Response.Status.BAD_REQUEST) if department is null
     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
     * in other error cases
     * @return Response - created(URI.create("/" + departmentId))
     */
    @POST
    //@Path("/")
    public Response create(Department department) {
        if (department == null) {
            throw new WebApplicationException("department can not be null", Response.Status.BAD_REQUEST);
        }
        
        try {
            Long departmentId = getDdao().create(department);
            return Response.created(URI.create("/" + departmentId)).entity(department).build();
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get a department by the Id
     *
     * @param departmentId
     * @throws WebApplicationException("departmentId can not be null",
     * Response.Status.BAD_REQUEST) if department is null
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
     * null
     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
     * in other error cases
     * @return department with a particular Id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Department findById(@PathParam("id") Long departmentId) {
        if (departmentId == null) {
            throw new WebApplicationException("departmentId can not be null", Response.Status.BAD_REQUEST);
        }

        Department result = null;
        try {
            result = getDdao().findById(departmentId);
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        if (result == null) {
            throw new WebApplicationException("can't find the department", Response.Status.NOT_FOUND);
        }

        return result;
    }

    /**
     * Fetch all items from a DB
     *
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
     * null
     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
     * in other error cases
     * @return List<Department>
     */
    @GET
    //@Path("/")
    @Produces({MediaType.APPLICATION_XML})
    public List<Department> findAll() {
        List<Department> result = null;

        try {
            result = getDdao().findAll();
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        if (result == null) {
            throw new WebApplicationException("can't find departments", Response.Status.NOT_FOUND);
        }
        
        return result;
    }

    /**
     * Update the department
     *
     * @param department for an update
     * @throws WebApplicationException("department can not be null",
     * Response.Status.BAD_REQUEST) if department is null
     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
     * in other error cases
     * @return Response ok().status(303)
     */
    @PUT
    //@Path("/")
    @Consumes({MediaType.APPLICATION_XML})
    public Response update(Department department) {
        if (department == null) {
            throw new WebApplicationException("department can not be null", Response.Status.BAD_REQUEST);
        }
        
        try {
            getDdao().update(department);
            return Response.ok().status(303).build();
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete a department
     *
     * @param departmentId department's Id
     * @throws WebApplicationException("departmentId can not be null",
     * Response.Status.BAD_REQUEST) if department is null
     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
     * if can't remove
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long departmentId) {
        if (departmentId == null) {
            throw new WebApplicationException("departmentId can not be null",
                    Response.Status.BAD_REQUEST);
        }
        
        try {
            getDdao().delete(findById(departmentId));
        } catch (Exception e) {
             throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
