package com.slyadz.hrlist.service.rs;

import com.slyadz.hrlist.entity.Department;
import java.io.File;
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
public class DepartmentRSEmbeddedTestIT {

    private static EJBContainer ejbContainer;
    private static Context ctx;
    private static DepartmentRS departmentRS;

    public DepartmentRSEmbeddedTestIT() {
    }

    @BeforeClass
    public static void setUpClass() throws NamingException, Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File[]{new File("target/classes"), new File("target/test-classes")});
        ejbContainer = EJBContainer.createEJBContainer(properties);
        ctx = ejbContainer.getContext();
        departmentRS = (DepartmentRS) ctx.lookup("java:global/classes/DepartmentRS");
    }

    @AfterClass
    public static void tearDownClass() {
        if (ejbContainer != null) {
            ejbContainer.close();
        }
    }

    /**
     * Creates an Department object, saves it to DB and gets it by ID.
     *
     */
    @Test
    public void testCreate() {
        System.out.println("=== create ====");
        Department d1 = new Department("d1");
        departmentRS.create(d1);
        System.out.println("Created item: " + d1);
        System.out.println("Finded item:" + departmentRS.findById(d1.getId()));
        assertEquals(d1, departmentRS.findById(d1.getId()));
    }

    /**
     * Creates an Department object, saves it to DB and gets it by id, add 1 to
     * it's id and tries to get it by this new id
     */
    @Test
    public void testFindById() {
        System.out.println("=== findById ====");
        Department d1 = new Department("d1");
        departmentRS.create(d1);
        System.out.println("Created item: " + d1);
        System.out.println("Finded item: " + departmentRS.findById(d1.getId()));
        assertEquals(d1, departmentRS.findById(d1.getId()));
        Long wrongId = d1.getId() + 1;
        assertNotEquals(d1.getId(), departmentRS.findById(wrongId));
    }

    /**
     * Gets all items count, creates 2 new items and gets a new count. It must
     * be old count + 2.
     */
    @Test
    public void testFindAll() {
        System.out.println("=== findAll ===");
        int startDepartmentCount = departmentRS.findAll().size();
        System.out.println("start count: " + startDepartmentCount);
        Department d1 = new Department("d1");
        departmentRS.create(d1);
        Department d2 = new Department("d2");
        departmentRS.create(d2);
        int endDepartmentCount = departmentRS.findAll().size();
        System.out.println("end count: " + endDepartmentCount);
        assertEquals(startDepartmentCount + 2, endDepartmentCount);
    }

     /**
     * Creates new item, persist it, changes it's name, update and compare.
     *
     */
    @Test
    public void testUpdate() {
        System.out.println("=== update ===");
        Department d1 = new Department("d1");
        departmentRS.create(d1);
        d1.setName("d1test");
        departmentRS.update(d1);
        System.out.println("Old name: d1");
        System.out.println("New name: " + departmentRS.findById(d1.getId()).getName());
        assertNotEquals("d1", departmentRS.findById(d1.getId()).getName());
    }

    /**
     * Creates new item, delete and tries to get it by id.
     *
     */
    @Test
    public void testDelete() {
        System.out.println("=== delete ===");
        Department d1 = new Department("d1");
        departmentRS.create(d1);
        Long oldId = d1.getId();
        departmentRS.delete(oldId);
        assertNotEquals(d1, departmentRS.findById(oldId));
    }

}
