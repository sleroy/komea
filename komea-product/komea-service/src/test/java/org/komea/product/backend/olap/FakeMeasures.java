/**
 *
 */
package org.komea.product.backend.olap;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import org.joda.time.DateTime;
import org.komea.product.database.model.Measure;
import org.komea.product.database.utils.MeasureUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 */
public class FakeMeasures {

    /**
     * Generates jenkins event / every hour
     *
     * @param _numberOfProjects
     * @return
     */
    @Transactional
    public static List<Measure> generateDailyDataForKpi(final String _kpiID, final int _maxYears,
            final int _numberOfProjects, final int _valueMaxrange) {

        final List<Measure> measures = Lists.newArrayList();
        final Random random = new Random();
        DateTime from = programBeforeDate(_maxYears);
        while (from.isBeforeNow()) {
            for (int idProject = 0; idProject < _numberOfProjects; ++idProject) {
                if (random.nextBoolean()) { // Generate a build

                    measures.add(fakeMeasure(from, _kpiID, idProject, random.nextInt(_valueMaxrange)));
                }
            }
            from = from.plusDays(1);
        }
        return measures;
    }

    /**
     * Generates jenkins event / every hour
     *
     * @param _numberOfProjects
     * @return
     */
    @Transactional
    public static List<Measure> generateHourlyDataForKpi(final String _kpiID, final int _maxYears,
            final int _numberOfProjects, final int _valueMaxrange) {

        final List<Measure> measures = Lists.newArrayList();
        final Random random = new Random();
        DateTime from = programBeforeDate(_maxYears);
        while (from.isBeforeNow()) {
            for (int idProject = 0; idProject < _numberOfProjects; ++idProject) {
                from = generateHourlyData(_kpiID, _valueMaxrange, measures, random, from, idProject);
            }
            from = from.plusDays(1);
        }
        return measures;
    }

    private static Measure fakeMeasure(final DateTime _from, final String _idKpi, final int _idProject, final double _d) {

        final Measure measure = new Measure();
        MeasureUtils.setMeasureDateTime(measure, _from);
        measure.setValue(_d);
        measure.setEntityID(_idProject);
        measure.setIdKpi(_idKpi);
        return measure;

    }

    private static DateTime generateHourlyData(final String _kpiID, final int _valueMaxrange,
            final List<Measure> measures, final Random random, final DateTime from, final int idProject) {

        DateTime from2 = from;
        for (int hour = 0; hour < 24; ++hour) {
            try {
                from2 = from.withHourOfDay(hour);
                if (random.nextBoolean()) { // Generate a build

                    measures.add(fakeMeasure(from, _kpiID, idProject, random.nextInt(_valueMaxrange)));
                }
            } catch (final Exception e) {
                //
            }
        }
        return from2;
    }

    private static DateTime programBeforeDate(final int _maxYears) {

        final DateTime from = new DateTime().minusYears(_maxYears).withHourOfDay(0).withMinuteOfHour(0)
                .withSecondOfMinute(0).withMillisOfSecond(0);
        return from;
    }

}
