package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EventSimpleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Date date;

    @NotNull
    @Size(min = 0, max = 255)
    private String eventType;

    @NotNull
    @Size(min = 1)
    private String message = "";

    private String person = null;
    private String personGroup = null;
    private String project = null;
    @NotNull
    private Map<String, String> properties = new HashMap<String, String>();

    @NotNull
    @Size(min = 0, max = 255)
    private String provider;
    private String url;

    private double value;

    public EventSimpleDto() {

        super();
    }

    /**
     * @return the date
     */
    public Date getDate() {

        return date;
    }

    /**
     * @return the eventType
     */
    public String getEventType() {

        return eventType;
    }

    /**
     * @return the message
     */
    public String getMessage() {

        return message;
    }

    /**
     * @return the person
     */
    public String getPerson() {

        return person;
    }

    /**
     * @return the personGroup
     */
    public String getPersonGroup() {

        return personGroup;
    }

    /**
     * @return the project
     */
    public String getProject() {

        return project;
    }

    /**
     * @return the properties
     */
    public Map<String, String> getProperties() {

        return properties;
    }

    /**
     * @return the provider
     */
    public String getProvider() {

        return provider;
    }

    /**
     * @return the url
     */
    public String getUrl() {

        return url;
    }

    /**
     * @return the value
     */
    public double getValue() {

        return value;
    }

    /**
     * @param _date the date to set
     */
    public void setDate(final Date _date) {

        date = _date;
    }

    /**
     * @param _eventType the eventType to set
     */
    public void setEventType(final String _eventType) {

        eventType = _eventType;
    }

    /**
     * @param _message the message to set
     */
    public void setMessage(final String _message) {

        message = _message;
    }

    /**
     * @param _persons the person to set
     */
    public void setPerson(final String _person) {

        person = _person;
    }

    /**
     * @param _personGroup the personGroup to set
     */
    public void setPersonGroup(final String _personGroup) {

        personGroup = _personGroup;
    }

    /**
     * @param _project the project to set
     */
    public void setProject(final String _project) {

        project = _project;
    }

    /**
     * @param _properties the properties to set
     */
    public void setProperties(final Map<String, String> _properties) {

        properties = _properties;
    }

    /**
     * @param _provider the provider to set
     */
    public void setProvider(final String _provider) {

        provider = _provider;
    }

    /**
     * @param _url the url to set
     */
    public void setUrl(final String _url) {

        url = _url;
    }

    /**
     * @param _value the value to set
     */
    public void setValue(final double _value) {

        value = _value;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "EventSimpleDto [eventType=" + eventType + ", provider=" + provider + ", message=" + message + ", person=" + person
                + ", personGroup=" + personGroup + ", project=" + project + ", properties=" + properties + ", date=" + date + ", value="
                + value + ", url=" + url + "]";
    }
}
