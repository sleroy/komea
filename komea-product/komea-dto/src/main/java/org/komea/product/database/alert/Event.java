/**
 *
 */
package org.komea.product.database.alert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.Provider;

/**
 * This type defines the informations necessary to launch an alert/information.
 *
 * @author sleroy
 */
public class Event implements IEvent {

    @NotNull
    private EventType eventType;

    @NotNull
    private Provider provider;

    @NotNull
    @Size(min = 1)
    private String message = "";

    @NotNull
    private List<Person> persons = new ArrayList<Person>();

    private PersonGroup personGroup = null;

    private Project project = null;

    @NotNull
    private Map<String, String> properties = new HashMap<String, String>();

    private Date date;

    private double value;

    private String url;

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#getDate()
     */
    @Override
    public Date getDate() {

        return date;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#getEventType()
     */
    @Override
    public EventType getEventType() {

        return eventType;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#getMessage()
     */
    @Override
    public String getMessage() {

        return message;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#getPersonGroup()
     */
    @Override
    public PersonGroup getPersonGroup() {

        return personGroup;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#getPersons()
     */
    @Override
    public List<Person> getPersons() {

        return persons;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#getProject()
     */
    @Override
    public Project getProject() {

        return project;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#getProperties()
     */
    @Override
    public Map<String, String> getProperties() {

        return properties;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#getProvider()
     */
    @Override
    public Provider getProvider() {

        return provider;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#getUrl()
     */
    @Override
    public String getUrl() {

        return url;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#getValue()
     */
    @Override
    public double getValue() {

        return value;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#setDate(java.util.Date)
     */
    @Override
    public void setDate(final Date _date) {

        date = _date;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#setEventType(javax.swing.event.DocumentEvent.EventType)
     */
    @Override
    public void setEventType(final EventType _eventType) {

        eventType = _eventType;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#setMessage(java.lang.String)
     */
    @Override
    public void setMessage(final String _message) {

        message = _message;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#setPersonGroup(org.komea.product.database.model.PersonGroup)
     */
    @Override
    public void setPersonGroup(final PersonGroup _personGroup) {

        personGroup = _personGroup;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#setPersons(java.util.List)
     */
    @Override
    public void setPersons(final List<Person> _persons) {

        persons = _persons;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#setProject(org.komea.product.database.model.Project)
     */
    @Override
    public void setProject(final Project _project) {

        project = _project;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#setProperties(java.util.Map)
     */
    @Override
    public void setProperties(final Map<String, String> _properties) {

        properties = _properties;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#setProvider(org.komea.product.database.model.Provider)
     */
    @Override
    public void setProvider(final Provider _provider) {

        provider = _provider;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#setUrl(java.lang.String)
     */
    @Override
    public void setUrl(final String _url) {

        url = _url;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.database.alert.IEvent#setValue(double)
     */
    @Override
    public void setValue(final double _value) {

        value = _value;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "Event [eventType="
                + eventType + ", provider=" + provider + ", message=" + message + ", persons="
                + persons + ", personGroup=" + personGroup + ", project=" + project
                + ", properties=" + properties + ", date=" + date + ", value=" + value + ", url="
                + url + "]";
    }

}
