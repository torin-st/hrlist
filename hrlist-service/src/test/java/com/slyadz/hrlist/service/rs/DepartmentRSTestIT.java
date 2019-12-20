 package com.slyadz.hrlist.service.rs;

import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.service.persistence.DepartmentDAO;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Perform integration test for DepartmentRS class
 */
public class DepartmentRSTestIT implements Serializable {

    private final static Logger logger = Logger.getLogger(DepartmentRSTestIT.class.getCanonicalName());
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
        DepartmentRSTestIT.client = client;
    }

    public static GlassFish getGlassFish() {
        return glassFish;
    }

    public static void setGlassFish(GlassFish glassFish) {
        DepartmentRSTestIT.glassFish = glassFish;
    }

    public static Deployer getDeployer() {
        return deployer;
    }

    public static void setDeployer(Deployer deployer) {
        DepartmentRSTestIT.deployer = deployer;
    }

    public static DepartmentDAO getDepartmentDAO() {
        return departmentDAO;
    }

    public static void setDepartmentDAO(DepartmentDAO departmentDAO) {
        DepartmentRSTestIT.departmentDAO = departmentDAO;
    }

    public static String getAppName() {
        return appName;
    }

    public static void setAppName(String appName) {
        DepartmentRSTestIT.appName = appName;
    }
    
    public DepartmentRSTestIT() {
    }

    @BeforeClass
    public static void setUpClass() throws NamingException, Exception {
        GlassFishProperties glassfishProperties = new GlassFishProperties();
        //preconfigured glassfish-server config (port 8080, user: test with pass: 123 with group: TutorialUser)
        File instanceRoot = new File("target/test-classes/glassfish4/glassfish/domains/domain1");        
        glassfishProperties.setInstanceRoot(instanceRoot.getPath());
        //starting and deploying glassfish
        setGlassFish(GlassFishRuntime.bootstrap().newGlassFish(glassfishProperties));
        getGlassFish().start();
        setDeployer(getGlassFish().getDeployer());
        ScatteredArchive archive = new ScatteredArchive("hrlist-service", ScatteredArchive.Type.WAR);
        //target/classes directory contains complied class-files
        archive.addClassPath(new File("target", "classes"));
        //custom beans.xml to use different persistent unit during integration test
        archive.addMetadata(new File("target/test-classes/META-INF", "beans.xml"));
        //deploy the scattered web archive.
        setAppName(deployer.deploy(archive.toURI(), "--name=hrlist-service", "--contextroot=hrlist-service", "--force=true"));
        //http client
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("test", "123");
        setClient(ClientBuilder.newClient().register(feature));
        //EJB fetching
        Context ctx = new InitialContext();
        setDepartmentDAO((DepartmentDAO) ctx.lookup("java:global/hrlist-service/DepartmentDAO"));
        logger.fine("setUpClass()");
    }

    @AfterClass
    public static void tearDownClass() throws GlassFishException {
        if (getClient() != null) {
            getClient().close();
        }
        
        if (getGlassFish() != null) {
            if (getAppName() != null) {
                if (getDeployer() != null) {
                    getDeployer().undeploy(getAppName());
                }
            }
            getGlassFish().stop();
        }
    }

    /**
    * Create a Department instance with REST, get it by the Id with DAO,
    * compare.
    */
    @Test
    public void testCreate() {
        logger.info("testCreate()");
        
        Department d1 = new Department("testCreate");
        Department departmentFromREST = null;
        try {
            departmentFromREST = getClient().target(SERVICE_URL)
                    .path("/")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.entity(d1, MediaType.APPLICATION_XML),
                            new GenericType<Department>(){});
        } catch (Exception e) {
            logger.severe("error while post sending: " + e.getMessage());
            fail();
        }

        Department departmentFromDAO = null;
        try {
            departmentFromDAO = getDepartmentDAO().findById(departmentFromREST.getId());
        } catch (IOException e) {
            logger.severe("error department fetching: " + e.getMessage());
            fail();
        }        
        
        logger.info("Created item: " + departmentFromREST);
        logger.info("Found item:" + departmentFromDAO);
        assertEquals(departmentFromREST, departmentFromDAO);

    }    
    /**
     * Create a Department instance with DAO, get it by id with REST,
     * compare. 
     */
    @Test
    public void testFindById() {
        logger.info("testFindById()");
        
        Department d1 = new Department("testFindById");
        try {
            getDepartmentDAO().create(d1);
        } catch (IOException e) {
            logger.severe("error while department creation: " + e.getMessage());
            fail();            
        }

        Department departmentFromREST = null;
        try {
            departmentFromREST = getClient().target(SERVICE_URL)
                    .path(d1.getId().toString())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<Department>(){});
        } catch (Exception e) {
            logger.severe("error while get sending: " + e.getMessage());
            fail();
        }
        
        logger.info("Created item: " + d1);
        logger.info("Found item: " + departmentFromREST);
        assertEquals(d1, departmentFromREST);
    }
   
    /**
     * Count all departments, create 2 new items with DAO and get a new count
     * with REST. It must be = old count + 2. 
     */
    @Test
    public void testFindAll() {
        logger.info("testFindAll()");
        
        int startDepartmentCount = 0;
        try {
            startDepartmentCount = getDepartmentDAO().findAll().size();
        } catch (IOException e) {
            logger.severe("error while list size evaluation: " + e.getMessage());
            fail();            
        }
        logger.info("start count: " + startDepartmentCount);
        
        Department d1 = new Department("testFindAll1");
        Department d2 = new Department("testFindAll2");        
        try {
            getDepartmentDAO().create(d1);
            getDepartmentDAO().create(d2);            
        } catch (IOException e) {
            logger.severe("error while department creation: " + e.getMessage());
            fail();            
        }
        
        List<Department> response = null;
        try {
            response = getClient().target(SERVICE_URL)
                    .path("/")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType< List<Department> >(){});
        } catch (Exception e) {
            logger.severe("error while get sending: " + e.getMessage());
            fail();
        }
        int endDepartmentCount = response.size();
        logger.info("end count: " + endDepartmentCount);
        assertEquals(startDepartmentCount + 2, endDepartmentCount);
    }

     /**
     * Create a new Department instance with DAO, change it's name, update with
     * REST and compare old and new names.
     */
    @Test
    public void testUpdate() {
        logger.info("testUpdate()");
        
        Department d1 = new Department("testUpdate");
        try {
            getDepartmentDAO().create(d1);
        } catch (IOException e) {
            logger.severe("error while department creation: " + e.getMessage());
            fail();            
        }
        d1.setName("testUpdateNew");
        
        Response response = null;
        try {
            response = getClient().target(SERVICE_URL)
                    .path("/")
                    .request()
                    .put(Entity.entity(d1, MediaType.APPLICATION_XML),
                            Response.class);
        } catch (Exception e) {
            logger.severe("error while put sending: " + e.getMessage());
            fail();
        }

        logger.info("Old name: testUpdate");
        Department departmentFromDAO = null;
        try {
            departmentFromDAO = getDepartmentDAO().findById(d1.getId());
        } catch (IOException e) {
            logger.severe("error while department fetching: " + e.getMessage());
            fail();
        }
        logger.info("New name: " + departmentFromDAO.getName());
        assertNotEquals("testUpdate", departmentFromDAO.getName());
    }

    /**
     * Count all departments, create a new Department instance, persist it, 
     * delete it and compare count.
     *
     */
    @Test
    public void testDelete() {
        logger.info("testDelete()");
        
        int startCount = 0;
        try {
            startCount = getDepartmentDAO().findAll().size();
        } catch (IOException e) {
            logger.severe("error while list size evaluation: " + e.getMessage());
            fail();
        }
        logger.info("start count: " + startCount);
        
        Department d1 = new Department("testDelete");
        try {
            getDepartmentDAO().create(d1);
        } catch (IOException e) {
            logger.severe("error while department creation: " + e.getMessage());
            fail();
        }
        
        Response response = null;
        try {
            response = getClient().target(SERVICE_URL)
                    .path(d1.getId().toString())
                    .request()
                    .delete();
        } catch (Exception e) {
            logger.severe("error while delete sending: " + e.getMessage());
            fail();
        }
        
        int endCount = 0;        
        try {
            endCount = getDepartmentDAO().findAll().size();
        } catch (IOException e) {
            logger.severe("error while list size evaluation: " + e.getMessage());
            fail();
        }
        logger.info("end count: " + endCount);        
        
        assertEquals(startCount, endCount);        
    }

}
