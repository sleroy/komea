/**
 *
 */

package org.komea.product.wicket.kpiimport;



import java.io.File;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IKpiImportationService;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class KpiZipReadPageTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    @Autowired
    private IKpiImportationService      kpiImportationService;
    
    @Autowired
    private IKPIService                 kpiService;
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(kpiImportationService);
        wicketRule.getApplicationContextMock().putBean(kpiService);
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.wicket.kpis.KpiPage#KpiPage(org.apache.wicket.request.mapper.parameter.PageParameters)} .
     */
    @Test
    public final void testKpiPage() throws Exception {
    
    
        WicketTester newWicketTester = null;
        try {
            newWicketTester = wicketRule.newWicketTester();
            final KpiZipReadPage page =
                    newWicketTester.startPage(new KpiZipReadPage(new PageParameters(), new File(
                            "src/test/resources/scripts.zip")));
            final DataTable<KpiEntry, String> table =
                    (DataTable<KpiEntry, String>) page.get("table");
            assertEquals(0, table.getDataProvider().size());
            newWicketTester.debugComponentTrees();
            
        } finally {
            if (newWicketTester.hasNoErrorMessage().wasFailed()) {
                
                newWicketTester.dumpPage();
            }
            if (newWicketTester != null) {
                newWicketTester.destroy();
            }
        }
        
    }
}
