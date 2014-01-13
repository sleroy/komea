
package org.komea.product.plugins.business;



import java.util.ArrayList;

import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
import org.komea.product.plugin.api.EventTypeDef;
import org.komea.product.plugin.api.ProviderPlugin;



public class ProviderEventTypesDescriptionLoader
{
    
    
    private final ProviderPlugin providerAnnotation;
    private final ProviderDto    providerdto;
    
    
    
    public ProviderEventTypesDescriptionLoader(
            final ProviderPlugin _providerAnnotation,
            final ProviderDto _providerdto) {
    
    
        providerAnnotation = _providerAnnotation;
        providerdto = _providerdto;
        
        
    }
    
    
    public void load() {
    
    
        providerdto.setEventTypes(new ArrayList<EventType>());
        for (final EventTypeDef eventTypeDef : providerAnnotation.eventTypes()) {
            providerdto.getEventTypes().add(newEventType(eventTypeDef));
        }
    }
    
    
    public EventType newEventType(final EventTypeDef _eventTypeDef) {
    
    
        final EventType eventType = new EventType();
        eventType.setCategory(_eventTypeDef.category());
        eventType.setDescription(_eventTypeDef.description());
        eventType.setEnabled(_eventTypeDef.enabled());
        eventType.setEntityType(_eventTypeDef.entityType());
        eventType.setKey(_eventTypeDef.key());
        eventType.setName(_eventTypeDef.name());
        eventType.setSeverity(_eventTypeDef.severity());
        return eventType;
    }
    
}
