/**
 *
 */

package org.komea.product.wicket.statistics;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.api.IKpiRefreshingScheduler;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.service.esper.IEventStatisticsService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class StatPageTest
{


    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();



    @Before
    public void before() {


        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IQueryService.class));
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IEventStatisticsService.class));
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IStatisticsAPI.class));
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IKPIService.class));
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IKpiRefreshingScheduler.class));
        
    }


    /**
     * Test method for
     * {@link org.komea.product.wicket.statistics.StatPage#StatPage(org.apache.wicket.request.mapper.parameter.PageParameters)} .
     */
    @Test
    public final void testStatPage() throws Exception {


        wicketRule.testStart(StatPage.class);

    }
    
}
