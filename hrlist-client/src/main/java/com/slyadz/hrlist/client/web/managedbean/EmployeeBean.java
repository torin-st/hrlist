package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.client.web.interceptor.Loggable;
import com.slyadz.hrlist.entity.Department;
import com.slyadz.hrlist.entity.Employee;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author A.G. Slyadz
 */
@Named
@SessionScoped
@Loggable
public class EmployeeBean extends AbstractEntityBean<Employee> implements Serializable {

    private final static String SERVICE_URL = "http://localhost:8080/hrlist-service/api/employees";
    private Date dateFrom;
    private Date dateTill;
    @Inject
    private DepartmentBean departmentBean;

    public EmployeeBean() {
    }

    @Override
    protected String getServiceURL() {
        return SERVICE_URL;
    }

    @Override
    protected String getOutcomePrefix() {
        return "Employee";
    }

    @Override
    protected String getInstanceId(Employee instance) {
        return instance.getId().toString();
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTill() {
        return dateTill;
    }

    public void setDateTill(Date dateTill) {
        this.dateTill = dateTill;
    }

    @PostConstruct
    private void init() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("test", "123");
        Client client = ClientBuilder.newClient();
        client.register(feature);
        setClient(client);
        refreshEntities();
        setDateFrom(new Date());
        setDateTill(new Date());
    }

    @PreDestroy
    private void clean() {
        getClient().close();
    }

    public List<Employee> filter() {
        if (getDateFrom() == null || getDateTill() == null) {
            return null;
        }

        return getClient().target(SERVICE_URL)
                .path("/find_by_birthday_range")
                .queryParam("from", new SimpleDateFormat("dd.MM.yyyy").format(getDateFrom()))
                .queryParam("till", new SimpleDateFormat("dd.MM.yyyy").format(getDateTill()))
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Employee>>() {
                });
    }
    
    public String clearFilter() {
        setDateFrom(new Date());
        setDateTill(new Date());
        return null;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> result = null;
        //perform get request
        try {
            result = getClient().target(getServiceURL())
                    .path("/")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<Employee>>() {
                    }); //Doesn't work in AbstractEntityBean - find out why
        } catch (Exception e) {
            message(null, "CouldNotFindAll" + getOutcomePrefix(), new Object[]{e.getMessage()});
            return null;
        }
        return result;
    }

    public List<Employee> findByDepartment(Department department) {
        if (department == null) {
            throw new NullPointerException(getLocalizedMessage("InstanceIsNull"));
        }
        //from bean
        return getEntities().stream().filter(e -> e.getDepartment().equals(department)).collect(Collectors.toList());
    }

    public String create(Employee employee, String fromOutcome) {
        String result = super.create(employee);
        if (result.equals("employeeCreated")) {
            departmentBean.refreshAverageSalary();
            result = fromOutcome;            
        }
        return result;
    }

    @Override
    public String delete(Employee instance) {
        String result = super.delete(instance);
        if (result.equals("employeeDeleted")) {
            departmentBean.refreshAverageSalary();
            return null;
        }
        return result;
    }

    public String update(Employee employee, String fromOutcome) {
        String result = super.update(employee);
        if (result.equals("employeeUpdated")) {
            departmentBean.refreshAverageSalary();
            result = fromOutcome;
        }
        return result;
    }
}
