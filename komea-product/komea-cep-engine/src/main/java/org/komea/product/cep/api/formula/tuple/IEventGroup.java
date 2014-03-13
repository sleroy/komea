/**
 * 
 */

package org.komea.product.cep.api.formula.tuple;



import java.io.Serializable;
import java.util.List;



/**
 * This interface defines an event group.
 * 
 * @author sleroy
 */
public interface IEventGroup
{
    
    
    /**
     * Addd the event
     * 
     * @param _event
     *            the event
     */
    void addEvent(Serializable _event);
    
    
    /**
     * Returns the list of events
     * 
     * @return the list of events.
     */
    <T extends Serializable> List<T> getEvents();
}
