
package org.komea.product.backend.service;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.exceptions.InvalidProviderDescriptionException;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.database.dto.PropertyDTO;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;



/**
 * This service defines the methods to load the provider API service.
 * 
 * @author sleroy
 */
@Service
public class ProviderDTOConvertorService implements IProviderDTOConvertorService
{
    
    
    public ProviderDTOConvertorService() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.service.IProviderAPIService#loadEvents(org.komea.product.backend.plugin.api.ProviderPlugin)
     */
    @Override
    public List<EventType> loadEvents(final ProviderPlugin _providerAnnotation) {
    
    
        final ArrayList<EventType> eventTypes = new ArrayList<EventType>();
        for (final EventTypeDef eventTypeDef : _providerAnnotation.eventTypes()) {
            eventTypes.add(newEventType(eventTypeDef));
        }
        return eventTypes;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.service.IProviderAPIService#loadProperties(org.komea.product.backend.plugin.api.ProviderPlugin)
     */
    @Override
    public List<PropertyDTO> loadProperties(final ProviderPlugin providerAnnotation) {
    
    
        final Property[] properties = providerAnnotation.properties();
        final List<PropertyDTO> propertyList = new ArrayList<PropertyDTO>(properties.length);
        if (properties != null) {
            for (final Property property : properties) {
                propertyList.add(new PropertyDTO(property.key(), property.value(), property.type()
                        .getName(), property.description()));
            }
        }
        return propertyList;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.service.IProviderAPIService#loadProviderDescription(org.komea.product.backend.plugin.api.ProviderPlugin)
     */
    @Override
    public Provider loadProviderDescription(final ProviderPlugin providerAnnotation) {
    
    
        final Provider provider = new Provider();
        provider.setIcon(providerAnnotation.icon());
        provider.setName(providerAnnotation.name());
        provider.setUrl(providerAnnotation.url());
        provider.setProviderType(providerAnnotation.type());
        
        return provider;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.service.IProviderAPIService#loadProviderDTO(java.lang.Object)
     */
    @Override
    public ProviderDto loadProviderDTO(final Object providerBean) {
    
    
        if (!AnnotationUtils.isAnnotationDeclaredLocally(ProviderPlugin.class,
                providerBean.getClass())) { throw new InvalidProviderDescriptionException(
                "@Provider annotation should be declared locally."); }
        final ProviderPlugin providerAnnotation =
                AnnotationUtils.findAnnotation(providerBean.getClass(), ProviderPlugin.class);
        final ProviderDto providerdto = new ProviderDto();
        final Provider provider = loadProviderDescription(providerAnnotation);
        providerdto.setProvider(provider);
        providerdto.setProperties(loadProperties(providerAnnotation));
        providerdto.setEventTypes(loadEvents(providerAnnotation));
        
        return providerdto;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.service.IProviderAPIService#newEventType(org.komea.product.backend.plugin.api.EventTypeDef)
     */
    @Override
    public EventType newEventType(final EventTypeDef _eventTypeDef) {
    
    
        final EventType eventType = new EventType();
        eventType.setCategory(_eventTypeDef.category());
        eventType.setDescription(_eventTypeDef.description());
        eventType.setEnabled(_eventTypeDef.enabled());
        eventType.setEntityType(_eventTypeDef.entityType());
        eventType.setEventKey(_eventTypeDef.key());
        eventType.setName(_eventTypeDef.name());
        eventType.setSeverity(_eventTypeDef.severity());
        return eventType;
    }
}
