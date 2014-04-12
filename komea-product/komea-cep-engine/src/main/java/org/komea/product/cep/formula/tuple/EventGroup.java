/**
 * 
 */

package org.komea.product.cep.formula.tuple;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.komea.product.cep.api.formula.tuple.IEventGroup;



/**
 * This class defines the storage of event. Basically it is a simple array list;
 * 
 * @author sleroy
 */
public class EventGroup implements IEventGroup
{
    
    
    private final List event = new ArrayList<Serializable>();
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.IEventGroup#addEvent(java.io.Serializable)
     */
    @Override
    public void addEvent(final Serializable _event) {
    
    
        event.add(_event);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.IEventGroup#getEvents()
     */
    @Override
    public <T extends Serializable> List<T> getEvents() {
    
    
        return event;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.formula.tuple.IEventGroup#getFirstEvent()
     */
    @Override
    public <T extends Serializable> T getFirstEvent() {
    
    
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
