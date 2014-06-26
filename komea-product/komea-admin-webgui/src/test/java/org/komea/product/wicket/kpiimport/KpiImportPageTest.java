/**
 *
 */

package org.komea.product.wicket.kpiimport;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class KpiImportPageTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    @Autowired
    private IKomeaFS                    komeaFS;
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(komeaFS);
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.wicket.kpis.KpiPage#KpiPage(org.apache.wicket.request.mapper.parameter.PageParameters)} .
     */
    @Test
    public final void testKpiPage() throws Exception {
    
    
        wicketRule.testStart(KpiImportPage.class);
    }
    
}
