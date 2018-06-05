/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.service.rs;

import com.slyadz.hrlist.entity.Department;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @A.G. Slyadz
 */
public class DepartmentRSEmbeddedTestIT {

    private static EJBContainer ejbContainer;
    private static Context ctx;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hrlistIT");
    private static DepartmentRS departmentRS;

    @BeforeClass
    public static void setUpClass() throws NamingException {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File[]{new File("target/classes"), new File("target/test-classes")});
        ejbContainer = EJBContainer.createEJBContainer(properties);
        ctx = ejbContainer.getContext();
        departmentRS = (DepartmentRS) ctx.lookup("java:global/classes/DepartmentRS");
        departmentRS.setEntityManagerFactory(emf);
    }

    @AfterClass
    public static void tearDownClass() {
        if (ejbContainer != null) {
            ejbContainer.close();
        }
        if (emf != null) {
            if (emf.isOpen() == true) {
                emf.close();
            }
        }
    }

    /**
     * Creates 2 Department object, saves them to DB and trying get 1-st by real
     * ID, and 2-nd by wrong ID (1-st object's ID).
     *
     */
    @Test
    public void getDepartmentById() {
        Department testDepartment1 = new Department("junitTestName1");
        departmentRS.createDepartment(testDepartment1);
        Department testDepartment2 = new Department("junitTestName2");
        departmentRS.createDepartment(testDepartment2);

        assertEquals(testDepartment1, departmentRS.getDepartmentById(testDepartment1.getId()));
        assertNotEquals(testDepartment2, departmentRS.getDepartmentById(testDepartment1.getId()));
    }

    /**
     * Gets current all departments count, creates 2 Department objects, saves
     * them to DB and gets new all departments count.
     *
     */
    @Test
    public void getAllDepartments() {
        int startDerartmentsCount = departmentRS.getAllDepartments().size();
        Department testDepartment3 = new Department("junitTestName3");
        departmentRS.createDepartment(testDepartment3);
        Department testDepartment4 = new Department("junitTestName4");
        departmentRS.createDepartment(testDepartment4);
        int endDepartmentsCount = departmentRS.getAllDepartments().size();

        assertEquals(startDerartmentsCount + 2, endDepartmentsCount);
    }

    /**
     * Creates a Department object, saves it to DB and gets it by ID.
     *
     */
    @Test
    public void createDepartment() {
        Department testDepartment5 = new Department("junitTestName5");
        departmentRS.createDepartment(testDepartment5);

        System.out.println(testDepartment5);
        System.out.println(departmentRS.getDepartmentById(testDepartment5.getId()));

        assertEquals(testDepartment5, departmentRS.getDepartmentById(testDepartment5.getId()));
    }
}
