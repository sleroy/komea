/**
 * 
 */

package org.komea.product.wicket.statistics;



import org.junit.Rule;
import org.junit.Test;
import org.komea.product.wicket.utils.WicketTesterMethodRule;



/**
 * @author sleroy
 */
public class StatPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.statistics.StatPage#StatPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testStatPage() throws Exception {
    
    
        wicketRule.testStart(StatPage.class);
    }
    
}
