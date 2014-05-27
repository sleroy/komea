/**
 * 
 */

package org.komea.product.backend.service.olap;



import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiMathService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.KpiGoal;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest
{
    
    
    @Mock
    private IEventEngineService engineService;
    @Mock
    private KpiDao              kpiDao;
    @Mock
    private IKpiMathService     kpiMathService;
    
    
    @Mock
    private IQueryService       kpiQueryService;
    
    
    @Mock
    private MeasureDao          measureDao;
    
    
    @InjectMocks
    private StatisticsService   statisticsService;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.StatisticsService#evaluateKpiGoalValue(org.komea.product.database.model.KpiGoal)}.
     */
    @Test
    public final void testEvaluateKpiGoalValue() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.StatisticsService#getRemainingEffort(org.komea.product.database.model.KpiGoal)}.
     */
    @Test
    public final void testGetRemainingEffort() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.StatisticsService#getRemainingTime(org.komea.product.database.model.KpiGoal)}.
     */
    @Test
    public final void testGetRemainingTime() throws Exception {
    
    
        final DateTime plusDays = new DateTime().plusDays(2);
        final KpiGoal kpiGoal = new KpiGoal();
        kpiGoal.setUntilDate(plusDays.toDate());
        final Period remainingTime = statisticsService.getRemainingTime(kpiGoal);
        assertEquals(1, remainingTime.toStandardDays().getDays());
        
    }
    
}
