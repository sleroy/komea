
package com.tocea.product.rest.client.api;



import java.net.ConnectException;

import org.komea.product.database.dto.ProviderDto;



/**
 * Komea interface to manage providers
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 15 janv. 2014
 */
public interface IProvidersAPI extends IRestClientAPI
{
    
    
    /**
     * This method register an external provider into komea.
     * After this, providers can use komea to send alert
     * 
     * @param _provider
     *            the provider to register
     * @throws ConnectException
     *             launch if it can't connect to the server
     */
    void registerProvider(final ProviderDto _provider) throws ConnectException;
}
