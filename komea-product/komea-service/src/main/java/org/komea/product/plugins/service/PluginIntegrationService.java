
package org.komea.product.plugins.service;



import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.NotNull;

import org.komea.product.database.dao.ProviderMapper;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.plugin.api.ProviderPlugin;
import org.komea.product.plugins.business.ProviderDescriptionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;



/**
 * This service registers providers loaded at the startup of Komea.
 * 
 * @author sleroy
 */
@Service
public class PluginIntegrationService
{
    
    
    @Autowired
    private ApplicationContext  context;
    
    private static final Logger LOGGER = LoggerFactory.getLogger("plugin-loader");
    
    @Autowired
    private ProviderMapper      providerMapper;
    
    
    
    @Autowired
    public void init() {
    
    
        LOGGER.info("Initializing the plugin loader");
        final Map<String, Object> providerPluginBeansMap =
                context.getBeansWithAnnotation(ProviderPlugin.class);
        LOGGER.info("Found {} plugins", providerPluginBeansMap.size());
        
        for (final Entry<String, Object> providerDesc : providerPluginBeansMap.entrySet()) {
            LOGGER.debug("With bean {}", providerDesc.getKey());
            try {
                final ProviderDescriptionLoader newProviderDescription =
                        newProviderDescription(providerDesc.getValue());
                registerProvider(newProviderDescription.load());
            } catch (final Exception e) {
                LOGGER.error("Cannot load the provider with bean {}, has failed : ",
                        providerDesc.getKey(), e);
            }
        }
        
        LOGGER.info("Registration finished.");
        
    }
    
    
    public ProviderDescriptionLoader newProviderDescription(@NotNull
    final Object _providerBean) {
    
    
        return new ProviderDescriptionLoader(_providerBean);
    }
    
    
    /**
     * Registers a provider from the DTO Description.
     * 
     * @param _object
     *            the provider DTO description
     */
    public void registerProvider(final ProviderDto _providerDTO) {
    
    
    }
}
