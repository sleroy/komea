
package org.komea.product.rest.client;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.api.IProvidersAPI;

public class ProvidersAPIIT extends AbstractRestClientIntegrationTestCase {
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @Test
    public void testRegisterProvider() throws Exception {
    
        IProvidersAPI providersAPI = RestClientFactory.INSTANCE.createProvidersAPI("http://localhost:8585/komea");
        Assert.assertNotNull(providersAPI);
        
        ProviderDto providerDto = new ProviderDto();
        Provider provider = new Provider();
        provider.setName("jenkins");
        provider.setUrl("http://...");
        providerDto.setProvider(provider);
        
        providersAPI.registerProvider(providerDto);
        
    }
}
