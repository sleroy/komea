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
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.KpiGoal;
import org.komea.product.model.timeserie.EntityIdValue;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeScale;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



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
    
    
        initKpiService();
        initMeasureService();
        final Double evaluateKpiGoalValue =
                statisticsService.evaluateKpiGoalValue(buildFakeKpiGoal());
        assertEquals(50.0d, evaluateKpiGoalValue.doubleValue(), 0);
        verify(measureDao, times(1)).evaluateKpiValuesOnPeriod(
                Mockito.any(PeriodTimeSerieOptions.class));
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.olap.StatisticsService#getRemainingEffort(org.komea.product.database.model.KpiGoal)}.
     */
    @Test
    public final void testGetRemainingEffort() throws Exception {
    
    
        final KpiGoal kpiGoal = buildFakeKpiGoal();
        initKpiService();
        
        initMeasureService();
        
        final Double remainingEffort = statisticsService.getRemainingEffort(kpiGoal);
        assertEquals(-49.9, remainingEffort, 0.5);
        
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
    
    
    private KpiGoal buildFakeKpiGoal() {
    
    
        final KpiGoal kpiGoal = new KpiGoal();
        kpiGoal.setId(1);
        kpiGoal.setIdKpi(1);
        kpiGoal.setValue(100.0d);
        kpiGoal.setFrequency(TimeScale.PER_DAY.name());
        return kpiGoal;
    }
    
    
    private void initKpiService() {
    
    
        final Kpi kpi = new Kpi();
        kpi.setEsperRequest("bla");
        kpi.setEntityType(EntityType.PROJECT);
        kpi.setGroupFormula(GroupFormula.AVG_VALUE);
        kpi.setId(1);
        when(kpiDao.selectByCriteriaWithBLOBs(Mockito.any(KpiCriteria.class))).thenReturn(
                Lists.newArrayList(kpi));
    }
    
    
    private void initMeasureService() {
    
    
        final EntityIdValue entityIdValue = new EntityIdValue();
        entityIdValue.setEntityID(1);
        entityIdValue.setValue(50d);
        when(measureDao.evaluateKpiValuesOnPeriod(Mockito.any(PeriodTimeSerieOptions.class)))
                .thenReturn(Lists.newArrayList(entityIdValue));
    }
    
}
