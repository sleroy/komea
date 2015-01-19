/**
 *
 */
package org.komea.event.utils.date.impl;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;
import org.komea.event.generator.KpiRange;

/**
 * @author sleroy
 *
 */
public class IntervalUtilsTest {

	/**
	 * Test method for
	 * {@link org.komea.event.utils.date.impl.IntervalUtils#minus(org.joda.time.DateTime, org.komea.event.generator.KpiRange)}
	 * .
	 */
	@Test
	public void testMinus() throws Exception {
		final DateTime dateTime = new DateTime();
		final DateTime minus = IntervalUtils.minus(dateTime, KpiRange.HOUR);
		assertEquals(minus, dateTime.minusHours(1));
		//
		final DateTime minus2 = IntervalUtils.minus(dateTime, KpiRange.DAY);
		assertEquals(minus2, dateTime.minusDays(1));
		//
		final DateTime minus3 = IntervalUtils.minus(dateTime, KpiRange.MINUTE);
		assertEquals(minus3, dateTime.minusMinutes(1));
		//
		final DateTime minus4 = IntervalUtils.minus(dateTime, KpiRange.MONTH);
		assertEquals(minus4, dateTime.minusMonths(1));
		//
		final DateTime minus5 = IntervalUtils.minus(dateTime, KpiRange.SECOND);
		assertEquals(minus5, dateTime.minusSeconds(1));

		//
		final DateTime minus6 = IntervalUtils.minus(dateTime, KpiRange.WEEK);
		assertEquals(minus6, dateTime.minusWeeks(1));
		//
		final DateTime minus7 = IntervalUtils.minus(dateTime, KpiRange.YEAR);
		assertEquals(minus7, dateTime.minusYears(1));

	}

	/**
	 * Test method for
	 * {@link org.komea.event.utils.date.impl.IntervalUtils#plus(org.joda.time.DateTime, org.komea.event.generator.KpiRange)}
	 * .
	 */
	@Test
	public void testPlus() throws Exception {
		final DateTime dateTime = new DateTime();
		final DateTime plus = IntervalUtils.plus(dateTime, KpiRange.HOUR);
		assertEquals(plus, dateTime.plusHours(1));
		//
		final DateTime plus2 = IntervalUtils.plus(dateTime, KpiRange.DAY);
		assertEquals(plus2, dateTime.plusDays(1));
		//
		final DateTime plus3 = IntervalUtils.plus(dateTime, KpiRange.MINUTE);
		assertEquals(plus3, dateTime.plusMinutes(1));
		//
		final DateTime plus4 = IntervalUtils.plus(dateTime, KpiRange.MONTH);
		assertEquals(plus4, dateTime.plusMonths(1));
		//
		final DateTime plus5 = IntervalUtils.plus(dateTime, KpiRange.SECOND);
		assertEquals(plus5, dateTime.plusSeconds(1));

		//
		final DateTime plus6 = IntervalUtils.plus(dateTime, KpiRange.WEEK);
		assertEquals(plus6, dateTime.plusWeeks(1));
		//
		final DateTime plus7 = IntervalUtils.plus(dateTime, KpiRange.YEAR);
		assertEquals(plus7, dateTime.plusYears(1));

		// WEBLO / WEBSPHERE / JBOSS.

	}

}
