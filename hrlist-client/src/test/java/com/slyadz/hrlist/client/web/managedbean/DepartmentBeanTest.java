package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author A.G. Slyadz
 */
@RunWith(PowerMockRunner.class)
public class DepartmentBeanTest {
    
    @Rule
    public ExpectedException expectedException;

    public DepartmentBeanTest() {
        expectedException = ExpectedException.none();
    }

    /**
     * Test of getServiceURL method, of class DepartmentBean.
     */
    @Test
    public void testGetServiceURL() {
        System.out.println("testGetServiceURL");
        DepartmentBean instance = new DepartmentBean();
        String expResult = "http://localhost:8080/hrlist-service/api/departments";
        String result = instance.getServiceURL();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOutcomePrefix method, of class DepartmentBean.
     */
    @Test
    public void testGetOutcomePrefix() {
        System.out.println("testGetOutcomePrefix");
        DepartmentBean instance = new DepartmentBean();
        String expResult = "Department";
        String result = instance.getOutcomePrefix();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInstanceId method, of class DepartmentBean.
     */
    @Test
    public void testGetInstanceId() {
        System.out.println("testGetInstanceId");
        Department department = new Department();
        department.setId(1L);
        DepartmentBean instance = new DepartmentBean();
        String expResult = "1";
        String result = instance.getInstanceId(department);
        assertEquals(expResult, result);
    }

    /**
     * If success
     */
    @Test
    public void testCreateSuccess() {
        System.out.println("testCreateSuccess");
        Department department = new Department("test");
        DepartmentBean instance = new DepartmentBean();
        DepartmentBean instanceSpy = spy(instance);
        doReturn("departmentCreated").when(instanceSpy).parentCreateWrapper(department);
        doNothing().when(instanceSpy).refreshAverageSalary();
        String expResult = "departmentCreated";
        String result = instanceSpy.create(department);
        assertEquals(expResult, result);
        verify(instanceSpy).refreshAverageSalary();
    }

    /**
     * If error
     */
    @Test
    public void testCreateError() {
        System.out.println("testCreateError");
        Department department = new Department("test");
        DepartmentBean instance = new DepartmentBean();
        DepartmentBean instanceSpy = spy(instance);
        doReturn("departmentError").when(instanceSpy).parentCreateWrapper(department);
        String expResult = "departmentError";
        String result = instanceSpy.create(department);
        assertEquals(expResult, result);
        verify(instanceSpy, times(0)).refreshAverageSalary();
    }

    /**
     * If there was an exception
     */
    @Test
    public void testFindAllException() {
        System.out.println("testFindAllException");
        DepartmentBean instance = new DepartmentBean();
        DepartmentBean instanceSpy = spy(instance);
        Client mockClient = mock(Client.class);
        Exception e = new IllegalStateException();
        doThrow(e).when(mockClient).target(instance.getServiceURL());
        doNothing().when(instanceSpy).parentMessageWrapper(any(), any());
        instanceSpy.setClient(mockClient);
        List<Department> expResult = null;
        List<Department> result = instanceSpy.findAll();
        assertEquals(expResult, result);
    }

    /**
     * If there wasn't an exception
     */
    @Test
    public void testFindAll() {
        System.out.println("testFindAll");
        DepartmentBean departmentBean = new DepartmentBean();
        DepartmentBean departmentBeanSpy = spy(departmentBean);
        Client client = ClientBuilder.newClient();
        Client clientSpy = spy(client);
        departmentBeanSpy.setClient(clientSpy);
        WebTarget webTarget = client.target(departmentBean.getServiceURL());
        WebTarget webTargetSpy = spy(webTarget);
        doReturn(webTargetSpy).when(clientSpy).target(departmentBean.getServiceURL());
        doReturn(webTargetSpy).when(webTargetSpy).path("/");
        Invocation.Builder iBuilder = webTarget.request(MediaType.APPLICATION_XML);
        Invocation.Builder iBuilderSpy = spy(iBuilder);
        doReturn(iBuilderSpy).when(webTargetSpy).request(MediaType.APPLICATION_XML);
        Department d1 = new Department("dep1");
        Department d2 = new Department("dep2");
        List<Department> list = new ArrayList<>();
        list.add(d1);
        list.add(d2);
        doReturn(list).when(iBuilderSpy).get(new GenericType<List<Department>>() {
        });
        List<Department> expResult = new ArrayList<>(list);
        List<Department> result = departmentBeanSpy.findAll();
        assertEquals(expResult, result);
    }

    /**
     * If principal is null.
     */
    @Test
    public void testDeleteNullPrincipal() {
        System.out.println("testDeleteNullPrincipal");
        Department department = new Department("test");
        DepartmentBean instance = new DepartmentBean();
        DepartmentBean instanceSpy = spy(instance);
        ExternalContext externalContext = mock(ExternalContext.class);
        doReturn(externalContext).when(instanceSpy).getExternalContext();
        doReturn(null).when(externalContext).getUserPrincipal();
        String expResult = "needLogin";
        String result = instanceSpy.delete(department);
        assertEquals(expResult, result);
    }

    /**
     * If instance is null.
     */
    @Test
    public void testDeleteNullInstance() {
        System.out.println("testDeleteNullInstance");
        DepartmentBean instance = new DepartmentBean();
        DepartmentBean instanceSpy = spy(instance);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        doReturn(externalContextMock).when(instanceSpy).getExternalContext();
        Principal principalMock = mock(Principal.class);
        doReturn(principalMock).when(externalContextMock).getUserPrincipal();
        doReturn("gg").when(instanceSpy).getLocalizedMessageWrapper(any());
        doNothing().when(instanceSpy).parentMessageWrapper(any(), any());
        String expResult = "departmentError";
        String result = instanceSpy.delete(null);
        assertEquals(expResult, result);
    }

    /**
     * If the list of employees is not null.
     */
    @Test
    public void testDeleteNotNullEmployees() {
        System.out.println("testDeleteNotNullEmployees");
        DepartmentBean instance = new DepartmentBean();
        EmployeeBean employeeBeanMock = mock(EmployeeBean.class);
        List<Employee> es = new ArrayList<>();
        es.add(new Employee("e1"));
        doReturn(es).when(employeeBeanMock).findByDepartment(any());
        instance.setEmployeeBean(employeeBeanMock);
        DepartmentBean instanceSpy = spy(instance);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        doReturn(externalContextMock).when(instanceSpy).getExternalContext();
        Principal principalMock = mock(Principal.class);
        doReturn(principalMock).when(externalContextMock).getUserPrincipal();
        doReturn("gg").when(instanceSpy).getLocalizedMessageWrapper(any());
        doNothing().when(instanceSpy).parentMessageWrapper(any(), any());
        String expResult = "departmentError";
        String result = instanceSpy.delete(new Department("d1"));
        assertEquals(expResult, result);
    }

    /**
     * Error during parent's delete-method execution.
     */
    @Test
    public void testDeleteParentError() {
        System.out.println("testDeleteParentError");
        Department d1 = new Department("d1");
        DepartmentBean instance = new DepartmentBean();
        EmployeeBean employeeBeanMock = mock(EmployeeBean.class);
        doReturn(null).when(employeeBeanMock).findByDepartment(any());
        instance.setEmployeeBean(employeeBeanMock);
        DepartmentBean instanceSpy = spy(instance);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        doReturn(externalContextMock).when(instanceSpy).getExternalContext();
        Principal principalMock = mock(Principal.class);
        doReturn(principalMock).when(externalContextMock).getUserPrincipal();
        doReturn("departmentError").when(instanceSpy).parentDeleteWrapper(d1);
        doNothing().when(instanceSpy).refreshAverageSalary();
        String expResult = null;
        String result = instanceSpy.delete(d1);
        assertEquals(expResult, result);
        verify(instanceSpy, times(0)).refreshAverageSalary();        
    }    
    
    /**
     * Test delete method.
     */
    @Test
    public void testDelete() {
        System.out.println("testDelete");
        Department d1 = new Department("d1");
        DepartmentBean instance = new DepartmentBean();
        EmployeeBean employeeBeanMock = mock(EmployeeBean.class);
        doReturn(null).when(employeeBeanMock).findByDepartment(any());
        instance.setEmployeeBean(employeeBeanMock);
        DepartmentBean instanceSpy = spy(instance);
        ExternalContext externalContextMock = mock(ExternalContext.class);
        doReturn(externalContextMock).when(instanceSpy).getExternalContext();
        Principal principalMock = mock(Principal.class);
        doReturn(principalMock).when(externalContextMock).getUserPrincipal();
        doReturn("departmentDeleted").when(instanceSpy).parentDeleteWrapper(d1);
        doNothing().when(instanceSpy).refreshAverageSalary();
        String expResult = null;
        String result = instanceSpy.delete(d1);
        assertEquals(expResult, result);
        verify(instanceSpy, times(1)).refreshAverageSalary();        
    }
    
    /**
     * If there was an error.
     */
    @Test
    public void testUpdateError() {
        System.out.println("testUpdateError");
        Department d1 = new Department("d1");
        DepartmentBean instance = new DepartmentBean();
        DepartmentBean instanceSpy = spy(instance);
        doReturn("departmentError").when(instanceSpy).parentUpdateWrapper(d1);
        String expResult = "departmentError";
        String result = instanceSpy.update(d1);
        assertEquals(expResult, result);
        verify(instanceSpy, times(0)).refreshAverageSalary();        
    }    
    
    /**
     * Test update method.
     */
    @Test
    public void testUpdate() {
        System.out.println("testUpdate");
        Department d1 = new Department("d1");
        DepartmentBean instance = new DepartmentBean();
        DepartmentBean instanceSpy = spy(instance);
        doReturn("departmentUpdated").when(instanceSpy).parentUpdateWrapper(d1);
        doNothing().when(instanceSpy).refreshAverageSalary();
        String expResult = "departmentUpdated";
        String result = instanceSpy.update(d1);
        assertEquals(expResult, result);
        verify(instanceSpy, times(1)).refreshAverageSalary();        
    }        

    /**
     * If entities are null
     */
    @Test
    public void testRefreshAverageSalaryNullEntities() {
        System.out.println("testRefreshAverageSalaryNullEntities");
        DepartmentBean instance = new DepartmentBean();
        DepartmentBean instanceSpy = spy(instance);
        doReturn(null).when(instanceSpy).getEntitiesWrapper();
        instanceSpy.refreshAverageSalary();
        verify(instanceSpy, times(0)).setAverageSalary(any());
    }
    /**
     * Create 2 test department: one with 2 employees with salary = 5000,
     * second - without employees
     * Department's average salary must be 5000.0f and 0.0.f accordingly 
     */
    @Test
    public void testRefreshAverageSalary() {
        System.out.println("testRefreshAverageSalary");
        Department d1 = new Department("d1");
        Department d2 = new Department("d2");        
        List<Department> dep = new ArrayList<>();        
        dep.add(d1);
        dep.add(d2);        
        Employee e21 = new Employee("e21", 5000.0f);
        Employee e22 = new Employee("e22", 5000.0f);        
        List<Employee> es1 = new ArrayList<>();
        es1.add(e21);
        es1.add(e22);        
        DepartmentBean instance = new DepartmentBean();
        DepartmentBean instanceSpy = spy(instance);
        doReturn(dep).when(instanceSpy).getEntitiesWrapper();
        EmployeeBean employeeBeanMock = mock(EmployeeBean.class);
        doReturn(employeeBeanMock).when(instanceSpy).getEmployeeBean();
        doReturn(es1).when(employeeBeanMock).findByDepartment(d1);
        doReturn(null).when(employeeBeanMock).findByDepartment(d2);        
        HashMap<Department, Float> expResult = new HashMap<>();
        expResult.put(d1, 5000.0f);
        expResult.put(d2, 0.0f);        
        instanceSpy.refreshAverageSalary();        
        assertEquals(expResult, instanceSpy.getAverageSalary());
    }    

    /**
     * If department is null
     */
    @Test
    public void testFindAvgSalaryException() {
        System.out.println("testFindAvgSalaryException");        
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("department is null!");
        DepartmentBean instance = new DepartmentBean();
        instance.findAvgSalary(null);
    }
    
    /**
     * Create test average salary hashMap: d1 = 5000.0f, d2 = 0.0f and try
     * get it's value
     */
    @Test
    public void testFindAvgSalary() {
        System.out.println("testFindAvgSalary");
        Department d1 = new Department("d1");
        Department d2 = new Department("d2");        
        DepartmentBean instance = new DepartmentBean();
        DepartmentBean instanceSpy = spy(instance);
        HashMap<Department, Float> hashMap = new HashMap<>();
        hashMap.put(d1, 5000.0f);
        hashMap.put(d2, 0.0f);                
        doReturn(hashMap).when(instanceSpy).getAverageSalary();
        Float expResult = 5000.0f;
        Float result = instanceSpy.findAvgSalary(d1);
        assertEquals(expResult, result);
        expResult = 0.0f;
        result = instanceSpy.findAvgSalary(d2);
        assertEquals(expResult, result);        
    }
}
