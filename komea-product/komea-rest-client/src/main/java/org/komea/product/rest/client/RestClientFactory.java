
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.net.URISyntaxException;

import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.rest.client.api.IProvidersAPI;

/**
 * Komea factory to create rest api client objects
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 15 janv. 2014
 */
public enum RestClientFactory {
    
    INSTANCE;
    
    /**
     * This method create a providers api object
     * 
     * @param _serverBaseURL
     *            the server base url
     * @return a providersAPI instance
     * @throws ConnectException
     *             launch if the server can't connect to the server
     * @throws URISyntaxException
     *             launch if the server URL in not valid
     */
    public IProvidersAPI createProvidersAPI(final String _serverBaseURL) throws ConnectException, URISyntaxException {
    
        IProvidersAPI providersAPI = new ProvidersAPI();
        providersAPI.setServerBaseURL(_serverBaseURL);
        return providersAPI;
    }
    
    /**
     * This method create a events api object
     * 
     * @param _serverBaseURL
     *            the server base url
     * @return a eventsAPI instance
     * @throws ConnectException
     *             launch if the server can't connect to the server
     * @throws URISyntaxException
     *             launch if the server URL in not valid
     */
    public IEventsAPI createEventsAPI(final String _serverBaseURL) throws ConnectException, URISyntaxException {
    
        IEventsAPI eventsAPI = new EventsAPI();
        eventsAPI.setServerBaseURL(_serverBaseURL);
        return eventsAPI;
    }
}
