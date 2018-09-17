package com.slyadz.hrlist.client.web.managedbean;

import java.security.Principal;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
  * Abstract class for managed entity beans.
  * 
 * @param <T> 
 */
public abstract class AbstractEntityBean<T> extends AbstractBean {

    private Client client;
    private List<T> entities;

    protected abstract String getServiceURL();
    protected abstract String getOutcomePrefix();
    protected abstract String getInstanceId(T instance);
    protected abstract List<T> findAll();    

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }
    
    public void refreshEntities(){
        setEntities(findAll());
    }
    
    public String create(T instance) {
        String navigation = getOutcomePrefix().toLowerCase() + "Error";

        if (instance == null) {
            message(null, "CouldNotCreate" + getOutcomePrefix(), new Object[]{getLocalizedMessage("InstanceIsNull")});
            return navigation;
        }
        //perform post request    
        Response response = null;
        try {
            response = getClient().target(getServiceURL())
                    .path("/")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.entity(instance, MediaType.APPLICATION_XML),
                            Response.class);
        } catch (Exception e) {
            message(null, "CouldNotCreate" + getOutcomePrefix(), new Object[]{e.getMessage()});
            return navigation;
        }
        //check response
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            message(null, "ObjectCreated");
            navigation = getOutcomePrefix().toLowerCase() + "Created";
            refreshEntities();
        } else {
            message(null, "CouldNotCreate" + getOutcomePrefix(), new Object[]{response.getStatus()});
        }
        return navigation;
    }

    public String update(T instance) {
        String navigation = getOutcomePrefix().toLowerCase() + "Error";

        if (instance == null) {
            message(null, "CouldNotUpdate" + getOutcomePrefix(), new Object[]{getLocalizedMessage("InstanceIsNull")});
            return navigation;
        }
        //perform put request
        Response response = null;
        try {
            response = getClient().target(getServiceURL())
                    .path("/")
                    .request()
                    .put(Entity.entity(instance, MediaType.APPLICATION_XML), Response.class);
        } catch (Exception e) {
            message(null, "CouldNotUpdate" + getOutcomePrefix(), new Object[]{e.getMessage()});
            return navigation;
        }
        //check response
        if (Response.Status.SEE_OTHER.getStatusCode() == response.getStatus()) {
            message(null, "ObjectUpdated");
            navigation = getOutcomePrefix().toLowerCase() + "Updated";
            refreshEntities();
        } else {
            message(null, "CouldNotUpdate" + getOutcomePrefix(), new Object[]{response.getStatus()});
        }
        return navigation;
    }

    public String delete(T instance) {
        String navigation = getOutcomePrefix().toLowerCase() + "Error";
        //check access
        Principal principal = getExternalContext().getUserPrincipal();
        if (principal == null) {
            navigation = "needLogin";
            return navigation;
        }
        if (instance == null) {
            message(null, "CouldNotDelete" + getOutcomePrefix(), new Object[]{getLocalizedMessage("InstanceIsNull")});
            return navigation;
        }
        //perform delete request    
        Response response = null;
        try {
            response = getClient().target(getServiceURL())
                    .path(getInstanceId(instance))
                    .request()
                    .delete();
        } catch (Exception e) {
            message(null, "CouldNotDelete" + getOutcomePrefix(), new Object[]{e.getMessage()});
            return navigation;
        }
        //check response
        if (Response.Status.NO_CONTENT.getStatusCode() == response.getStatus()) {
            message(null, "ObjectDeleted");
            navigation = getOutcomePrefix().toLowerCase() + "Deleted";
            refreshEntities();
        } else {
            message(null, "CouldNotDelete" + getOutcomePrefix(), new Object[]{response.getStatus()});
        }
        return navigation;
    }
}
