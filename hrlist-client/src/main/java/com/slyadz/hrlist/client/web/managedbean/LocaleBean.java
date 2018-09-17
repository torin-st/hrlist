package com.slyadz.hrlist.client.web.managedbean;

import com.slyadz.hrlist.client.web.interceptor.Loggable;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

/**
 * <p>
 * Managed bean that retrieves current locale, used on each page.</p>
 */
@Named
@SessionScoped
//@Loggable
public class LocaleBean extends AbstractBean implements Serializable {

    private static final long serialVersionUID = -2181710426297811604L;
    private Locale locale;
    private String localeCode;
    private Map<String, Locale> countries;

    public Map<String, Locale> getCountriesInMap() {
        return countries;
    }

    @PostConstruct
    public void init() {
        //get current locale and set it up into bound property
        locale = getContext().getViewRoot().getLocale();
        if (locale != null) {
            localeCode = locale.getLanguage();
            //also put it to the session
            getExternalContext().getSessionMap().put("locale", localeCode);
        }
        //prepopulate map of selectOneMenu
        countries = new LinkedHashMap<>();
        countries.put("English", Locale.ENGLISH);
        countries.put("Russian", new Locale("ru", ""));
    }

    public LocaleBean() {
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void componentInit(ComponentSystemEvent event) {
        Map map = getContext().getExternalContext().getSessionMap();
        //if session has locale value set it up to viewRoot and bound property
        if (map.containsKey("locale")) {
            for (Map.Entry<String, Locale> entry : countries.entrySet()) {
                if (entry.getValue().toString().equals(map.get("locale").toString())) {
                    getContext().getViewRoot().setLocale(entry.getValue());
                }
            }

            if (!map.get("locale").equals(localeCode)) {
                localeCode = map.get("locale").toString();
            }
        }

    }

    public void countryLocaleCodeChanged(ValueChangeEvent e) {
        String newLocaleValue = e.getNewValue().toString();
        //set up new value into viewRoot and put it into session
        for (Map.Entry<String, Locale> entry : countries.entrySet()) {
            if (entry.getValue().toString().equals(newLocaleValue)) {
                getContext().getViewRoot().setLocale(entry.getValue());
                getExternalContext().getSessionMap().put("locale", entry.getValue().getLanguage());
            }
        }
    }
}
