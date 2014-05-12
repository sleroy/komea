
package org.komea.product.backend.olap;



import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dao.timeserie.GroupFormula;
import org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions;
import org.komea.product.database.dao.timeserie.TimeScale;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;



public class BuildPeriodTimeSeriesPerformanceTest extends AbstractSpringIntegrationTestCase
{
    
    
    private final static int     KPI_BUILD              = 1;
    
    private static final Logger  LOGGER                 =
                                                                LoggerFactory
                                                                        .getLogger(BuildPeriodTimeSeriesPerformanceTest.class);
    
    private static final int     MAX_BUILD_PER_HOUR     = 5;
    
    /**
     * 
     */
    private static final int     MAX_NUMBER_OF_PROJECTS = 20;
    
    /**
     * 
     */
    private static final int     MAX_YEARS              = 2;
    
    
    private static List<Measure> measures               = new ArrayList<Measure>(20000);
    
    
    private static final int     MILLI_INTERVAL         = 1000;
    
    
    
    @BeforeClass
    public static void beforeClass() {
    
    
        measures.clear();
        measures =
                FakeMeasures.generateHourlyDataForKpi(KPI_BUILD, MAX_YEARS, MAX_NUMBER_OF_PROJECTS,
                        MAX_BUILD_PER_HOUR);
        LOGGER.info("Number of elements {}", measures.size());
    }
    
    
    
    // @Rule
    // public final H2ProfilerRule h2ProfilerRule = new H2ProfilerRule();
    
    
    @Autowired
    private MeasureDao     measureDao;
    
    @Autowired
    private IStatisticsAPI statisticsAPI;
    
    
    
    @Before
    public void before() {
    
    
        measureDao.deleteByCriteria(new MeasureCriteria());
        for (final Measure measure : measures) {
            
            
            measureDao.insert(measure);
        }
    }
    
    
    @Test(timeout = 52 * MILLI_INTERVAL)
    @Transactional
    public void groupElementsPerDay() {
    
    
        final BasicMicroBenchmark basicMicroBenchmark = new BasicMicroBenchmark();
        basicMicroBenchmark.setTestName("groupDay");
        basicMicroBenchmark.run(new Runnable()
        {
            
            
            @Override
            public void run() {
            
            
                final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions();
                timeSerieOptions.setTimeScale(TimeScale.PER_DAY);
                sameTimeSerieConfig(timeSerieOptions);
                statisticsAPI.buildPeriodTimeSeries(timeSerieOptions,
                        EntityKey.of(EntityType.PROJECT, 1));
                
                
            }
            
            
        });
        
        
    }
    
    
    @Test(timeout = 46 * MILLI_INTERVAL)
    @Transactional
    public void groupElementsPerHour() {
    
    
        final BasicMicroBenchmark basicMicroBenchmark = new BasicMicroBenchmark();
        basicMicroBenchmark.setTestName("groupHour");
        basicMicroBenchmark.run(new Runnable()
        {
            
            
            @Override
            public void run() {
            
            
                final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions();
                timeSerieOptions.setTimeScale(TimeScale.PER_HOUR);
                sameTimeSerieConfig(timeSerieOptions);
                statisticsAPI.buildPeriodTimeSeries(timeSerieOptions,
                        EntityKey.of(EntityType.PROJECT, 1));
                
                
            }
        });
        
        
    }
    
    
    @Test(timeout = 100 * MILLI_INTERVAL)
    @Transactional
    public void groupElementsPerMonth() {
    
    
        final BasicMicroBenchmark basicMicroBenchmark = new BasicMicroBenchmark();
        basicMicroBenchmark.setTestName("groupMonth");
        basicMicroBenchmark.run(new Runnable()
        {
            
            
            @Override
            public void run() {
            
            
                final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions();
                timeSerieOptions.setTimeScale(TimeScale.PER_MONTH);
                sameTimeSerieConfig(timeSerieOptions);
                statisticsAPI.buildPeriodTimeSeries(timeSerieOptions,
                        EntityKey.of(EntityType.PROJECT, 1));
            }
        });
        
    }
    
    
    @Test(timeout = 30 * MILLI_INTERVAL)
    @Transactional
    public void groupElementsPerWeek() {
    
    
        final BasicMicroBenchmark basicMicroBenchmark = new BasicMicroBenchmark();
        basicMicroBenchmark.setTestName("groupWeek");
        basicMicroBenchmark.run(new Runnable()
        {
            
            
            @Override
            public void run() {
            
            
                final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions();
                timeSerieOptions.setTimeScale(TimeScale.PER_WEEK);
                sameTimeSerieConfig(timeSerieOptions);
                statisticsAPI.buildPeriodTimeSeries(timeSerieOptions,
                        EntityKey.of(EntityType.PROJECT, 1));
                
            }
        });
        //
        
    }
    
    
    @Test(timeout = 32 * MILLI_INTERVAL)
    @Transactional
    public void groupElementsPerYear() {
    
    
        final BasicMicroBenchmark basicMicroBenchmark = new BasicMicroBenchmark();
        basicMicroBenchmark.setTestName("groupYears");
        basicMicroBenchmark.run(new Runnable()
        {
            
            
            @Override
            public void run() {
            
            
                final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions();
                timeSerieOptions.setTimeScale(TimeScale.PER_YEAR);
                sameTimeSerieConfig(timeSerieOptions);
                statisticsAPI.buildPeriodTimeSeries(timeSerieOptions,
                        EntityKey.of(EntityType.PROJECT, 1));
                
                
            }
        });
        
    }
    
    
    private void sameTimeSerieConfig(final PeriodTimeSerieOptions timeSerieOptions) {
    
    
        timeSerieOptions.setKpiID(KPI_BUILD);
        timeSerieOptions.setGroupFormula(GroupFormula.COUNT);
        timeSerieOptions.untilNow();
        timeSerieOptions.lastYears(10);
        assertTrue(timeSerieOptions.isValid());
    }
    
}
