/**
 * 
 */

package org.komea.product.cep.api.formula;



import java.io.Serializable;
import java.util.List;



/**
 * This interface defines an event group.
 * 
 * @author sleroy
 */
public interface IEventGroup<T extends Serializable>
{
    
    
    /**
     * Addd the event
     * 
     * @param _event
     *            the event
     */
    void addEvent(T _event);
    
    
    /**
     * Returns the list of events
     * 
     * @return the list of events.
     */
    List<T> getEvents();
    
    
    /**
     * Returns the first element or null
     * 
     * @return the first element or null.
     */
    T getFirstEvent();
    
    
}
