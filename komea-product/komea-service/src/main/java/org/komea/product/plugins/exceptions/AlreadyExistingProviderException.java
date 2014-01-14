
package org.komea.product.plugins.exceptions;



import org.komea.product.database.dto.ProviderDto;



public class AlreadyExistingProviderException extends RuntimeException
{
    
    
    private final ProviderDto providerDTO;
    
    
    
    public AlreadyExistingProviderException(final ProviderDto _providerDTO) {
    
    
        super("Provider " + _providerDTO.getProvider().getName() + " is already registered.");
        providerDTO = _providerDTO;
        
        
    }
    
    
    public ProviderDto getProviderDTO() {
    
    
        return providerDTO;
    }
    
}
