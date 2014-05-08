
package org.komea.product.backend.olap;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dao.timeserie.TimeSerieOptions;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;



public class DatabasePerformanceTest extends AbstractSpringIntegrationTestCase
{
    
    
    private final static int    KPI_BUILD          = 1;
    
    private static final Logger LOGGER             =
                                                           LoggerFactory
                                                                   .getLogger(DatabasePerformanceTest.class);
    
    private static final int    MAX_BUILD_PER_HOUR = 5;
    
    
    static List<Measure>        measures           = new ArrayList<Measure>(20000);
    
    
    
    @BeforeClass
    public static void beforeClass() {
    
    
        measures.clear();
        
        generateTwoYearsOfBuildForJenkins(2);
    }
    
    
    private static Measure fakeMeasure(
            final DateTime _from,
            final int _idKpi,
            final int _idProject,
            final double _d) {
    
    
        final Measure measure = new Measure();
        measure.setDateTime(_from);
        measure.setValue(_d);
        measure.setEntityID(_idProject);
        measure.setIdKpi(_idKpi);
        return measure;
        
    }
    
    
    /**
     * Generates jenkins event / every hour
     * 
     * @param _numberOfProjects
     */
    @Transactional
    private static void generateTwoYearsOfBuildForJenkins(final int _numberOfProjects) {
    
    
        final Random random = new Random();
        DateTime from =
                new DateTime().minusYears(2).withHourOfDay(0).withMinuteOfHour(0)
                        .withSecondOfMinute(0).withMillisOfSecond(0);
        while (from.isBeforeNow()) {
            for (int idProject = 0; idProject < _numberOfProjects; ++idProject) {
                for (int hour = 0; hour < 24; ++hour) {
                    try {
                        from = from.withHourOfDay(hour);
                        if (random.nextBoolean()) { // Generate a build
                            measures.add(fakeMeasure(from, KPI_BUILD, idProject,
                                    random.nextInt(MAX_BUILD_PER_HOUR)));
                        }
                    } catch (final Exception e) {
                        //
                    }
                }
            }
            from = from.plusDays(1);
        }
        
    }
    
    
    
    @Rule
    public TestRule    benchmarkRun = new BenchmarkRule();
    
    
    @Autowired
    private MeasureDao measureDao;
    
    
    
    @Before
    public void before() {
    
    
        measureDao.deleteByCriteria(new MeasureCriteria());
        for (final Measure measure : measures) {
            measureDao.insert(measure);
        }
    }
    
    
    @Test
    @Transactional
    public void groupElementsPerYear() {
    
    
        final TimeSerieOptions timeSerieOptions = new TimeSerieOptions();
        timeSerieOptions.setKpiID(KPI_BUILD);
        final List map = measureDao.buildTimeSeries(timeSerieOptions);
        System.out.println(map.size());
        //
        
    }
    
    
    @Test
    @Transactional
    public void insertElements() {
    
    
        //
        
    }
    
    
    @Test
    @Transactional
    public void insertElementsAndCount() {
    
    
        final int countNumberOfValues = measureDao.countByCriteria(new MeasureCriteria());
        LOGGER.info("Number of values produced {}", countNumberOfValues);
        
    }
}
