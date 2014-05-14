package org.komea.product.database.utils;

import org.joda.time.DateTime;
import org.komea.product.database.model.Measure;

public abstract class MeasureUtils {

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
