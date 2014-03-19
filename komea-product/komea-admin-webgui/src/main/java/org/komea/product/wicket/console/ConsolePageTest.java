/**
 * 
 */

package org.komea.product.wicket.console;



import org.junit.Rule;
import org.junit.Test;
import org.komea.product.wicket.WicketTesterMethodRule;



/**
 * @author sleroy
 */
public class ConsolePageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.console.ConsolePage#ConsolePage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testConsolePage() throws Exception {
    
    
        wicketRule.testStart(ConsolePage.class);
        
    }
    
}
