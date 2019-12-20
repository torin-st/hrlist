package com.slyadz.hrlist.client.web.converter;

import com.slyadz.hrlist.client.web.managedbean.DepartmentBean;
import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Perform module tests for the DepartmentConverter class
 */
public class DepartmentConverterTest {

    @Rule
    public ExpectedException expectedException;

    public DepartmentConverterTest() {
        expectedException = ExpectedException.none();
    }

    /**
     * Send a null-value for the newValue argument and check if it will throw a
     * IllegalArgumentException with a correct message
     */
    @Test
    public void testGetAsObjectNullNewValue() {
        System.out.println("getAsObjectNullNewValue");
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("New value is empty!");
        FacesContext context = null;
        UIComponent component = null;
        String newValue = "";
        DepartmentConverter instance = new DepartmentConverter();
        instance.getAsObject(context, component, newValue);
    }

    /**
     * Send a null-value for the departmentBean and check if it will throw a
     * NullPointerException with a correct message
     */
    @Test
    public void testGetAsObjectNullDepartmentBean() {
        System.out.println("getAsObjectNullDepartmentBean");
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("departmentBean is null!");
        FacesContext contextMock = mock(FacesContext.class);
        Application appMock = mock(Application.class);
        doReturn(appMock).when(contextMock).getApplication();
        doReturn(null).when(appMock).evaluateExpressionGet(contextMock,
                "#{departmentBean}", DepartmentBean.class);
        UIComponent component = null;
        String newValue = "test";
        DepartmentConverter instance = new DepartmentConverter();
        instance.getAsObject(contextMock, component, newValue);
    }

    /**
     * Create three Department-instances with id 1, 2, 3. Try to get it from
     * bean as object by sending newValue = "2".
     */
    @Test
    public void testGetAsObject() {
        System.out.println("getAsObject");
        FacesContext contextMock = mock(FacesContext.class);
        Application appMock = mock(Application.class);
        DepartmentBean departmentBeanMock = mock(DepartmentBean.class);
        doReturn(appMock).when(contextMock).getApplication();
        doReturn(departmentBeanMock).when(appMock).evaluateExpressionGet(contextMock,
                "#{departmentBean}", DepartmentBean.class);
        Department d1 = new Department("d1");
        d1.setId(1L);
        Department d2 = new Department("d2");
        d2.setId(2L);
        Department d3 = new Department("d3");
        d3.setId(3L);
        List<Department> departments = Arrays.asList(d1, d2, d3);
        doReturn(departments).when(departmentBeanMock).getEntities();
        UIComponent component = null;
        String newValue = "2";
        DepartmentConverter instance = new DepartmentConverter();
        assertEquals(d2, instance.getAsObject(contextMock, component, newValue));
        assertNotEquals(d3, instance.getAsObject(contextMock, component, newValue));
    }

    /**
     * Send a null-value for the convertedValue and check if it will throw a
     * NullPointerException with a correct message
     */    
    @Test
    public void testGetAsObjectNullConvertedValue() {
        System.out.println("getAsObjectNullConvertedValue");
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Converted value is null after request!");
        FacesContext contextMock = mock(FacesContext.class);
        Application appMock = mock(Application.class);
        DepartmentBean departmentBeanMock = mock(DepartmentBean.class);
        doReturn(appMock).when(contextMock).getApplication();
        doReturn(departmentBeanMock).when(appMock).evaluateExpressionGet(contextMock,
                "#{departmentBean}", DepartmentBean.class);
        doReturn(new ArrayList<>()).when(departmentBeanMock).getEntities();
        UIComponent component = null;
        String newValue = "2";
        DepartmentConverter instance = new DepartmentConverter();
        instance.getAsObject(contextMock, component, newValue);
    }

     /**
     * Send a null-value for the value and check if it will return ""
     */    
    @Test
    public void testGetAsStringNullValue() {
        System.out.println("getAsStringNullValue");
        FacesContext context = null;
        UIComponent component = null;
        Object value = null;
        DepartmentConverter instance = new DepartmentConverter();
        String expResult = "";
        String result = instance.getAsString(context, component, value);
        assertEquals(expResult, result);
    }

     /**
     * Send a Employee-instance for the value and check if it will return ""
     */    
    @Test
    public void testGetAsStringNotNeccessaryInstance() {
        System.out.println("getAsStringNotNeccessaryInstance");
        FacesContext context = null;
        UIComponent component = null;
        Object value = new Employee();
        DepartmentConverter instance = new DepartmentConverter();
        String expResult = "";
        String result = instance.getAsString(context, component, value);
        assertEquals(expResult, result);
    }    
    
     /**
     * Send a Department-instance with id 1L for the value and check if it will
     * return "1"
     */    
    @Test
    public void testGetAsString() {
        System.out.println("getAsString");
        FacesContext context = null;
        UIComponent component = null;
        Department d1 = new Department("d1");
        d1.setId(1L);
        Object value = d1;
        DepartmentConverter instance = new DepartmentConverter();
        String result = instance.getAsString(context, component, value);
        assertEquals("1", result);
    }        
}
