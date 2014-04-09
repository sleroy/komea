
package org.komea.product.backend.exceptions;



import org.komea.product.database.dto.ProviderDto;



/**
 */
public class AlreadyExistingProviderException extends RuntimeException
{
    
    
    private final ProviderDto providerDTO;
    
    
    
    /**
     * Constructor for AlreadyExistingProviderException.
     * @param _providerDTO ProviderDto
     */
    public AlreadyExistingProviderException(final ProviderDto _providerDTO) {
    
    
        super("Provider " + _providerDTO.getProvider().getName() + " is already registered.");
        providerDTO = _providerDTO;
        
        
    }
    
    
    /**
     * Method getProviderDTO.
     * @return ProviderDto
     */
    public ProviderDto getProviderDTO() {
    
    
        return providerDTO;
    }
    
}
