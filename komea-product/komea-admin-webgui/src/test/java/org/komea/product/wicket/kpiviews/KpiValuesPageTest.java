/**
 *
 */

package org.komea.product.wicket.kpiviews;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.komea.product.wicket.kpivalues.KpiValuesPage;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class KpiValuesPageTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    @Autowired
    private IEntityService              entityService;
    @Autowired
    private IEventEngineService         eventEngineService;
    
    @Autowired
    private IQueryService               kpiQueryService;
    
    @Autowired
    private IKPIService                 kpiService;
    
    @Autowired
    private MeasureDao                  measureDAO;
    
    @Autowired
    private IStatisticsAPI              statisticsAPI;
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(statisticsAPI);
        wicketRule.getApplicationContextMock().putBean(entityService);
        wicketRule.getApplicationContextMock().putBean(kpiService);
        wicketRule.getApplicationContextMock().putBean(eventEngineService);
        wicketRule.getApplicationContextMock().putBean(kpiQueryService);
        wicketRule.getApplicationContextMock().putBean(measureDAO);
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.wicket.kpis.KpiPage#KpiPage(org.apache.wicket.request.mapper.parameter.PageParameters)} .
     */
    @Test
    public final void testKpiPage() throws Exception {
    
    
        wicketRule.testStart(KpiValuesPage.class);
    }
    
}
