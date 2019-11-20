// package com.slyadz.hrlist.service.rs;
//
//import com.slyadz.hrlist.entity.Department;
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//import javax.ejb.embeddable.EJBContainer;
//import javax.naming.Context;
//import javax.naming.NamingException;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// * Perform integration test for DepartmentRS class
// * @author A.G. Slyadz
// */
//public class DepartmentRSTestIT {
//
//    private static EJBContainer ejbContainer;
//    private static Context ctx;
//    private static DepartmentRS departmentRS;
//
//    public DepartmentRSTestIT() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() throws NamingException, Exception {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(EJBContainer.MODULES, new File[]{new File("target/classes"), new File("target/test-classes")});
//        ejbContainer = EJBContainer.createEJBContainer(properties);
//        ctx = ejbContainer.getContext();
//        departmentRS = (DepartmentRS) ctx.lookup("java:global/classes/DepartmentRS");
//        System.out.println("******************** DepartmentRSTestIT ********************");                
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//        if (ejbContainer != null) {
//            ejbContainer.close();
//        }
//    }
//
//    /**
//     * Create a Department instance, persist it and get it by the Id.
//     *
//     */
//    @Test
//    public void testCreate() {
//        System.out.println("=== create ====");
//        Department d1 = new Department("d1");
//        departmentRS.create(d1);
//        System.out.println("Created item: " + d1);
//        System.out.println("Finded item:" + departmentRS.findById(d1.getId()));
//        assertEquals(d1, departmentRS.findById(d1.getId()));
//    }
//
//    /**
//     * Create a Department instance, persist it and get it by id.
//     * 
//     */
//    @Test
//    public void testFindById() {
//        System.out.println("=== findById ====");
//        Department d1 = new Department("d1");
//        departmentRS.create(d1);
//        System.out.println("Created item: " + d1);
//        System.out.println("Finded item: " + departmentRS.findById(d1.getId()));
//        assertEquals(d1, departmentRS.findById(d1.getId()));
//    }
//   
//    /**
//     * Count all departments, create 2 new items and get a new count. It
//     * must be =  old count + 2.
//     * 
//     */
//    @Test
//    public void testFindAll() {
//        System.out.println("=== findAll ===");
//        int startDepartmentCount = departmentRS.findAll().size();
//        System.out.println("start count: " + startDepartmentCount);
//        Department d1 = new Department("d1");
//        departmentRS.create(d1);
//        Department d2 = new Department("d2");
//        departmentRS.create(d2);
//        int endDepartmentCount = departmentRS.findAll().size();
//        System.out.println("end count: " + endDepartmentCount);
//        assertEquals(startDepartmentCount + 2, endDepartmentCount);
//    }
//
//     /**
//     * Create a new Department instance, persist it, change it's name, update
//     * and compare.
//     *
//     */
//    @Test
//    public void testUpdate() {
//        System.out.println("=== update ===");
//        Department d1 = new Department("d1");
//        departmentRS.create(d1);
//        d1.setName("d1test");
//        departmentRS.update(d1);
//        System.out.println("Old name: d1");
//        System.out.println("New name: " + departmentRS.findById(d1.getId()).getName());
//        assertNotEquals("d1", departmentRS.findById(d1.getId()).getName());
//    }
//
//    /**
//     * Count all departments, create a new Department instance, persist it, 
//     * delete it and compare count.
//     *
//     */
//    @Test
//    public void testDelete() {
//        System.out.println("=== delete ===");
//        int count = departmentRS.findAll().size();
//        int startCount = count;
//        System.out.println("start count: " + count);
//        Department d1 = new Department("d1");
//        departmentRS.create(d1);
//        departmentRS.delete(d1.getId());        
//        count = departmentRS.findAll().size();        
//        System.out.println("end count: " + count);        
//        assertEquals(startCount, count);        
//    }
//
//}
