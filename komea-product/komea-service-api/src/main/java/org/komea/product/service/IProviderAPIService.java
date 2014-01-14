
package org.komea.product.service;



import java.util.List;

import org.komea.product.database.dto.PropertyDTO;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.komea.product.plugin.api.EventTypeDef;
import org.komea.product.plugin.api.ProviderPlugin;



public interface IProviderAPIService
{
    
    
    /**
     * Load the event types from the annotation.
     * 
     * @param _providerAnnotation
     * @return
     */
    public abstract List<EventType> loadEvents(ProviderPlugin _providerAnnotation);
    
    
    /**
     * Load properties from provider annotation.
     * 
     * @param providerAnnotation
     *            the provider annotation.
     * @return
     */
    public abstract List<PropertyDTO> loadProperties(ProviderPlugin providerAnnotation);
    
    
    /**
     * Loads the provider description.
     * 
     * @param providerAnnotation
     *            the provider annotation
     * @return the provider description.
     */
    public abstract Provider loadProviderDescription(ProviderPlugin providerAnnotation);
    
    
    /**
     * Loads the description from the bean.
     * 
     * @param providerBean
     * @return
     */
    public abstract ProviderDto loadProviderDTO(Object providerBean);
    
    
    /**
     * Factory : to build a new event type.
     * 
     * @param _eventTypeDef
     *            the event type definition.
     * @return the new event type.
     */
    public abstract EventType newEventType(EventTypeDef _eventTypeDef);
    
}
