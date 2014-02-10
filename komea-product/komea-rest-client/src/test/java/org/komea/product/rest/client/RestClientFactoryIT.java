
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.rest.client.api.IProvidersAPI;

// @Ignore
public class RestClientFactoryIT extends AbstractRestClientIntegrationTestCase {
    
    //
    
    @Test
    public void testCreateEventsAPI() throws ConnectException, URISyntaxException, InterruptedException {
    
        IEventsAPI eventsAPI = RestClientFactory.INSTANCE.createEventsAPI("http://localhost:8585/komea");
        Assert.assertNotNull(eventsAPI);
    }
    
    @Test
    public void testCreateProvidersAPI() throws ConnectException, URISyntaxException {
    
        IProvidersAPI providersAPI = RestClientFactory.INSTANCE.createProvidersAPI("http://localhost:8585/komea");
        Assert.assertNotNull(providersAPI);
    }
}
