
package com.slyadz.hrlist.service.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.slyadz.hrlist.service.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UpdateEmployee_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "updateEmployee");
    private final static QName _GetEmployeesByDepartment_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "getEmployeesByDepartment");
    private final static QName _DeleteEmployeeResponse_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "deleteEmployeeResponse");
    private final static QName _GetAllEmployeesResponse_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "getAllEmployeesResponse");
    private final static QName _GetAllEmployees_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "getAllEmployees");
    private final static QName _DeleteEmployee_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "deleteEmployee");
    private final static QName _UpdateEmployeeResponse_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "updateEmployeeResponse");
    private final static QName _Department_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "department");
    private final static QName _Employee_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "employee");
    private final static QName _CreateEmployee_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "createEmployee");
    private final static QName _GetEmployeesByDepartmentResponse_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "getEmployeesByDepartmentResponse");
    private final static QName _CreateEmployeeResponse_QNAME = new QName("http://ws.service.hrlist.slyadz.com/", "createEmployeeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.slyadz.hrlist.service.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteEmployeeResponse }
     * 
     */
    public DeleteEmployeeResponse createDeleteEmployeeResponse() {
        return new DeleteEmployeeResponse();
    }

    /**
     * Create an instance of {@link GetAllEmployeesResponse }
     * 
     */
    public GetAllEmployeesResponse createGetAllEmployeesResponse() {
        return new GetAllEmployeesResponse();
    }

    /**
     * Create an instance of {@link GetEmployeesByDepartment }
     * 
     */
    public GetEmployeesByDepartment createGetEmployeesByDepartment() {
        return new GetEmployeesByDepartment();
    }

    /**
     * Create an instance of {@link UpdateEmployee }
     * 
     */
    public UpdateEmployee createUpdateEmployee() {
        return new UpdateEmployee();
    }

    /**
     * Create an instance of {@link CreateEmployeeResponse }
     * 
     */
    public CreateEmployeeResponse createCreateEmployeeResponse() {
        return new CreateEmployeeResponse();
    }

    /**
     * Create an instance of {@link CreateEmployee }
     * 
     */
    public CreateEmployee createCreateEmployee() {
        return new CreateEmployee();
    }

    /**
     * Create an instance of {@link GetEmployeesByDepartmentResponse }
     * 
     */
    public GetEmployeesByDepartmentResponse createGetEmployeesByDepartmentResponse() {
        return new GetEmployeesByDepartmentResponse();
    }

    /**
     * Create an instance of {@link Department }
     * 
     */
    public Department createDepartment() {
        return new Department();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link DeleteEmployee }
     * 
     */
    public DeleteEmployee createDeleteEmployee() {
        return new DeleteEmployee();
    }

    /**
     * Create an instance of {@link UpdateEmployeeResponse }
     * 
     */
    public UpdateEmployeeResponse createUpdateEmployeeResponse() {
        return new UpdateEmployeeResponse();
    }

    /**
     * Create an instance of {@link GetAllEmployees }
     * 
     */
    public GetAllEmployees createGetAllEmployees() {
        return new GetAllEmployees();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "updateEmployee")
    public JAXBElement<UpdateEmployee> createUpdateEmployee(UpdateEmployee value) {
        return new JAXBElement<UpdateEmployee>(_UpdateEmployee_QNAME, UpdateEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeesByDepartment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "getEmployeesByDepartment")
    public JAXBElement<GetEmployeesByDepartment> createGetEmployeesByDepartment(GetEmployeesByDepartment value) {
        return new JAXBElement<GetEmployeesByDepartment>(_GetEmployeesByDepartment_QNAME, GetEmployeesByDepartment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "deleteEmployeeResponse")
    public JAXBElement<DeleteEmployeeResponse> createDeleteEmployeeResponse(DeleteEmployeeResponse value) {
        return new JAXBElement<DeleteEmployeeResponse>(_DeleteEmployeeResponse_QNAME, DeleteEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEmployeesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "getAllEmployeesResponse")
    public JAXBElement<GetAllEmployeesResponse> createGetAllEmployeesResponse(GetAllEmployeesResponse value) {
        return new JAXBElement<GetAllEmployeesResponse>(_GetAllEmployeesResponse_QNAME, GetAllEmployeesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEmployees }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "getAllEmployees")
    public JAXBElement<GetAllEmployees> createGetAllEmployees(GetAllEmployees value) {
        return new JAXBElement<GetAllEmployees>(_GetAllEmployees_QNAME, GetAllEmployees.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "deleteEmployee")
    public JAXBElement<DeleteEmployee> createDeleteEmployee(DeleteEmployee value) {
        return new JAXBElement<DeleteEmployee>(_DeleteEmployee_QNAME, DeleteEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "updateEmployeeResponse")
    public JAXBElement<UpdateEmployeeResponse> createUpdateEmployeeResponse(UpdateEmployeeResponse value) {
        return new JAXBElement<UpdateEmployeeResponse>(_UpdateEmployeeResponse_QNAME, UpdateEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Department }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "department")
    public JAXBElement<Department> createDepartment(Department value) {
        return new JAXBElement<Department>(_Department_QNAME, Department.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Employee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "employee")
    public JAXBElement<Employee> createEmployee(Employee value) {
        return new JAXBElement<Employee>(_Employee_QNAME, Employee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "createEmployee")
    public JAXBElement<CreateEmployee> createCreateEmployee(CreateEmployee value) {
        return new JAXBElement<CreateEmployee>(_CreateEmployee_QNAME, CreateEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeesByDepartmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "getEmployeesByDepartmentResponse")
    public JAXBElement<GetEmployeesByDepartmentResponse> createGetEmployeesByDepartmentResponse(GetEmployeesByDepartmentResponse value) {
        return new JAXBElement<GetEmployeesByDepartmentResponse>(_GetEmployeesByDepartmentResponse_QNAME, GetEmployeesByDepartmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.hrlist.slyadz.com/", name = "createEmployeeResponse")
    public JAXBElement<CreateEmployeeResponse> createCreateEmployeeResponse(CreateEmployeeResponse value) {
        return new JAXBElement<CreateEmployeeResponse>(_CreateEmployeeResponse_QNAME, CreateEmployeeResponse.class, null, value);
    }

}
