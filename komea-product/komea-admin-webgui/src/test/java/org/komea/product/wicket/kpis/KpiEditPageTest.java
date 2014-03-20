/**
 * 
 */

package org.komea.product.wicket.kpis;



import org.junit.Rule;
import org.junit.Test;
import org.komea.product.wicket.utils.WicketTesterMethodRule;



/**
 * @author sleroy
 */
public class KpiEditPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.kpis.KpiEditPage#KpiEditPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testKpiEditPagePageParameters() throws Exception {
    
    
        wicketRule.testStart(KpiEditPage.class);
    }
    
}
