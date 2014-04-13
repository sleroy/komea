
package org.komea.eventory.filter;



import java.io.Serializable;
import java.util.Date;



/**
 * This type defines the informations necessary to launch an alert/information.
 * 
 * @author sleroy
 */
public class Event implements Serializable
{
    
    
    private Date   date;
    
    private String eventType;
    
    
    private String message = "";
    
    
    
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
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "Event [date=" + date + ", eventType=" + eventType + ", message=" + message + "]";
    }
    
    
}
