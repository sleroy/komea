/**
 * 
 */

package org.komea.eventory.api.engine;



import java.io.Serializable;

import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.api.filters.IEventTransformer;



/**
 * This class defines a storage of event into the CEP.
 * 
 * @author sleroy
 */
public interface ICEPEventStorage<T extends Serializable>
{
    
    
    /**
     * Returns the cache storage.
     * 
     * @return the cache storage.
     */
    ICacheStorage<T> getCache();
    
    
    /**
     * Returns the event filter associated to the storage.
     * 
     * @return the event filter;
     */
    IEventFilter<T> getEventFilter();
    
    
    /**
     * Another solution exists to filter events, that's the event transformed, it performs filtering and transforms the event before storing
     * it in the cache.
     * 
     * @return the event transformer.
     */
    IEventTransformer<?, T> getEventTransformer();
    
    
    /**
     * Returns the event filter name.
     * 
     * @return the vent filter name.
     */
    String getFilterName();
    
    
    /**
     * Push an event into the cache.
     * 
     * @param _event
     *            the event to push.
     */
    void notifyEvent(Serializable _event);
}
