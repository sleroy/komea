/**
 * 
 */

package org.komea.eventory.api.engine;



import java.io.Serializable;
import java.util.List;



/**
 * This class defines a CEP statement. This statement is composed of :
 * <ul>
 * <li>A list of event filters with their expiration policies</li>
 * </ul>
 * filters the events received by the CEP, stores them into an appropriate cache storage.
 * 
 * @author sleroy
 */
public interface ICEPStatement<T extends Serializable>
{
    
    
    /**
     * Returns an aggregate view of the events contained in the statement. It browse all storages to obtain the information.
     * 
     * @warning the list is costly to prepare.
     * @return the list of unordered events
     */
    List<T> getAggregateView();
    
    
    /**
     * Returns the default storage and only this one!
     * 
     * @return the default storage.
     */
    List<T> getDefaultStorage();
    
    
    /**
     * Returns the filter with the given name
     * 
     * @param _filtername
     *            the filter.
     */
    ICEPEventStorage<T> getEventStorage(String _filtername);
    
    
    /**
     * Returns the list of event filters
     * 
     * @return the list of events filters.
     */
    List<ICEPEventStorage<T>> getEventStorages();
    
    
    /**
     * Methods to notify that a new event has been received.
     * 
     * @param _event
     *            the event.
     */
    void notifyEvent(T _event);
}
