# Example project for demonstrating a Java EE 7 web application
## Prerequisites
Java 1.8
## Content and technologies
### hrlist-common

Entities and metamodels

### hrlist-service

Service web application

- JAX-RS (DepartmentRS.java, EmployeeRS.java, ServiceApplication.java)
- EJB (DBPopulator.java, DepartmentDAO.java, EmployeeDAO.java)
- JPA (DepartmentDAO.java, EmployeeDAO.java, persistence.xml)
- CDI (Hrlist.java, HrlistITTestFor.java, NameReturner.java, PersistenceUnitName.java)
- Security constraint with basic authentication (web.xml)
- JUnit, Mockito, PowerMock (DepartmentRSTest.java)
- Embedded application server GlassFish (DepartmentRSTestIT.java)
- Embedded (in-memory) database Derby (persistence.xml)

### hrlist-client

Client web application

- Managed beans (AuthBean.java, DepartmentBean.java, DepartmentManager.java, 
EmployeeBean.java, EmployeeManager.java, LocaleBean.java)
- Interceptor (Loggable.java, LoggingInterceptor.java, beans.xml)
- CDI (LoggingProducer.java)
- Security constraint with form authentication (web.xml)
- Internalization and Localization (faces-config.xml, message_en.properties, message_ru.properties, LocaleBean.java)
- JSF (*.xhtml, web.xml, faces-config.xml)
    - converter (DepartmentConverter.java, EmployeeConverter.java)
    - listener (MessageHandler.java, faces-config.xml)
    - Facelets (*.xhtml)
- PrimeFaces (*.xhtml, ru.js)
- Servlet
    - filter (NoCacheFilter.java)
- JUnit, Mockito, PowerMock (DepartmentConverterTest.java, NoCacheFilterTest.java,
MessageHandlerTest.java, AuthBeanTest.java, DepartmentBeanTest.java)

## How to run
- choose the directory: `cd hrlist/`
- install applications: `./mvnw clean install`
- change the directory and run the app: `cd hrlist-service/ && ./mvnw embedded-glassfish:run`
- address of client app: `http://localhost:8080/hrlist-client`, user "test" with pass "123"
- type "x" and press "Enter" to quit

## How to test
### Module tests
- choose the directory: `cd hrlist-client/` or `cd hrlist-service/`
- run tests: `./mvnw test`

### Integration tests
- choose the directory: `cd hrlist-service/`
- run tests: `./mvnw failsafe:integration-test`