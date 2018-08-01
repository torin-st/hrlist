/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.client.web.converter;

import com.slyadz.hrlist.entity.Department;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author A. G. Slyadz
 */
@FacesConverter(forClass = com.slyadz.hrlist.service.ws.Department.class)//"departmentCV")
public class DepartmentConverter implements Converter {

    public static final String CONVERSION_ERROR_MESSAGE_ID = "ConversionError";

    
    public DepartmentConverter() {
    }

    /**
     * Converts into Department instance from id.
     * @param context
     * @param component
     * @param newValue
     * @return 
     */
    @Override
    public Object getAsObject(FacesContext context,
            UIComponent component, String newValue)
            throws ConverterException {

        System.out.println("getAsObject:");
        
        if (newValue.isEmpty()) {
            System.out.println("getAsObject: newValue.isEmpty");
            return null;
        }
       
        com.slyadz.hrlist.service.ws.Department convertedValue = new com.slyadz.hrlist.service.ws.Department();
        com.slyadz.hrlist.entity.Department mock = null;
        
        try {
           //
           Client client = ClientBuilder.newClient();
           mock = client.target("http://localhost:8080/hrlist-service/api/departments")
                .path(newValue)
                .request(MediaType.APPLICATION_XML)
                .get(com.slyadz.hrlist.entity.Department.class);
           client.close();
           convertedValue.setId(mock.getId());
           convertedValue.setName(mock.getName());
           System.out.print("convertedValue = " + convertedValue);
           if (convertedValue == null){
            throw new NullPointerException("convertedValue is null after request!");
           }
           
        } catch (Exception e) {
            throw new ConverterException("Error while converting: " + e.getMessage());
        }

        return convertedValue;
    }

    /**
     * Formats the value by inserting space after every four characters for
     * better readability if they don't already exist. In the process converts
     * any <code>"-"</code> characters into blanks for consistency.
     * @param context
     * @param component
     * @param value
     * @return 
     */
    @Override
    public String getAsString(FacesContext context,
            UIComponent component, Object value)
            throws ConverterException {

        if (value == null) {
            System.out.println("getAsString: value == null");
            return "";
        }
        
        if (!(value instanceof com.slyadz.hrlist.service.ws.Department)) {
            System.out.println("getAsString: value is not instanceof ws.Department");
            return "";
        }

        return ((com.slyadz.hrlist.service.ws.Department) value).getId().toString();
    }
}
