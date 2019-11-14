package com.slyadz.hrlist.client.web.converter;

import com.slyadz.hrlist.client.web.managedbean.DepartmentBean;
import com.slyadz.hrlist.entity.Department;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converter class for a Department type.
 *
 * @author A.G. Slyadz
 */
@FacesConverter(forClass = Department.class)
public class DepartmentConverter implements Converter {

    private static final String CONVERSION_ERROR_MESSAGE_ID = "ConversionError";

    public DepartmentConverter() {}

    /**
     * Convert into Department instance from id.
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

        //Workaround to "inject" DepartmentBean instance 
        DepartmentBean departmentBean = context.getApplication().evaluateExpressionGet(context, "#{departmentBean}", DepartmentBean.class);
        Department convertedValue = null;

        if (departmentBean == null) {
            throw new NullPointerException("departmentBean is null!");
        } else {
            convertedValue = departmentBean.getEntities().stream()
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
     * Convert into String from a Department instance (use it's Id).
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

        if (!(value instanceof Department)) {
            return "";
        }

        return ((Department) value).getId().toString();
    }
}
