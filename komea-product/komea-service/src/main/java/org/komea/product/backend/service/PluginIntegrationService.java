
package org.komea.product.backend.service;



import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.komea.product.backend.exceptions.AlreadyExistingProviderException;
import org.komea.product.backend.exceptions.InvalidProviderDescriptionException;
import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.utils.SpringUtils;
import org.komea.product.database.dao.ProviderMapper;
import org.komea.product.database.dto.PropertyDTO;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This service registers providers loaded at the startup of Komea.
 * 
 * @author sleroy
 */
@Service
public class PluginIntegrationService implements IPluginIntegrationService, ApplicationContextAware
{
    
    
    @Autowired
    private ApplicationContext           context;
    
    private static final Logger          LOGGER = LoggerFactory.getLogger("plugin-loader");
    
    @Autowired
    private ProviderMapper               providerMapper;
    
    @Autowired
    private IProviderDTOConvertorService providerAPIService;
    
    @Autowired
    private ISettingService              settingsService;
    
    
    @Autowired
    private IProviderSettingService      providerSettingsService;
    
    @Autowired
    private IEventTypeService            eventTypeService;
    
    
    
    public PluginIntegrationService() {
    
    
        super();
    }
    
    
    /**
     * Tests if a provider is existing.
     * 
     * @param criteria
     *            the criteria
     * @return true if the provider is existing.
     */
    public boolean existSelectedProvider(final ProviderCriteria criteria) {
    
    
        final int existingProvider = providerMapper.countByExample(criteria);
        return existingProvider > 0;
    }
    
    
    public ApplicationContext getContext() {
    
    
        return context;
    }
    
    
    public IEventTypeService getEventTypeService() {
    
    
        return eventTypeService;
    }
    
    
    public IProviderDTOConvertorService getProviderAPIService() {
    
    
        return providerAPIService;
    }
    
    
    public ProviderMapper getProviderMapper() {
    
    
        return providerMapper;
    }
    
    
    public IProviderSettingService getProviderSettingsService() {
    
    
        return providerSettingsService;
    }
    
    
    public ISettingService getSettingsService() {
    
    
        return settingsService;
    }
    
    
    @PostConstruct
    public void init() {
    
    
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.service.IPluginIntegrationService#registerProvider(org.komea.product.database.dto.ProviderDto)
     */
    @Override
    @Transactional
    public void registerProvider(@Valid
    final ProviderDto _providerDTO) {
    
    
        final Provider provider = _providerDTO.getProvider();
        LOGGER.info("Registering provider {}", provider.getName());
        final ProviderCriteria criteria = new ProviderCriteria();
        criteria.createCriteria().andUrlEqualTo(provider.getUrl());
        if (existSelectedProvider(criteria)) { throw new AlreadyExistingProviderException(
                _providerDTO); }
        if (provider.getId() != null) { throw new InvalidProviderDescriptionException(
                "Producer DTO should not register primary key"); }
        providerMapper.insert(provider);
        // Properties
        for (final PropertyDTO property : _providerDTO.getProperties()) {
            providerSettingsService.create(provider.getId(), property.getKey(),
                    property.getValue(), property.getType(), property.getDescription());
        }
        
        // Alertes
        for (final EventType eventType : _providerDTO.getEventTypes()) {
            eventTypeService.registerEvent(provider, eventType);
        }
    }
    
    
    @Override
    public void setApplicationContext(final ApplicationContext _applicationContext)
            throws BeansException {
    
    
        context = _applicationContext;
        
        LOGGER.info("Initializing the plugin loader");
        
        final Map<String, Object> providerPluginBeansMap =
                context.getBeansWithAnnotation(ProviderPlugin.class);
        LOGGER.info("Found {} plugins", providerPluginBeansMap.size());
        
        for (final Entry<String, Object> providerDesc : providerPluginBeansMap.entrySet()) {
            LOGGER.debug("With bean {}", providerDesc.getKey());
            try {
                final ProviderDto loadProviderDTO =
                        providerAPIService.loadProviderDTO(context.findAnnotationOnBean(
                                providerDesc.getKey(), ProviderPlugin.class));
                registerProvider(loadProviderDTO);
            } catch (final Exception e) {
                LOGGER.error("Cannot load the provider with bean {}, has failed : ",
                        providerDesc.getKey(), e);
            }
        }
        
        LOGGER.info("Registration finished.");
        LOGGER.info("Creating new Properties.");
        final Map<String, Object> propertiesBeansMap =
                context.getBeansWithAnnotation(Properties.class);
        LOGGER.info("Properties created.");
        LOGGER.info("Found {} custom properties annotations", propertiesBeansMap.size());
        
        for (final Properties properties : SpringUtils.findAnnotations(context, Properties.class)) {
            for (final Property property : properties.value()) {
                settingsService.create(property.key(), property.value(), property.type().getName(),
                        property.description());
            }
        }
        
    }
    
    
    public void setContext(final ApplicationContext _context) {
    
    
        context = _context;
    }
    
    
    public void setEventTypeService(final IEventTypeService _eventTypeService) {
    
    
        eventTypeService = _eventTypeService;
    }
    
    
    public void setProviderAPIService(final IProviderDTOConvertorService _providerAPIService) {
    
    
        providerAPIService = _providerAPIService;
    }
    
    
    public void setProviderMapper(final ProviderMapper _providerMapper) {
    
    
        providerMapper = _providerMapper;
    }
    
    
    public void setProviderSettingsService(final IProviderSettingService _providerSettingsService) {
    
    
        providerSettingsService = _providerSettingsService;
    }
    
    
    public void setSettingsService(final ISettingService _settingsService) {
    
    
        settingsService = _settingsService;
    }
}
