package com.slyadz.hrlist.service.rs;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author A.G. Slyadz
 */
public class EmployeeRSEmbeddedTestIT {

    private static EJBContainer ejbContainer;
    private static Context ctx;
    private static EmployeeRS employeeRS;

    public EmployeeRSEmbeddedTestIT() {
    }

    @BeforeClass
    public static void setUpClass() throws NamingException, Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File[]{new File("target/classes"), new File("target/test-classes")});
        ejbContainer = EJBContainer.createEJBContainer(properties);
        ctx = ejbContainer.getContext();
        employeeRS = (EmployeeRS) ctx.lookup("java:global/classes/EmployeeRS");
        System.out.println("******************** EmployeeRS ********************");        
    }

    @AfterClass
    public static void tearDownClass() {
        if (ejbContainer != null) {
            ejbContainer.close();
        }
    }

    /**
     * Creates an Employee object, saves it to DB and gets it by ID.
     *
     */
    @Test
    public void testCreate() {
        System.out.println("=== create ====");
        Employee e1 = new Employee("e1");                
        Department d1 = new Department("d1");
        e1.setDepartment(d1);        
        employeeRS.create(e1);
        System.out.println("Created item: " + e1);
        System.out.println("Finded item:" + employeeRS.findById(e1.getId()));
        assertEquals(e1, employeeRS.findById(e1.getId()));
    }

    /**
     * Creates an Employee object, saves it to DB and gets it by id, add 1 to
     * it's id and tries to get it by this new id
     */
    @Test
    public void testFindById() {
        System.out.println("=== findById ====");
        Employee e1 = new Employee("e1");
        employeeRS.create(e1);
        System.out.println("Created item: " + e1);
        System.out.println("Finded item: " + employeeRS.findById(e1.getId()));
        assertEquals(e1, employeeRS.findById(e1.getId()));
        Long wrongId = e1.getId() + 1;
        assertNotEquals(e1.getId(), employeeRS.findById(wrongId));
    }

    /**
     * Gets all items count, creates 2 new items and gets a new count. It must
     * be old count + 2.
     */
    @Test
    public void testFindAll() {
        System.out.println("=== findAll ===");
        int startEmployeeCount = employeeRS.findAll().size();
        System.out.println("start count: " + startEmployeeCount);
        Employee e1 = new Employee("e1");
        Department d1 = new Department("d1");
        e1.setDepartment(d1);
        employeeRS.create(e1);
        Employee e2 = new Employee("e2");
        Department d2 = new Department("d2");
        e2.setDepartment(d2);        
        employeeRS.create(e2);
        int endEmployeeCount = employeeRS.findAll().size();
        System.out.println("end count: " + endEmployeeCount);
        assertEquals(startEmployeeCount + 2, endEmployeeCount);
    }

    /**
     * Creates 3 items: 2 with department and one with other. Compares.
     * 
     */
    @Test
    public void testFindByDepartmentId() {
        System.out.println("=== findByDepartmentIdAll ===");
        Department d1 = new Department("d100");
        Department d2 = new Department("d200");        
        Employee e1 = new Employee("e1");
        e1.setDepartment(d1);
        Employee e2 = new Employee("e2");
        e2.setDepartment(d2);        
        Employee e3 = new Employee("e3");
        e3.setDepartment(d1);                
        employeeRS.create(e1);
        employeeRS.create(e2);
        employeeRS.create(e3);
        System.out.println(Arrays.toString(employeeRS.findAll().toArray()));
        int countD100 = employeeRS.findByDepartmentId(d1.getId()).size();
        int countD200 = employeeRS.findByDepartmentId(d2.getId()).size();
        assertEquals(2, countD100);
        assertEquals(1, countD200);        
    }

    /**
     * Creates new item, persist it, changes it's name, update and compare.
     *
     */
    @Test
    public void testUpdate() {
        System.out.println("=== update ===");
        Employee e1 = new Employee("e1");
        employeeRS.create(e1);
        e1.setName("e1test");
        employeeRS.update(e1);
        System.out.println("Old name: e1");
        System.out.println("New name: " + employeeRS.findById(e1.getId()).getName());
        assertNotEquals("e1", employeeRS.findById(e1.getId()).getName());
    }

    /**
     * Creates new item, delete and tries to get it by id.
     *
     */
    @Test
    public void testDelete() {
        System.out.println("=== delete ===");
        Employee e1 = new Employee("e1");
        employeeRS.create(e1);
        Long oldId = e1.getId();
        employeeRS.delete(oldId);
        assertNotEquals(e1, employeeRS.findById(oldId));
    }

}
