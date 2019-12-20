package com.slyadz.hrlist.client.web.converter;

import com.slyadz.hrlist.client.web.managedbean.EmployeeBean;
import com.slyadz.hrlist.entity.Employee;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converter class for a Employee type.
  */
@FacesConverter(forClass = Employee.class)
public class EmployeeConverter implements Converter {

    public static final String CONVERSION_ERROR_MESSAGE_ID = "ConversionError";

    public EmployeeConverter() {
    }

    /**
     * Converts into Employee instance from id.
     *
     * @param context Faces context
     * @param component UI component
     * @param newValue new value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context,
            UIComponent component, String newValue)
            throws ConverterException {
        if (newValue.isEmpty()) {
            throw new IllegalArgumentException("New value is empty!");

        }
        //Workaround to "inject" EmployeeBean instance 
        EmployeeBean employeeBean = context.getApplication().evaluateExpressionGet(context, "#{employeeBean}", EmployeeBean.class);
        Employee convertedValue = null;

        if (employeeBean == null) {
            throw new NullPointerException("employeeBean is null!");
        } else {
            convertedValue = employeeBean.getEntities().stream()
                    .filter(x -> newValue.equals(x.getId().toString()))
                    .findAny()
                    .orElse(null);
        }

        if (convertedValue == null) {
            throw new NullPointerException("Converted value is null after request!");
        }

        return convertedValue;
    }

    /**
     * Converts into String from a Employee instance (uses it's Id).
     *
     * @param context Faces context
     * @param component UI component
     * @param value original value
     * @return
     */
    @Override
    public String getAsString(FacesContext context,
            UIComponent component, Object value)
            throws ConverterException {
        if (value == null) {
            return "";
        }

        if (!(value instanceof Employee)) {
            return "";
        }

        return ((Employee) value).getId().toString();
    }
}
