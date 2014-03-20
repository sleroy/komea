/**
 * 
 */

package org.komea.product.wicket.providers;



import org.junit.Rule;
import org.junit.Test;
import org.komea.product.wicket.utils.WicketTesterMethodRule;



/**
 * @author sleroy
 */
public class ProviderPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.providers.ProviderPage#ProviderPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testProviderPage() throws Exception {
    
    
        wicketRule.testStart(ProviderPage.class);
    }
    
}
