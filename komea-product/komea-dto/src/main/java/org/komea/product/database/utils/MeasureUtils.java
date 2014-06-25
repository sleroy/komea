package org.komea.product.database.utils;

import org.joda.time.DateTime;
import org.komea.product.database.model.Measure;

public abstract class MeasureUtils {

    /**
     * Do not use joda-time directly in class Measure because of gwt
     * compatibility
     *
     * @param measure measure
     * @param _date date
     */
    public static void setMeasureDateTime(final Measure measure, final DateTime _date) {
        measure.setYear(_date.getYear());
        measure.setMonth(_date.getMonthOfYear());
        measure.setWeek(_date.getWeekOfWeekyear());
        measure.setDay(_date.getDayOfMonth());
        measure.setHour(_date.getHourOfDay());
        measure.setDate(_date.toDate());
    }

    private MeasureUtils() {
    }

}
