
package org.komea.product.rest.client;



import java.net.ConnectException;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.rest.client.api.IProvidersAPI;



// 
public class RestClientFactoryIT
{
    
    
    @Rule
    public ServerMethodRule serverInit = new ServerMethodRule();
    
    
    
    //
    
    @Test 
    public void testCreateEventsAPI() throws ConnectException, URISyntaxException {
    
    
        final IEventsAPI eventsAPI =
                RestClientFactory.INSTANCE.createEventsAPI(serverInit.getAddress());
        Assert.assertNotNull(eventsAPI);
    }
    
    
    @Test 
    public void testCreateProvidersAPI() throws ConnectException, URISyntaxException {
    
    
        final IProvidersAPI providersAPI =
                RestClientFactory.INSTANCE.createProvidersAPI(serverInit.getAddress());
        Assert.assertNotNull(providersAPI);
    }
}
