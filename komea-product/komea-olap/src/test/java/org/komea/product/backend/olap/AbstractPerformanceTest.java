/**
 * 
 */

package org.komea.product.backend.olap;



import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
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
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.komea.product.test.spring.H2ProfilerRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public abstract class AbstractPerformanceTest extends

AbstractSpringIntegrationTestCase
{
    
    
    public static final int      KPI_BUILD              = 1;
    private static final Logger  LOGGER                 =
                                                                LoggerFactory
                                                                        .getLogger(EvaluateKpiValuesPerformanceTest.class);
    private static final int     MAX_BUILD_PER_HOUR     = 5;
    /**
     * 
     */
    private static final int     MAX_NUMBER_OF_PROJECTS = 20;
    private static List<Measure> measures               = new ArrayList<Measure>(20000);
    protected static final int   MILLI_INTERVAL         = 1000;
    
    
    
    @BeforeClass
    public static void beforeClass() {
    
    
        measures.clear();
        measures =
                FakeMeasures.generateHourlyDataForKpi(KPI_BUILD, 2, MAX_NUMBER_OF_PROJECTS,
                        MAX_BUILD_PER_HOUR);
        LOGGER.info("Number of elements {}", measures.size());
    }
    
    
    
    @Rule
    public final H2ProfilerRule h2ProfilerRule = new H2ProfilerRule();
    
    
    @Autowired
    private KpiDao              kpiDao;
    @Autowired
    private MeasureDao          measureDao;
    @Autowired
    protected IStatisticsAPI    statisticsAPI;
    
    
    
    /**
     * 
     */
    public AbstractPerformanceTest() {
    
    
        super();
    }
    
    
    @Before
    public void before() {
    
    
        measureDao.deleteByCriteria(new MeasureCriteria());
        for (final Measure measure : measures) {
            
            
            measureDao.insert(measure);
        }
    }
    
    
    @Before
    public void before2() {
    
    
        if (kpiDao.selectByPrimaryKey(1) != null) {
            return;
        }
        final Kpi record = new Kpi();
        record.setCronExpression("");
        record.setDescription("");
        record.setEntityType(EntityType.PROJECT);
        record.setEsperRequest("esperRequest");
        record.setKpiKey("bla");
        record.setName("bla");
        record.setProviderType(ProviderType.BUGTRACKER);
        record.setValueDirection(ValueDirection.BETTER);
        record.setValueMax(100d);
        record.setValueMin(0d);
        record.setValueType(ValueType.BOOL);
        
        
        record.setId(1);
        kpiDao.insert(record);
    }
    
    
    protected void sameTimeSerieConfig(final PeriodTimeSerieOptions timeSerieOptions) {
    
    
        timeSerieOptions.untilNow();
        timeSerieOptions.lastYears(10);
        
        sameTimeSerieTimeConfig(timeSerieOptions);
        assertTrue(timeSerieOptions.isValid());
    }
    
    
    protected void sameTimeSerieTimeConfig(final TimeSerieOptions _timeSerieOptions) {
    
    
        _timeSerieOptions.setKpiID(KPI_BUILD);
        _timeSerieOptions.setGroupFormula(GroupFormula.COUNT);
        
        
    }
    
}
