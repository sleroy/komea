
package org.komea.product.rest.client;



import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.api.IProvidersAPI;



public class ProvidersAPIIT
{
    
    
    //
    
    
    @Rule
    public ServerMethodRule serverInit = new ServerMethodRule();
    
    
    
    @Test 
    public void testRegisterProvider() throws Exception {
    
    
        final IProvidersAPI providersAPI =
                RestClientFactory.INSTANCE.createProvidersAPI(serverInit.getAddress());
        Assert.assertNotNull(providersAPI);
        
        final ProviderDto providerDto = new ProviderDto();
        final Provider provider = new Provider();
        provider.setName("jenkins");
        provider.setUrl("http://komea.tocea.com/test/registerProvider");
        provider.setIcon("incon3.png");
        provider.setProviderType(ProviderType.CI_BUILD);
        providerDto.setProvider(provider);
        
        providersAPI.registerProvider(providerDto);
        
    }
}
