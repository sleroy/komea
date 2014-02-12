
package org.komea.product.database.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement(name = "event")
public class EventSimpleDto implements Serializable {
    
    private static final long   serialVersionUID = 1L;
    
    @NotNull
    @Size(min = 0, max = 255)
    private String              eventType;
    
    @NotNull
    @Size(min = 0, max = 255)
    private String              provider;
    
    @NotNull
    @NotEmpty
    private String              message          = "";
    
    @NotNull
    private List<String>        persons          = new ArrayList<String>();
    private String              personGroup      = null;
    private String              project          = null;
    private Map<String, String> properties;
    
    @NotNull
    private Date                date;
    private double              value;
    
    @NotNull
    @NotEmpty
    private String              url;
    
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
     * @return the personGroup
     */
    public String getPersonGroup() {
    
        return personGroup;
    }
    
    /**
     * @return the persons
     */
    public List<String> getPersons() {
    
        return persons;
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
     * @param _date
     *            the date to set
     */
    public void setDate(final Date _date) {
    
        date = _date;
    }
    
    /**
     * @param _eventType
     *            the eventType to set
     */
    public void setEventType(final String _eventType) {
    
        eventType = _eventType;
    }
    
    /**
     * @param _message
     *            the message to set
     */
    public void setMessage(final String _message) {
    
        message = _message;
    }
    
    /**
     * @param _personGroup
     *            the personGroup to set
     */
    public void setPersonGroup(final String _personGroup) {
    
        personGroup = _personGroup;
    }
    
    /**
     * @param _persons
     *            the persons to set
     */
    public void setPersons(final List<String> _persons) {
    
        persons = _persons;
    }
    
    /**
     * @param _project
     *            the project to set
     */
    public void setProject(final String _project) {
    
        project = _project;
    }
    
    /**
     * @param _properties
     *            the properties to set
     */
    public void setProperties(final Map<String, String> _properties) {
    
        properties = _properties;
    }
    
    /**
     * @param _provider
     *            the provider to set
     */
    public void setProvider(final String _provider) {
    
        provider = _provider;
    }
    
    /**
     * @param _url
     *            the url to set
     */
    public void setUrl(final String _url) {
    
        url = _url;
    }
    
    /**
     * @param _value
     *            the value to set
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
    
        return "EventSimpleDto [eventType=" + eventType + ", provider=" + provider + ", message=" + message + ", persons=" + persons
                + ", personGroup=" + personGroup + ", project=" + project + ", properties=" + properties + ", date=" + date + ", value="
                + value + ", url=" + url + "]";
    }
}
