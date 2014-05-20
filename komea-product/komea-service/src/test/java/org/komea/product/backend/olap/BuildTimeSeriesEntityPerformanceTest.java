package org.komea.product.backend.olap;

import org.junit.Test;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.service.dto.EntityKey;
import org.springframework.transaction.annotation.Transactional;

public class BuildTimeSeriesEntityPerformanceTest extends AbstractPerformanceTest {

    @Test(timeout = 52 * MILLI_INTERVAL)
    @Transactional
    public void groupElementsPerDay() {

        final Kpi kpi = beforeInitialization();
        final BasicMicroBenchmark basicMicroBenchmark = new BasicMicroBenchmark();
        basicMicroBenchmark.setTestName("groupDay");
        basicMicroBenchmark.run(new Runnable() {

            @Override
            public void run() {

                final TimeSerieOptions timeSerieOptions = new TimeSerieOptions();
                timeSerieOptions.setTimeScale(TimeScale.PER_DAY);
                sameTimeSerieTimeConfig(timeSerieOptions, kpi);
                statisticsAPI.buildTimeSeries(timeSerieOptions, EntityKey.of(EntityType.PROJECT, 1));
            }

        });

    }

    @Test(timeout = 46 * MILLI_INTERVAL)
    @Transactional
    public void groupElementsPerHour() {

        final Kpi kpi = beforeInitialization();
        final BasicMicroBenchmark basicMicroBenchmark = new BasicMicroBenchmark();
        basicMicroBenchmark.setTestName("groupHour");
        basicMicroBenchmark.run(new Runnable() {

            @Override
            public void run() {

                final TimeSerieOptions timeSerieOptions = new TimeSerieOptions();
                timeSerieOptions.setTimeScale(TimeScale.PER_HOUR);
                sameTimeSerieTimeConfig(timeSerieOptions, kpi);
                statisticsAPI.buildTimeSeries(timeSerieOptions, EntityKey.of(EntityType.PROJECT, 1));
            }
        });

    }

    @Test(timeout = 100 * MILLI_INTERVAL)
    @Transactional
    public void groupElementsPerMonth() {

        final Kpi kpi = beforeInitialization();
        final BasicMicroBenchmark basicMicroBenchmark = new BasicMicroBenchmark();
        basicMicroBenchmark.setTestName("groupMonth");
        basicMicroBenchmark.run(new Runnable() {

            @Override
            public void run() {

                final TimeSerieOptions timeSerieOptions = new TimeSerieOptions();
                timeSerieOptions.setTimeScale(TimeScale.PER_MONTH);
                sameTimeSerieTimeConfig(timeSerieOptions, kpi);
                statisticsAPI.buildTimeSeries(timeSerieOptions, EntityKey.of(EntityType.PROJECT, 1));
            }
        });

    }

    @Test(timeout = 30 * MILLI_INTERVAL)
    @Transactional
    public void groupElementsPerWeek() {

        final Kpi kpi = beforeInitialization();
        final BasicMicroBenchmark basicMicroBenchmark = new BasicMicroBenchmark();
        basicMicroBenchmark.setTestName("groupWeek");
        basicMicroBenchmark.run(new Runnable() {

            @Override
            public void run() {

                final TimeSerieOptions timeSerieOptions = new TimeSerieOptions();
                timeSerieOptions.setTimeScale(TimeScale.PER_WEEK);
                sameTimeSerieTimeConfig(timeSerieOptions, kpi);
                statisticsAPI.buildTimeSeries(timeSerieOptions, EntityKey.of(EntityType.PROJECT, 1));

            }
        });
        //

    }

    @Test(timeout = 32 * MILLI_INTERVAL)
    @Transactional
    public void groupElementsPerYear() {

        final Kpi kpi = beforeInitialization();
        final BasicMicroBenchmark basicMicroBenchmark = new BasicMicroBenchmark();
        basicMicroBenchmark.setTestName("groupYears");
        basicMicroBenchmark.run(new Runnable() {

            @Override
            public void run() {

                final TimeSerieOptions timeSerieOptions = new TimeSerieOptions();
                timeSerieOptions.setTimeScale(TimeScale.PER_YEAR);
                sameTimeSerieTimeConfig(timeSerieOptions, kpi);
                statisticsAPI.buildTimeSeries(timeSerieOptions, EntityKey.of(EntityType.PROJECT, 1));

            }
        });

    }

}
