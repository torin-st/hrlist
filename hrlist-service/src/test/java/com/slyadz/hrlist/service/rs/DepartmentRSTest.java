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
 * Perform module test for DepartmentRS class
 *
 * @author A.G. Slyadz
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
        System.out.println("testCreateNullException");
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
        System.out.println("testCreateOtherException");
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
        System.out.println("testCreateURIException");
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
        System.out.println("testFindByIdNullException");
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
        System.out.println("testFindByIdDdaoException");
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
        System.out.println("testFindByIdNullReturn");
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
        System.out.println("testFindAllDdaoException");
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
        System.out.println("testFindAllNullReturn");
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
        System.out.println("testUpdateNullException");
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
        System.out.println("testUpdateDdaoException");
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
        System.out.println("testDeleteNullException");
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
        System.out.println("testDeleteDdaoException");
        expectedException.expect(WebApplicationException.class);
        doThrow(IllegalArgumentException.class).when(ddaoMock).findById(1L);
        departmentRS.delete(1L);
    }                
//    /**
//     * FOR STATIC NOT FINAL
//     */
//    public void testTestResponseException() throws Exception {
//        System.out.println("testResponseException");
//        expectedException.expect(WebApplicationException.class);
//        PowerMockito.spy(Response.class);
//        PowerMockito.when(Response.class, "accepted").thenThrow(IOException.class);
//        departmentRS.test();
//    }
}
