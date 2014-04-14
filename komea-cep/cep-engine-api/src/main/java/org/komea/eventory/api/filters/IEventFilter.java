/**
 * 
 */

package org.komea.eventory.api.filters;



import java.io.Serializable;



/**
 * This class defines the event filter.
 * 
 * @author sleroy
 */
public interface IEventFilter<T extends Serializable>
{
    
    
    /**
     * Tests if this event should be filtered (and pushed) into the cache.
     * 
     * @param _event
     *            the event
     * @return true if this event is filtered and next pushed into the cache.
     */
    public boolean isFiltered(T _event);
}
