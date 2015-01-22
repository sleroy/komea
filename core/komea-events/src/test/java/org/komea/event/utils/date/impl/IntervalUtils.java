/**
 *
 */
package org.komea.event.utils.date.impl;

import org.joda.time.DateTime;
import org.komea.event.generator.KpiRange;

/**
 * @author sleroy
 */
public class IntervalUtils {
	
	/**
	 * Decrements the interval by a timeunit.
	 *
	 * @param _iter
	 * @param _interval
	 * @return
	 */
	public static DateTime minus(final DateTime _iter, final KpiRange _interval) {
		switch (_interval) {
			case DAY:
				return _iter.minusDays(1);
			case HOUR:
				return _iter.minusHours(1);
			case MINUTE:
				return _iter.minusMinutes(1);
			case SECOND:
				return _iter.minusSeconds(1);
			case MONTH:
				return _iter.minusMonths(1);
			case WEEK:
				return _iter.minusWeeks(1);
			case YEAR:
				return _iter.minusYears(1);
			default:
				return null;
				
		}
		
	}

	/**
	 * Increments the interval by a timeunit.
	 *
	 * @param _iter
	 * @param _interval
	 * @return
	 */
	public static DateTime plus(final DateTime _iter, final KpiRange _interval) {
		switch (_interval) {
			case DAY:
				return _iter.plusDays(1);
			case HOUR:
				return _iter.plusHours(1);
			case MINUTE:
				return _iter.plusMinutes(1);
			case SECOND:
				return _iter.plusSeconds(1);
			case MONTH:
				return _iter.plusMonths(1);
			case WEEK:
				return _iter.plusWeeks(1);
			case YEAR:
				return _iter.plusYears(1);
			default:
				return null;
				
		}
		
	}
}
