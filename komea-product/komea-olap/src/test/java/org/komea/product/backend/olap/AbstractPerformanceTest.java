/**
 * 
 */

package org.komea.product.backend.olap;



import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dao.timeserie.GroupFormula;
import org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions;
import org.komea.product.database.dao.timeserie.TimeSerieOptions;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
@Transactional
public abstract class AbstractPerformanceTest extends

AbstractSpringIntegrationTestCase
{
    
    
    private static final Logger  LOGGER                 =
                                                                LoggerFactory
                                                                        .getLogger(EvaluateKpiValuesPerformanceTest.class);
    private static final int     MAX_BUILD_PER_HOUR     = 5;
    /**
     * 
     */
    private static final int     MAX_NUMBER_OF_PROJECTS = 5;
    private static List<Measure> measures               = new ArrayList<Measure>(20000);
    protected static final int   MILLI_INTERVAL         = 1000;
    
    
    // @Rule
    // public final H2ProfilerRule h2ProfilerRule = new H2ProfilerRule();
    
    
    private Kpi                  generatedKpi;
    @Autowired
    private KpiDao               kpiDao;
    @Autowired
    private MeasureDao           measureDao;
    @Autowired
    protected IStatisticsAPI     statisticsAPI;
    
    
    
    /**
     * 
     */
    public AbstractPerformanceTest() {
    
    
        super();
    }
    
    
    @Before
    public void before2() {
    
    
        if (kpiDao.selectByPrimaryKey(1) != null) {
            return;
        }
        initFakeKPi();
        
        
        kpiDao.insert(generatedKpi);
        
        System.out.println("< Number of kpis : "
                + kpiDao.selectByCriteria(new KpiCriteria()).size() + " <> ID ="
                + generatedKpi.getId());
        
        
        measures.clear();
        measures =
                FakeMeasures.generateHourlyDataForKpi(generatedKpi.getId(), 2,
                        MAX_NUMBER_OF_PROJECTS, MAX_BUILD_PER_HOUR);
        LOGGER.info("Number of elements {}", measures.size());
        
        
        measureDao.deleteByCriteria(new MeasureCriteria());
        for (final Measure measure : measures) {
            
            
            measureDao.insert(measure);
        }
        System.out.println("Number of kpis : " + kpiDao.selectByCriteria(new KpiCriteria()).size());
    }


    private void initFakeKPi() {
    
    
        generatedKpi = new Kpi();
        generatedKpi.setCronExpression("");
        generatedKpi.setDescription("");
        generatedKpi.setEntityType(EntityType.PROJECT);
        generatedKpi.setEsperRequest("esperRequest");
        generatedKpi.setKpiKey("bla");
        generatedKpi.setName("bla");
        generatedKpi.setProviderType(ProviderType.BUGTRACKER);
        generatedKpi.setValueDirection(ValueDirection.BETTER);
        generatedKpi.setValueMax(100d);
        generatedKpi.setValueMin(0d);
        generatedKpi.setValueType(ValueType.BOOL);
    }
    
    
    protected void sameTimeSerieConfig(final PeriodTimeSerieOptions timeSerieOptions) {
    
    
        timeSerieOptions.untilNow();
        timeSerieOptions.lastYears(10);
        
        sameTimeSerieTimeConfig(timeSerieOptions);
        assertTrue(timeSerieOptions.isValid());
    }
    
    
    protected void sameTimeSerieTimeConfig(final TimeSerieOptions _timeSerieOptions) {
    
    
        _timeSerieOptions.setKpiID(generatedKpi.getId());
        _timeSerieOptions.setGroupFormula(GroupFormula.COUNT);
        
        
    }
    
}
