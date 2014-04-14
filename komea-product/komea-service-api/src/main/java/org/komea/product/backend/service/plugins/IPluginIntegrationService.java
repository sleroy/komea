
package org.komea.product.backend.service.plugins;



import org.komea.product.database.dto.ProviderDto;



/**
 */
public interface IPluginIntegrationService
{
    
    
    /**
     * Registers a provider from the DTO Description.
     * 
    
     * @param _providerDTO ProviderDto
     */
    public abstract void registerProvider(ProviderDto _providerDTO);
    
}
