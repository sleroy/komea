/**
 * 
 */

package org.komea.cep.filter;



import java.io.Serializable;



/**
 * @author sleroy
 */
public class Event implements Serializable
{
    
    
    String message;
    
    
    Number value;
    
    
    
    /**
     * 
     */
    public Event() {
    
    
        // TODO Auto-generated constructor stub
    }
    
    
    /**
     * @return the message
     */
    public String getMessage() {
    
    
        return message;
    }
    
    
    /**
     * @return the value
     */
    public Number getValue() {
    
    
        return value;
    }
    
    
    /**
     * @param _message
     *            the message to set
     */
    public void setMessage(final String _message) {
    
    
        message = _message;
    }
    
    
    /**
     * @param _value
     *            the value to set
     */
    public void setValue(final Number _value) {
    
    
        value = _value;
    }
    
}
