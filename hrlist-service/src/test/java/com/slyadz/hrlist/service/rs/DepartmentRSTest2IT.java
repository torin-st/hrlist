 package com.slyadz.hrlist.service.rs;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.service.persistence.DepartmentDAO;
import java.io.File;
import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Perform integration test for DepartmentRS class
 * @author A.G. Slyadz
 */
public class DepartmentRSTest2IT {

    private final static String SERVICE_URL = "http://localhost:8080/hrlist-service/api/departments";
    private static GlassFish glassFish;
    private static Deployer deployer;
    private static DepartmentDAO departmentDAO;
    private static Client client;
    private static String appName;

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        DepartmentRSTest2IT.client = client;
    }

    public DepartmentRSTest2IT() {
    }

    @BeforeClass
    public static void setUpClass() throws NamingException, Exception {
        //preconfigured glassfish config
        GlassFishProperties glassfishProperties = new GlassFishProperties();
        File instanceRoot = new File("target/test-classes/glassfish4/glassfish/domains/domain1");        
        glassfishProperties.setInstanceRoot(instanceRoot.getPath());
        //starting and deploying
        glassFish = GlassFishRuntime.bootstrap().newGlassFish(glassfishProperties);
        glassFish.start();
        deployer = glassFish.getDeployer();
        appName = deployer.deploy(new File("target/hrlist-service-1.0.war"), "--name=hrlist-service", "--contextroot=hrlist-service", "--force=true");
        //http client
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("test", "123");
        Client client = ClientBuilder.newClient();
        client.register(feature);        
        setClient(client);
        //EJB fetching
        Context ctx = new InitialContext();
        departmentDAO = (DepartmentDAO) ctx.lookup("java:global/hrlist-service/DepartmentDAO");        
        System.out.println("******************** DepartmentRSTest2IT ********************");      
    }

    @AfterClass
    public static void tearDownClass() throws GlassFishException {
        if (getClient() != null) {
            getClient().close();
        }
        
        if (glassFish != null) {
            if (appName != null) {
                if (deployer != null) {
                    deployer.undeploy(appName);
                }
            }
            glassFish.stop();
        }
    }

    /**
    * Create a Department instance, persist it and get it by the Id.
    */
    @Test
    public void testCreate() {
        System.out.println("******************** testCreate ********************");      
        Department d1 = new Department("d1");
        
        Response response = null;
        try {
            response = getClient().target(SERVICE_URL)
                    .path("/")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.entity(d1, MediaType.APPLICATION_XML),
                            Response.class);
        } catch (Exception e) {
            System.out.println("error while get sending: " + e.getMessage());
            return;
        }
        System.out.println("*** response: " + response);
        Department departmentFromREST = response.readEntity(Department.class);
        System.out.println("*** response entity: " + departmentFromREST);
        System.out.println("******************** after ********************");              
        Department departmentFromDAO = null;
        try {
            departmentFromDAO = departmentDAO.findById(departmentFromREST.getId());
        } catch (IOException e) {
          System.out.println(e.getMessage());              
        }        
        System.out.println("Created item: " + departmentFromREST);
        System.out.println("Finded item:" + departmentFromDAO);

        assertEquals(departmentFromREST, departmentFromDAO);

    }    
    /**
     * Create a Department instance, persist it and get it by the Id.
     *
     */
//    @Test
//    public void testCreate() {
//        System.out.println("=== create ====");
//        System.out.println("=== before ====");
//        printAllDepartments();
//
//        Department d1 = new Department("d1test");
//        
//        Response response = null;
//        try {
//            response = getClient().target(SERVICE_URL)
//                    .path("/")
//                    .request(MediaType.APPLICATION_XML)
//                    .post(Entity.entity(d1, MediaType.APPLICATION_XML),
//                            Response.class);
//        } catch (Exception e) {
//            System.out.println("error while post sending: " + e.getMessage());
//            return;
//        }
//        System.out.println("response: " + response);
//        System.out.println("response headers: " + response.getHeaders().toString());
//        System.out.println("=== after ====");
//        printAllDepartments();
//        Department departmentFromRS = response.readEntity(Department.class);
//        try {        
//            assertEquals(departmentFromRS, departmentDAO.findById(departmentFromRS.getId()));
//        } catch (IOException e) {
//            System.out.println("error while getting the department from DAO");
//        }
//    }

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

}
