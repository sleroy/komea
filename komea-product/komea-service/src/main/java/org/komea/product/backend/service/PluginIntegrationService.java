package org.komea.product.backend.service;

import javax.validation.Valid;
import org.komea.product.backend.exceptions.InvalidProviderDescriptionException;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.backend.service.plugins.IPluginIntegrationService;
import org.komea.product.backend.service.plugins.IProviderDTOConvertorService;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
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
public class PluginIntegrationService implements IPluginIntegrationService, BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger("plugin-loader");

    @Autowired
    private IEventTypeService eventTypeService;

    @Autowired
    private IProviderDTOConvertorService providerAPIService;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private ISettingService settingsService;

    public PluginIntegrationService() {

        super();
    }

    /**
     * Tests if a provider is existing.
     *
     * @param criteria the criteria
     * @return true if the provider is existing.
     */
    public boolean existSelectedProvider(final ProviderCriteria criteria) {

        final int existingProvider = providerService.countByCriteria(criteria);
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

    public IProviderService getProviderService() {
        return providerService;
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

        final ProviderPlugin findAnnotation
                = AnnotationUtils.findAnnotation(_bean.getClass(), ProviderPlugin.class);
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
        if (provider.getId() != null) {
            throw new InvalidProviderDescriptionException(
                    "Producer DTO should not register primary key");
        }
        providerService.insert(provider);

        // Alertes
        for (final EventType eventType : _providerDTO.getEventTypes()) {
            eventTypeService.registerEvent(eventType);
        }
    }

    /**
     * Removes a provider.
     *
     * @param _url the url
     */
    public void removeProvider(final Provider _provider) {

        providerService.removeProvider(_provider);
    }

    /**
     * Method setEventTypeService.
     *
     * @param _eventTypeService IEventTypeService
     */
    public void setEventTypeService(final IEventTypeService _eventTypeService) {

        eventTypeService = _eventTypeService;
    }

    public void setProviderService(IProviderService providerService) {
        this.providerService = providerService;
    }

    /**
     * Method setProviderAPIService.
     *
     * @param _providerAPIService IProviderDTOConvertorService
     */
    public void setProviderAPIService(final IProviderDTOConvertorService _providerAPIService) {

        providerAPIService = _providerAPIService;
    }

    /**
     * Method setSettingsService.
     *
     * @param _settingsService ISettingService
     */
    public void setSettingsService(final ISettingService _settingsService) {

        settingsService = _settingsService;
    }

}
