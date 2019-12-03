package com.slyadz.hrlist.service.rs;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.service.persistence.DepartmentDAO;
import java.io.IOException;
import java.net.URI;
import javax.ws.rs.WebApplicationException;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * Perform module tests for the DepartmentRS class
 */
public class DepartmentRSTest {

    private DepartmentRS departmentRS;
    private DepartmentDAO ddaoMock;
    private Department department;
    @Rule
    public ExpectedException expectedException;

    public DepartmentRSTest() {
        departmentRS = new DepartmentRS();
        ddaoMock = mock(DepartmentDAO.class);
        departmentRS.setDdao(ddaoMock);
        expectedException = ExpectedException.none();
        department = new Department("test");
        department.setId(1L);
    }
    /**
     * Send a null-value to the create() method and check if it will throw
     * a WebApplicationException with a correct message
     */
    @Test
    public void testCreateNullException() {
        expectedException.expect(WebApplicationException.class);
        expectedException.expectMessage("department can not be null");
        departmentRS.create(null);
    }

    /**
     * Throw an IllegalArgumentException during getDdao.create() invocation 
     * and check if it will catch it and throw WebApplicationException
     */
    @Test
    public void testCreateDdaoException() throws IOException {
        expectedException.expect(WebApplicationException.class);
        doThrow(IllegalArgumentException.class).when(ddaoMock).create(department);
        departmentRS.create(department);
    }

    /**
     * Throw an IOException during URI.create() invocation 
     * and check if it will catch it and throw WebApplicationException
     */
    @Test
    @PrepareForTest({URI.class})
    public void testCreateURIException() throws Exception {
        expectedException.expect(WebApplicationException.class);
        doReturn(1L).when(ddaoMock).create(department);
        PowerMockito.spy(URI.class);
        PowerMockito.doThrow(new IOException()).when(URI.class, "create", new Object[]{"/1"});
        departmentRS.create(department);
    }

    /**
     * Send a null-value to the findById() method and check if it will throw
     * a WebApplicationException with a correct message
     */
    @Test
    public void testFindByIdNullException() {
        expectedException.expect(WebApplicationException.class);
        expectedException.expectMessage("departmentId can not be null");
        departmentRS.findById(null);
    }    
    
    /**
     * Throw an IllegalArgumentException during getDdao.findById() invocation 
     * and check if it will catch it and throw WebApplicationException
     */
    @Test
    public void testFindByIdDdaoException() throws IOException {
        expectedException.expect(WebApplicationException.class);
        doThrow(IllegalArgumentException.class).when(ddaoMock).findById(1L);
        departmentRS.findById(1L);
    }    
    
    /**
     * Get a null-value from getDdao.findById() method and check if it will throw
     * a WebApplicationException with a correct message
     */
    @Test
    public void testFindByIdNullReturn() throws IOException {
        expectedException.expect(WebApplicationException.class);
        expectedException.expectMessage("can't find the department");
        doReturn(null).when(ddaoMock).findById(1L);
        departmentRS.findById(1L);
    }    

    /**
     * Throw an IllegalArgumentException during getDdao.findAll() invocation 
     * and check if it will catch it and throw WebApplicationException
     */
    @Test
    public void testFindAllDdaoException() throws IOException {
        expectedException.expect(WebApplicationException.class);
        doThrow(IllegalArgumentException.class).when(ddaoMock).findAll();
        departmentRS.findAll();
    }        
    
    /**
     * Get a null-value from getDdao.findAll() method and check if it will throw
     * a WebApplicationException with a correct message
     */
    @Test
    public void testFindAllNullReturn() throws IOException {
        expectedException.expect(WebApplicationException.class);
        expectedException.expectMessage("can't find departments");
        doReturn(null).when(ddaoMock).findAll();
        departmentRS.findAll();
    }        
    
    /**
     * Send a null-value to the update() method and check if it will throw
     * a WebApplicationException with a correct message
     */
    @Test
    public void testUpdateNullException() {
        expectedException.expect(WebApplicationException.class);
        expectedException.expectMessage("department can not be null");
        departmentRS.update(null);
    }        
    
    /**
     * Throw an IllegalArgumentException during getDdao.update() invocation 
     * and check if it will catch it and throw WebApplicationException
     */
    @Test
    public void testUpdateDdaoException() throws IOException {
        expectedException.expect(WebApplicationException.class);
        doThrow(IllegalArgumentException.class).when(ddaoMock).update(department);
        departmentRS.update(department);
    }            
    
    /**
     * Send a null-value to the delete() method and check if it will throw
     * a WebApplicationException with a correct message
     */
    @Test
    public void testDeleteNullException() {
        expectedException.expect(WebApplicationException.class);
        expectedException.expectMessage("departmentId can not be null");
        departmentRS.delete(null);
    }   
    
    /**
     * Throw an IllegalArgumentException during getDdao.findById() invocation 
     * and check if it will catch it and throw WebApplicationException
     */
    @Test
    public void testDeleteDdaoException() throws IOException {
        expectedException.expect(WebApplicationException.class);
        doThrow(IllegalArgumentException.class).when(ddaoMock).findById(1L);
        departmentRS.delete(1L);
    }                

}
