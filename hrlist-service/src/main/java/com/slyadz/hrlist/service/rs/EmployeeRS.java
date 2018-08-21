package com.slyadz.hrlist.service.rs;

import com.slyadz.hrlist.entity.Employee;
import com.slyadz.hrlist.service.persistence.EmployeeDAO;
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
 * Performs CRUD-operations with Employee entities.
 *
 * @author A.G. Slyadz
 */
@Stateless
@Path("employees")
public class EmployeeRS implements Serializable {
    
    @Inject
    public EmployeeDAO edao;

    public EmployeeDAO getEdao() {
        return edao;
    }

    public void setDdao(EmployeeDAO edao) {
        this.edao = edao;
    }
    
    /**
     * Creates a Employee
     *
     * @param employee
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
     * null
     * @return Response
     */
    @POST
    @Path("/")
    public Response create(Employee employee) {
        Response result = null;
        
        try {
            Long employeeId = getEdao().create(employee);
            result = Response.created(URI.create("/" + employeeId)).build();
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
        
        return result;
    }    
    
     /**
     * Gets employee by Id
     *
     * @param employeeId
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
     * null
     * @return Employee
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Employee findById(@PathParam("id") Long employeeId) {
        Employee result = null;
        
        try {
            result = getEdao().findById(employeeId);
            if (result == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return result;
    }
    
     /**
     * Fetches items by department's Id
     *
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
     * null
     *
     * @return all employees
     */
    @GET
    @Path("/find_by_department_id/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Employee> findByDepartmentId(@PathParam("id") Long departmentId) {
        List<Employee> result = null;

        try {
            result = getEdao().findByDepartmentId(departmentId);
            if (result == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
        
        return result;        
    }    
    /**
     * Fetches all items from DB
     *
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if result is
     * null
     *
     * @return all employees
     */
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_XML})
    public List<Employee> findAll() {
        List<Employee> result = null;

        try {
            result = getEdao().findAll();
            if (result == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return result;        
    }
    
    /**
     * Updates the employee
     *
     * @param employee
     * @throws WebApplicationException(Response.Status.NOT_FOUND) if can't find
     * old employee
     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
     * if can't merge
     * @return Response
     */
    @PUT
    @Path("/")
    @Consumes({MediaType.APPLICATION_XML})
    public Response update(Employee employee) {
        if (employee == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        try {
            getEdao().update(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());            
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
        
        return Response.ok().status(303).build();
    } 
    
    /**
     * Delete the employee
     *
     * @param employeeId employee's Id
     * @throws WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR)
     * if can't remove
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long employeeId) {
        if (employeeId == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } 
        
        try {
            getEdao().delete(findById(employeeId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
      
}
