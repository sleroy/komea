package org.komea.product.database.dao.timeserie;

import java.util.Date;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;

public class PeriodTimeSerieOptions extends TimeSerieOptions {

	private static final int	MAX_MONTH_FOR_WEEK_TIMESCALE	= 6;

	private static final int	MAX_MONTH_FOR_DAYTIMESCALE	  = 1;

	private static final int	MAX_DAYS_FOR_HOURTIMESCALE	  = 2;

	private static final int	MAX_YEARS_FOR_MONTH_TIMESCALE	= 2;

	private Date	         fromPeriod;

	private Date	         toPeriod;

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof PeriodTimeSerieOptions)) { return false; }
		final PeriodTimeSerieOptions other = (PeriodTimeSerieOptions) obj;
		if (fromPeriod == null) {
			if (other.fromPeriod != null) { return false; }
		} else if (!fromPeriod.equals(other.fromPeriod)) { return false; }
		if (toPeriod == null) {
			if (other.toPeriod != null) { return false; }
		} else if (!toPeriod.equals(other.toPeriod)) { return false; }
		return true;
	}

	/**
	 * Defines the from period fro mthe last time scale.
	 * 
	 * @param _timeScale
	 */
	public void fromLastTimeScale(final TimeScale _timeScale) {
		timeScale = _timeScale;
		switch (timeScale) {
			case PER_DAY:
				fromPeriod = new DateTime().minusDays(1).toDate();
				break;
			case PER_HOUR:
				fromPeriod = new DateTime().minusHours(1).toDate();
				break;
			case PER_MONTH:
				fromPeriod = new DateTime().minusMonths(1).toDate();
				break;
			case PER_WEEK:
				fromPeriod = new DateTime().minusWeeks(1).toDate();
				break;
			case PER_YEAR:
				fromPeriod = new DateTime().minusYears(1).toDate();

				break;
			default:
				throw new UnsupportedOperationException("Enum not known " + timeScale);

		}

	}

	public Date getFromPeriod() {

		return fromPeriod;
	}

	@Override
	public GroupFormula getGroupFormula() {

		return groupFormula;
	}

	@Override
	public int getKpiID() {

		return kpiID;
	}

	@Override
	public TimeScale getTimeScale() {

		return timeScale;
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

	public boolean isPerYear() {

		return timeScale == TimeScale.PER_YEAR;
	}

	/**
	 * Tests if the configuration is valid.
	 * 
	 */
	@Override
	public boolean isValid() {

		return super.isValid() && fromPeriod != null && toPeriod != null;
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

	@Override
	public void setGroupFormula(final GroupFormula _groupFormula) {

		groupFormula = _groupFormula;
	}

	@Override
	public void setKpiID(final int _kpiID) {

		kpiID = _kpiID;
	}

	@Override
	public void setTimeScale(final TimeScale _timeScale) {

		timeScale = _timeScale;
	}

	public void setToPeriod(final DateTime _toPeriod) {

		toPeriod = _toPeriod.toDate();
	}

	@Override
	public String toString() {
		return "PeriodTimeSerieOptions [fromPeriod=" + fromPeriod + ", toPeriod=" + toPeriod + ", toString()="
		        + super.toString() + ", getClass()=" + getClass() + "]";
	}

	/**
	 * Sets the toPeriod to now.
	 */
	public void untilNow() {

		toPeriod = new DateTime().toDate();

	}
}
