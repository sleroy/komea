/**
 * 
 */

package org.komea.product.wicket.providers;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class ProviderPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IProviderService.class));
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.providers.ProviderPage#ProviderPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test 
    public final void testProviderPage() throws Exception {
    
    
        wicketRule.testStart(ProviderPage.class);
    }
    
}
