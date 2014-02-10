
package org.komea.product.rest.client;


import java.net.ConnectException;

import org.komea.product.database.dto.ProviderDto;
import org.komea.product.rest.client.api.IProvidersAPI;
import org.komea.product.service.dto.errors.InternalServerException;

class ProvidersAPI extends AbstractRestCientAPI implements IProvidersAPI {
    
    private static final String PROVIDERS_PATH = "providers";
    
    /**
     * (non-Javadoc)
     * 
     * @see org.komea.product.rest.client.api.IProvidersAPI#registerProvider(org.komea.product.database.dto.ProviderDto)
     */
    @Override
    public void registerProvider(final ProviderDto _provider) throws InternalServerException, ConnectException {
    
        String url = PROVIDERS_PATH + "/register";
        post(url, _provider, ProviderDto.class);
    }
}
