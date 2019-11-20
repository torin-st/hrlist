//package com.slyadz.hrlist.service.rs;
//
//import com.slyadz.hrlist.entity.Employee;
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//import javax.ejb.embeddable.EJBContainer;
//import javax.naming.Context;
//import javax.naming.NamingException;
//import javax.ws.rs.WebApplicationException;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.Rule;
//import org.junit.rules.ExpectedException;
//
///**
// *
// * @author A.G. Slyadz
// */
//public class EmployeeRSTest2IT {
//
//    private static EJBContainer ejbContainer;
//    private static Context ctx;
//    private static EmployeeRS employeeRS;
//    @Rule
//    public ExpectedException expectedException;    
//
//    public EmployeeRSTest2IT() {
//        expectedException = ExpectedException.none();        
//    }
//
//    @BeforeClass
//    public static void setUpClass() throws NamingException, Exception {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(EJBContainer.MODULES, new File[]{new File("target/classes"), new File("target/test-classes")});
//        ejbContainer = EJBContainer.createEJBContainer(properties);
//        ctx = ejbContainer.getContext();
//        employeeRS = (EmployeeRS) ctx.lookup("java:global/classes/EmployeeRS");        
//        System.out.println("******************** EmployeeRSTest2IT ********************");        
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
//     * Creates an Employee object, saves it to DB and gets it by ID.
//     *
//     */
//    @Test
//    public void testCreate() {
//        System.out.println("=== create ====");
//        Employee e1 = new Employee("e1");                
//        employeeRS.create(e1);
//        System.out.println("Created item: " + e1);
//        System.out.println("Finded item:" + employeeRS.findById(e1.getId()));
//        assertEquals(e1, employeeRS.findById(e1.getId()));
//    }
//    
//}
