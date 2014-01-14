
package org.komea.product.service;



import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.komea.product.database.dao.ProviderMapper;
import org.komea.product.database.dto.PropertyDTO;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;
import org.komea.product.plugin.api.ProviderPlugin;
import org.komea.product.plugins.exceptions.AlreadyExistingProviderException;
import org.komea.product.plugins.exceptions.InvalidProviderDescriptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This service registers providers loaded at the startup of Komea.
 * 
 * @author sleroy
 */
@Service
public class PluginIntegrationService implements IPluginIntegrationService
{
    
    
    @Autowired
    private ApplicationContext  context;
    
    private static final Logger LOGGER = LoggerFactory.getLogger("plugin-loader");
    
    @Autowired
    private ProviderMapper      providerMapper;
    
    @Autowired
    private IProviderAPIService providerAPIService;
    
    
    @Autowired
    private ISettingService     settingsService;
    
    
    @Autowired
    private IEventTypeService   eventTypeService;
    
    
    
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
    
    
    public IProviderAPIService getProviderAPIService() {
    
    
        return providerAPIService;
    }
    
    
    public ProviderMapper getProviderMapper() {
    
    
        return providerMapper;
    }
    
    
    public ISettingService getSettingsService() {
    
    
        return settingsService;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Initializing the plugin loader");
        final Map<String, Object> providerPluginBeansMap =
                context.getBeansWithAnnotation(ProviderPlugin.class);
        LOGGER.info("Found {} plugins", providerPluginBeansMap.size());
        
        for (final Entry<String, Object> providerDesc : providerPluginBeansMap.entrySet()) {
            LOGGER.debug("With bean {}", providerDesc.getKey());
            try {
                final ProviderDto loadProviderDTO =
                        providerAPIService.loadProviderDTO(providerDesc.getValue());
                registerProvider(loadProviderDTO);
            } catch (final Exception e) {
                LOGGER.error("Cannot load the provider with bean {}, has failed : ",
                        providerDesc.getKey(), e);
            }
        }
        
        LOGGER.info("Registration finished.");
        
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
        criteria.createCriteria().andNameEqualTo(provider.getProviderKey());
        if (existSelectedProvider(criteria)) { throw new AlreadyExistingProviderException(
                _providerDTO); }
        if (provider.getId() != null) { throw new InvalidProviderDescriptionException(
                "Producer DTO should not register primary key"); }
        providerMapper.insert(provider);
        // Properties
        for (final PropertyDTO property : _providerDTO.getProperties()) {
            settingsService.getOrCreate(property.getKey(), property.getValue(), property.getType());
        }
        
        // Alertes
        for (final EventType eventType : _providerDTO.getEventTypes()) {
            eventTypeService.registerEvent(provider, eventType);
        }
    }
    
    
    public void setContext(final ApplicationContext _context) {
    
    
        context = _context;
    }
    
    
    public void setEventTypeService(final IEventTypeService _eventTypeService) {
    
    
        eventTypeService = _eventTypeService;
    }
    
    
    public void setProviderAPIService(final IProviderAPIService _providerAPIService) {
    
    
        providerAPIService = _providerAPIService;
    }
    
    
    public void setProviderMapper(final ProviderMapper _providerMapper) {
    
    
        providerMapper = _providerMapper;
    }
    
    
    public void setSettingsService(final ISettingService _settingsService) {
    
    
        settingsService = _settingsService;
    }
    
    
}
