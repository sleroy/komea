
package org.komea.product.backend.service;



import javax.validation.Valid;

import org.komea.product.database.dto.ProviderDto;
import org.springframework.transaction.annotation.Transactional;



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
