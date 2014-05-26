package org.komea.product.model.timeserie;

import java.util.Date;
import org.apache.commons.lang3.Validate;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;
import org.komea.product.database.model.Kpi;

public class PeriodTimeSerieOptions extends TimeSerieOptions {

    /**
     *
     */
    private static final DateTimeComparator DATE_COMPARATOR_INSTANCE
            = DateTimeComparator
            .getInstance(DateTimeFieldType
                    .hourOfDay());

    private static final int MAX_DAYS_FOR_HOURTIMESCALE = 2;

    private static final int MAX_MONTH_FOR_DAYTIMESCALE = 1;

    private static final int MAX_MONTH_FOR_WEEK_TIMESCALE = 6;

    private static final int MAX_YEARS_FOR_MONTH_TIMESCALE = 2;

    private Date fromPeriod;

    private Date toPeriod;

    public PeriodTimeSerieOptions() {

        super();
    }

    public PeriodTimeSerieOptions(final Kpi _kpi) {

        super(_kpi);
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof PeriodTimeSerieOptions)) {
            return false;
        }
        final PeriodTimeSerieOptions other = (PeriodTimeSerieOptions) obj;
        if (fromPeriod == null) {
            if (other.fromPeriod != null) {
                return false;
            }
        } else if (DATE_COMPARATOR_INSTANCE.compare(fromPeriod, other.fromPeriod) != 0) {
            return false;
        }
        if (toPeriod == null) {
            if (other.toPeriod != null) {
                return false;
            }
        } else if (DATE_COMPARATOR_INSTANCE.compare(toPeriod, other.toPeriod) != 0) {
            return false;
        }
        return true;
    }

    /**
     * Get the date corresponding to now minus nb timescale.
     *
     * @param _timeScale
     * @param nb
     */
    private Date getDate(final TimeScale _timeScale, int nb) {
        switch (_timeScale) {
            case PER_DAY:
                return new DateTime().minusDays(nb).toDate();
            case PER_HOUR:
                return new DateTime().minusHours(nb).toDate();
            case PER_MONTH:
                return new DateTime().minusMonths(nb).toDate();
            case PER_WEEK:
                return new DateTime().minusWeeks(nb).toDate();
            case PER_YEAR:
                return new DateTime().minusYears(nb).toDate();
            default:
                throw new UnsupportedOperationException("Enum not known " + timeScale);

        }

    }

    /**
     * Defines the from period from the nb last timeScale.
     *
     * @param _timeScale
     * @param _nb
     */
    public void fromNbLastTimeScale(final TimeScale _timeScale, int _nb) {

        timeScale = _timeScale;
        fromPeriod = getDate(_timeScale, _nb);
    }

    public void fromLastTimeScale(final TimeScale _timeScale) {
        fromNbLastTimeScale(_timeScale, 1);
    }

    public void toNbLastTimeScale(final TimeScale _timeScale, int _nb) {
        toPeriod = getDate(_timeScale, _nb);
    }

    public void toLastTimeScale(final TimeScale _timeScale) {
        toNbLastTimeScale(_timeScale, 1);
    }

    public Date getFromPeriod() {

        return fromPeriod;
    }

    public Date getToPeriod() {

        return toPeriod;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (fromPeriod == null ? 0 : fromPeriod.hashCode());
        result = prime * result + (toPeriod == null ? 0 : toPeriod.hashCode());
        return result;
    }

    /**
     * Tests if the timescale is per year.
     */
    @JsonIgnore
    public boolean isPerYear() {

        return timeScale == TimeScale.PER_YEAR;
    }

    /**
     * Tests if the configuration is valid.
     */
    @JsonIgnore
    @Override
    public boolean isValid() {

        return super.isValid()
                && fromPeriod != null && toPeriod != null && fromPeriod.before(toPeriod);
    }

    public void lastYears(final int _numberOfYears) {

        fromPeriod = new DateTime().minusYears(_numberOfYears).toDate();

    }

    /**
     * Pick best granularity from dates
     */
    public void pickBestGranularity() {

        Validate.notNull(fromPeriod);
        Validate.notNull(toPeriod);
        final DateTime startInstant = new DateTime(fromPeriod);
        final DateTime endInstant = new DateTime(toPeriod);

        if (Days.daysBetween(startInstant, endInstant).getDays() <= MAX_DAYS_FOR_HOURTIMESCALE) {
            timeScale = TimeScale.PER_HOUR;
        } else if (Months.monthsBetween(startInstant, endInstant).getMonths() <= MAX_MONTH_FOR_DAYTIMESCALE) {
            timeScale = TimeScale.PER_DAY;
        } else if (Months.monthsBetween(startInstant, endInstant).getMonths() <= MAX_MONTH_FOR_WEEK_TIMESCALE) {
            timeScale = TimeScale.PER_WEEK;
        } else if (Years.yearsBetween(startInstant, endInstant).getYears() <= MAX_YEARS_FOR_MONTH_TIMESCALE) {
            timeScale = TimeScale.PER_MONTH;
        } else {
            timeScale = TimeScale.PER_MONTH;
        }

    }

    public void setFromPeriod(final DateTime _fromPeriod) {

        fromPeriod = _fromPeriod.toDate();
    }

    public void setToPeriod(final DateTime _toPeriod) {

        toPeriod = _toPeriod.toDate();
    }

    @Override
    public String toString() {

        return "PeriodTimeSerieOptions [fromPeriod="
                + fromPeriod + ", toPeriod=" + toPeriod + ", toString()=" + super.toString()
                + ", getClass()=" + getClass() + "]";
    }

    /**
     * Sets the toPeriod to now.
     */
    public void untilNow() {

        toPeriod = new DateTime().toDate();

    }
}
