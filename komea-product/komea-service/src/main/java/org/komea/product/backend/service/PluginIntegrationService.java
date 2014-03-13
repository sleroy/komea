
package org.komea.product.backend.service;



import javax.validation.Valid;

import org.komea.product.backend.exceptions.InvalidProviderDescriptionException;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.backend.service.plugins.IPluginIntegrationService;
import org.komea.product.backend.service.plugins.IProviderDTOConvertorService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.EventTypeCriteria;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This service registers providers loaded at the startup of Komea.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Service
@Transactional
public class PluginIntegrationService implements IPluginIntegrationService, BeanPostProcessor
{
    
    
    private static final Logger          LOGGER = LoggerFactory.getLogger("plugin-loader");
    
    
    @Autowired
    private IEventTypeService            eventTypeService;
    
    @Autowired
    private IProviderDTOConvertorService providerAPIService;
    
    @Autowired
    private ProviderDao                  providerMapper;
    
    
    @Autowired
    private ISettingService              settingsService;
    
    
    
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
    
    
        final int existingProvider = providerMapper.countByCriteria(criteria);
        return existingProvider > 0;
    }
    
    
    /**
     * Method getEventTypeService.
     * 
     * @return IEventTypeService
     */
    public IEventTypeService getEventTypeService() {
    
    
        return eventTypeService;
    }
    
    
    /**
     * Method getProviderAPIService.
     * 
     * @return IProviderDTOConvertorService
     */
    public IProviderDTOConvertorService getProviderAPIService() {
    
    
        return providerAPIService;
    }
    
    
    /**
     * Method getProviderMapper.
     * 
     * @return ProviderDao
     */
    public ProviderDao getProviderMapper() {
    
    
        return providerMapper;
    }
    
    
    /**
     * Method getSettingsService.
     * 
     * @return ISettingService
     */
    public ISettingService getSettingsService() {
    
    
        return settingsService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(final Object _bean, final String _beanName)
            throws BeansException {
    
    
        final ProviderPlugin findAnnotation =
                AnnotationUtils.findAnnotation(_bean.getClass(), ProviderPlugin.class);
        if (findAnnotation != null) {
            LOGGER.info("Registering new provider plugin {}", _beanName);
            final ProviderDto loadProviderDTO = providerAPIService.loadProviderDTO(findAnnotation);
            registerProvider(loadProviderDTO);
        }
        return _bean;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(final Object _bean, final String _beanName)
            throws BeansException {
    
    
        return _bean;
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
        if (existSelectedProvider(criteria)) {
            LOGGER.warn("Replacing existing provider with new definition (}",
                    _providerDTO.getProvider());
            removeProvider(provider);
        }
        if (provider.getId() != null) { throw new InvalidProviderDescriptionException(
                "Producer DTO should not register primary key"); }
        providerMapper.insert(provider);
        
        // Alertes
        for (final EventType eventType : _providerDTO.getEventTypes()) {
            eventTypeService.registerEvent(provider, eventType);
        }
    }
    
    
    /**
     * Removes a provider.
     * 
     * @param _url
     *            the url
     */
    public void removeProvider(final Provider _provider) {
    
    
        final ProviderCriteria criteria = new ProviderCriteria();
        criteria.createCriteria().andUrlEqualTo(_provider.getUrl());
        final Provider provider =
                CollectionUtil.singleOrNull(providerMapper.selectByCriteria(criteria));
        providerMapper.deleteByCriteria(criteria);
        final EventTypeCriteria eventTypeCriteria = new EventTypeCriteria();
        eventTypeCriteria.createCriteria().andIdProviderEqualTo(provider.getId());
        eventTypeService.deleteByCriteria(eventTypeCriteria);
        
    }
    
    
    /**
     * Method setEventTypeService.
     * 
     * @param _eventTypeService
     *            IEventTypeService
     */
    public void setEventTypeService(final IEventTypeService _eventTypeService) {
    
    
        eventTypeService = _eventTypeService;
    }
    
    
    /**
     * Method setProviderAPIService.
     * 
     * @param _providerAPIService
     *            IProviderDTOConvertorService
     */
    public void setProviderAPIService(final IProviderDTOConvertorService _providerAPIService) {
    
    
        providerAPIService = _providerAPIService;
    }
    
    
    /**
     * Method setProviderMapper.
     * 
     * @param _providerMapper
     *            ProviderDao
     */
    public void setProviderMapper(final ProviderDao _providerMapper) {
    
    
        providerMapper = _providerMapper;
    }
    
    
    /**
     * Method setSettingsService.
     * 
     * @param _settingsService
     *            ISettingService
     */
    public void setSettingsService(final ISettingService _settingsService) {
    
    
        settingsService = _settingsService;
    }
    
    
}
