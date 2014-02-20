
package org.komea.product.backend.service.plugins;



import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.EventTypeCriteria;
import org.komea.product.database.model.Provider;



/**
 * 
 */
public interface IEventTypeService extends IGenericService<EventType, Integer, EventTypeCriteria>
{
    
    
    /**
     * Registers an event
     * 
     * @param _provider
     *            the provider
     * @param _eventType
     *            the event type.
     */
    public void registerEvent(Provider _provider, EventType _eventType);
    
    
}
