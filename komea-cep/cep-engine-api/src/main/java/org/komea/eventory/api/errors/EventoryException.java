/**
 * 
 */

package org.komea.eventory.api.errors;



/**
 * This interface defines the base exception of Komea.
 * 
 * @author sleroy
 */
public abstract class EventoryException extends RuntimeException
{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 3292215099176541490L;
    
    
    
    /**
     * Komea Exception : the message
     * 
     * @param _message
     *            the message
     */
    public EventoryException(final String _message) {
    
    
        super(_message, null);
    }
    
    
    /**
     * Builds an exception
     * 
     * @param _message
     *            the message
     * @param _throwable
     *            the throwable
     */
    public EventoryException(final String _message, final Throwable _throwable) {
    
    
        super("Eventory engine  meets an exception : \n\t-> reason :" + _message, _throwable);
        
    }
    
    
    /**
     * Builds an exception
     * 
     * @param _throwable
     *            the throwable;
     */
    public EventoryException(final Throwable _throwable) {
    
    
        super(_throwable.getMessage(), _throwable);
    }
    
}
