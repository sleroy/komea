
package org.komea.product.database.alert;



import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.Provider;



public interface IEvent extends Serializable
{
    
    
    /**
     * @return the date
     */
    Date getDate();
    
    
    /**
     * @return the eventType
     */
    EventType getEventType();
    
    
    /**
     * @return the message
     */
    String getMessage();
    
    
    /**
     * @return the person
     */
    Person getPerson();
    
    
    /**
     * @return the personGroup
     */
    PersonGroup getPersonGroup();
    
    
    /**
     * @return the project
     */
    Project getProject();
    
    
    /**
     * @return the properties
     */
    Map<String, String> getProperties();
    
    
    /**
     * @return the provider
     */
    Provider getProvider();
    
    
    /**
     * @return the url
     */
    String getUrl();
    
    
    /**
     * @return the value
     */
    double getValue();
    
    
    /**
     * @param _date
     *            the date to set
     */
    void setDate(Date _date);
    
    
    /**
     * @param _eventType
     *            the eventType to set
     */
    void setEventType(EventType _eventType);
    
    
    /**
     * @param _message
     *            the message to set
     */
    void setMessage(String _message);
    
    
    /**
     * @param _persons
     *            the persons to set
     */
    void setPerson(Person _person);
    
    
    /**
     * @param _personGroup
     *            the personGroup to set
     */
    void setPersonGroup(PersonGroup _personGroup);
    
    
    /**
     * @param _project
     *            the project to set
     */
    void setProject(Project _project);
    
    
    /**
     * @param _properties
     *            the properties to set
     */
    void setProperties(Map<String, String> _properties);
    
    
    /**
     * @param _provider
     *            the provider to set
     */
    void setProvider(Provider _provider);
    
    
    /**
     * @param _url
     *            the url to set
     */
    void setUrl(String _url);
    
    
    /**
     * @param _value
     *            the value to set
     */
    void setValue(double _value);
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    String toString();
    
}
