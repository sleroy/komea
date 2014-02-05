
package org.komea.product.rest.client;


import java.net.ConnectException;

import org.komea.product.database.dto.ProviderDto;
import org.komea.product.rest.client.api.IProvidersAPI;

class ProvidersAPI extends AbstractRestCientAPI implements IProvidersAPI
{
    
    private static final String PROVIDERS_PATH = "providers";
    
    @Override
    public void registerProvider(final ProviderDto _provider) throws ConnectException {
    
        String url = PROVIDERS_PATH + "/register";
        post(url, _provider, ProviderDto.class);
    }
}
