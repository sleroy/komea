
package com.tocea.product.rest.client;


import java.net.ConnectException;

import javax.ws.rs.client.Entity;

import org.komea.product.database.dto.ProviderDto;
import org.springframework.web.bind.annotation.RequestBody;

import com.tocea.product.rest.client.api.IProvidersAPI;

class ProvidersAPI extends AbstractRestCientAPI implements IProvidersAPI
{
    
    private static final String PROVIDERS_PATH = "providers";
    
    @Override
    public void registerProvider(@RequestBody final ProviderDto _provider) throws ConnectException {
    
        if (!testConnectionValid()) {
            throw new ConnectException("can't connect to the server");
        }
        
        final String path = "register";
        getTarget().path(PROVIDERS_PATH).path(path).request().post(Entity.json(_provider), ProviderDto.class);
    }
}
