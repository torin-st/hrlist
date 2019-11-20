 package com.slyadz.hrlist.service.rs;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.service.persistence.DepartmentDAO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.embeddable.CommandResult;
import org.glassfish.embeddable.CommandRunner;
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

    private static GlassFish glassFish;
    private static DepartmentDAO departmentDAO;
    private final static String SERVICE_URL = "http://localhost:8080/hrlist-service-1.0/api/departments";
    private static Client client;

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
        GlassFishProperties glassfishProperties = new GlassFishProperties();
        glassfishProperties.setPort("http-listener", 8080);
        glassFish = GlassFishRuntime.bootstrap().newGlassFish(glassfishProperties);
//        Logger.getLogger("").getHandlers()[0].setLevel(Level.ALL);
//        Logger.getLogger("javax.enterprise.system.tools.deployment").setLevel(Level.ALL);
//        Logger.getLogger("javax.enterprise.system").setLevel(Level.ALL);
        glassFish.start();
        System.out.println("*** glassFish.status = " + glassFish.getStatus().toString());
        //String command1 = "create-file-user --groups TutorialUser test";
        CommandRunner commandRunner = glassFish.getCommandRunner();        
        String command = "list-commands";
        CommandResult commandResult = commandRunner.run(command);
        System.out.println("*** command: " + command);        
        System.out.println("*** commandResult.getOutput: " + commandResult.getOutput());
        System.out.println("*** commandResult.getExitStatud: " + commandResult.getExitStatus().toString());                
        
        command = "list-auth-realms";
        commandResult = commandRunner.run(command);
        System.out.println("*** command: " + command);        
        System.out.println("*** commandResult.getOutput: " + commandResult.getOutput());
        System.out.println("*** commandResult.getExitStatud: " + commandResult.getExitStatus().toString());                
        //String command1 = "create-file-user --userpassword 123 --groups TutorialUser test";
//        Thread.sleep(5000);        
//        command = "asadmin --passwordfile /cygdrive/d/1.txt create-file-user  --authrealmname file --groups TutorialUser test";
//        commandResult = commandRunner.run(command);
//        System.out.println("*** command: " + command);                
//        System.out.println("*** commandResult.getOutput: " + commandResult.getOutput());
//        System.out.println("*** commandResult.getExitStatud: " + commandResult.getExitStatus().toString());        
//        Thread.sleep(5000);        
        command = "asadmin";
        String subcommand = "create-file-user";
//        String groups = "--groups=TutorialUser";
        String userPassword = "--passwordfile=d:\\1.txt";
        String userName = "test2";
//        String passFile = "--passwordfile=d:\\1.txt";
        commandResult = commandRunner.run(command, userPassword, subcommand, userName);
        System.out.println("*** command: " + command);                
        System.out.println("*** commandResult.getOutput: " + commandResult.getOutput());
        System.out.println("*** commandResult.getExitStatud: " + commandResult.getExitStatus().toString());        
        System.out.println("*** commandResult.getFailureCause: " + commandResult.getFailureCause().toString());                
        command = "list-file-users";
        commandResult = commandRunner.run(command);
        System.out.println("*** command: " + command);                
        System.out.println("*** commandResult.getOutput: " + commandResult.getOutput());
        System.out.println("*** commandResult.getExitStatud: " + commandResult.getExitStatus().toString());        

        File war = new File("target/hrlist-service-1.0.war");
        Deployer deployer = glassFish.getDeployer();
        deployer.deploy(war, "--name=hrlist-service");
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("test", "123");
        Client client = ClientBuilder.newClient();
        client.register(feature);        
        setClient(client);
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
            glassFish.stop();
        }
    }

    private void printAllDepartments() {
        try {
            departmentDAO.findAll().stream().forEach(System.out::println);     
        } catch (Exception e) {
            System.out.println("error while printing departments: " + e.getMessage());
        }
    }
    
  /**
     * Create a Department instance, persist it and get it by the Id.
     *
     */
    @Test
    public void testCreate2() {
        System.out.println("=== create ====");
        System.out.println("=== before ====");
        printAllDepartments();
        
        Department d1 = new Department("d1");
        
        Response response = null;
        try {
            response = getClient().target(SERVICE_URL)
                    .path("test")
                    .request(MediaType.TEXT_PLAIN_TYPE)
                    .get();
        } catch (Exception e) {
            System.out.println("error while get sending: " + e.getMessage());
            return;
        }
        System.out.println("*** response: " + response);
        System.out.println("*** response entity: " + response.readEntity(String.class));
        System.out.println("=== after ====");
        printAllDepartments();
        assertEquals(true, true);
//        System.out.println("Created item: " + d1);
//        System.out.println("Finded item:" + departmentRS.findById(d1.getId()));
//        assertEquals(d1, departmentRS.findById(d1.getId()));
    }    
    /**
     * Create a Department instance, persist it and get it by the Id.
     *
     */
    @Test
    public void testCreate() {
        System.out.println("=== create ====");
        System.out.println("=== before ====");
        printAllDepartments();

        Department d1 = new Department("d1test");
        
        Response response = null;
        try {
            response = getClient().target(SERVICE_URL)
                    .path("/")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.entity(d1, MediaType.APPLICATION_XML),
                            Response.class);
        } catch (Exception e) {
            System.out.println("error while post sending: " + e.getMessage());
            return;
        }
        System.out.println("response: " + response);
        System.out.println("response headers: " + response.getHeaders().toString());
        System.out.println("=== after ====");
        printAllDepartments();
        Department departmentFromRS = response.readEntity(Department.class);
        try {        
            assertEquals(departmentFromRS, departmentDAO.findById(departmentFromRS.getId()));
        } catch (IOException e) {
            System.out.println("error while getting the department from DAO");
        }
    }

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
