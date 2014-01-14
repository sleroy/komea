
package org.komea.product.service;



import javax.validation.Valid;

import org.komea.product.database.dto.ProviderDto;
import org.springframework.transaction.annotation.Transactional;



public interface IPluginIntegrationService
{
    
    
    /**
     * Registers a provider from the DTO Description.
     * 
     * @param _object
     *            the provider DTO description
     */
    public abstract void registerProvider(ProviderDto _providerDTO);
    
}
