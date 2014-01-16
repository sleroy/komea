
package org.komea.product.backend.service;


import java.util.List;

import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.database.dto.PropertyDTO;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;

public interface IProviderAPIService
{
    
    /**
     * Load the event types from the annotation.
     * 
     * @param _providerAnnotation
     * @return
     */
    List<EventType> loadEvents(ProviderPlugin _providerAnnotation);
    
    /**
     * Load properties from provider annotation.
     * 
     * @param providerAnnotation
     *            the provider annotation.
     * @return
     */
    List<PropertyDTO> loadProperties(ProviderPlugin providerAnnotation);
    
    /**
     * Loads the provider description.
     * 
     * @param providerAnnotation
     *            the provider annotation
     * @return the provider description.
     */
    Provider loadProviderDescription(ProviderPlugin providerAnnotation);
    
    /**
     * Loads the description from the bean.
     * 
     * @param providerBean
     * @return
     */
    ProviderDto loadProviderDTO(Object providerBean);
    
    /**
     * Factory : to build a new event type.
     * 
     * @param _eventTypeDef
     *            the event type definition.
     * @return the new event type.
     */
    EventType newEventType(EventTypeDef _eventTypeDef);
    
}
