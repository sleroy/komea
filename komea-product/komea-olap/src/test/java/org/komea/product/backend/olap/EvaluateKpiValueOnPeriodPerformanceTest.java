
package org.komea.product.backend.olap;



import org.junit.Test;
import org.komea.product.database.dao.timeserie.PeriodTimeSerieOptions;
import org.komea.product.database.dao.timeserie.TimeScale;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;
import org.springframework.transaction.annotation.Transactional;



public class EvaluateKpiValueOnPeriodPerformanceTest extends AbstractPerformanceTest
{
    
    
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
                statisticsAPI.evaluateKpiValueOnPeriod(timeSerieOptions,
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
                statisticsAPI.evaluateKpiValueOnPeriod(timeSerieOptions,
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
                statisticsAPI.evaluateKpiValueOnPeriod(timeSerieOptions,
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
                statisticsAPI.evaluateKpiValueOnPeriod(timeSerieOptions,
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
                statisticsAPI.evaluateKpiValueOnPeriod(timeSerieOptions,
                        EntityKey.of(EntityType.PROJECT, 1));
                
                
            }
        });
        
    }
    
    
}
