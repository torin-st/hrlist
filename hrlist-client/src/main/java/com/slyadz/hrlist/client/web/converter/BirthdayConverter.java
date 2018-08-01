/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slyadz.hrlist.client.web.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author A. G. Slyadz
 */
@FacesConverter("birthday")
public class BirthdayConverter implements Converter {

    public static final String CONVERSION_ERROR_MESSAGE_ID = "ConversionError";

    public BirthdayConverter() {
    }

    /**
     * <p>Parses the CreditCardNumber and strips any blanks or
     * <code>"-"</code> characters from it.</p>
     */
    @Override
    public Object getAsObject(FacesContext context,
            UIComponent component, String newValue)
            throws ConverterException {

        if (newValue.isEmpty()) {
            return null;
        }

        XMLGregorianCalendar convertedValue = null;
        
        try {
            convertedValue = DatatypeFactory.newInstance().newXMLGregorianCalendar(newValue);
        } catch(DatatypeConfigurationException e){
            System.err.println("Convertion error");
        }

        return convertedValue;
    }

    /**
     * Formats the value by inserting space after every four characters for
     * better readability if they don't already exist. In the process converts
     * any
     * <code>"-"</code> characters into blanks for consistency.
     */
    @Override
    public String getAsString(FacesContext context,
            UIComponent component, Object value)
            throws ConverterException {

        XMLGregorianCalendar inputVal = null;

        if (value == null) {
            return "";
        }

        // Value must be of a type that can be cast to a String.
        try {
            inputVal = (XMLGregorianCalendar) value;
        } catch (ClassCastException ce) {
            FacesMessage errMsg = new FacesMessage(CONVERSION_ERROR_MESSAGE_ID);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }

        String convertedValue = inputVal.toString();

        return convertedValue;
    }    
}
