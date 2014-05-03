/**
 * 
 */

package org.komea.product.cep.formula;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.komea.product.cep.api.formula.IEventGroup;



/**
 * This class defines the storage of event. Basically it is a simple array list;
 * 
 * @author sleroy
 */
public class EventGroup<T extends Serializable> implements IEventGroup<T>
{
    
    
    private final List event = new ArrayList<T>();
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.IEventGroup#addEvent(java.io.T)
     */
    @Override
    public void addEvent(final T _event) {
    
    
        event.add(_event);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.IEventGroup#getEvents()
     */
    @Override
    public List<T> getEvents() {
    
    
        return event;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.IEventGroup#getFirstEvent()
     */
    @Override
    public T getFirstEvent() {
    
    
        if (event.isEmpty()) { return null; }
        return (T) event.get(0);
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "EventGroup [event=" + event + "]";
    }
    
}
