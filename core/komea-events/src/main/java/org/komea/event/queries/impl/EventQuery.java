/**
 *
 */
package org.komea.event.queries.impl;

import groovy.lang.Closure;

import org.joda.time.DateTime;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.ResultMapper;
import org.komea.event.storage.DateInterval;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * This class defines the event query.
 *
 * @author sleroy
 */
public class EventQuery {
	private String[]	            fromEventTypes	= new String[0];
	private Predicate<FlatEvent>	whereClosure	= Predicates.alwaysTrue();
	private ResultMapper<FlatEvent>	resultMapper	= null;
	private int	                    recordsNumber	= -1;

	private DateInterval	        period	       = null;

	/**
	 * Defines the period to analyze events.
	 *
	 * @param _to
	 *            the date
	 */
	public void forPeriod(final DateTime _from, final DateTime _to) {
		period = new DateInterval(_from, _to);

	}

	/**
	 * Defines the event types to filter.
	 *
	 * @param _eventTypes
	 *            the event types
	 */
	public void eventTypes(final String... _eventTypes) {
		fromEventTypes = _eventTypes;

	}

	public String[] getFromEventTypes() {
		return fromEventTypes;
	}

	public int getRecordsNumber() {
		return recordsNumber;
	}

	public ResultMapper<FlatEvent> getResultMapper() {
		return resultMapper;
	}

	public Predicate<FlatEvent> getWhereClosure() {
		return whereClosure;
	}

	/**
	 * Limit the number of records
	 *
	 * @param _recordsNumber
	 */
	public void limit(final int _recordsNumber) {
		recordsNumber = _recordsNumber;

	}

	/**
	 * Indicate that the filter returns "*".
	 */
	public void returnsEvents() {
		resultMapper = new EventResultMapper();
	}

	/**
	 * @param _resultMapper
	 */
	public void returnsResultMapper(
			final ResultMapper<FlatEvent> _resultMapper) {
		resultMapper = _resultMapper;

	}

	public void setFromEventTypes(final String[] _fromEventTypes) {
		fromEventTypes = _fromEventTypes;
	}

	/**
	 * Defines the period to analyze events.
	 *
	 * @param _dateTime
	 *            the date
	 */
	public void since(final DateTime _dateTime) {
		period = DateInterval.since(_dateTime);

	}

	/**
	 * Defines the period to analyze events.
	 *
	 * @param _dateTime
	 *            the date
	 */
	public void until(final DateTime _dateTime) {
		period = DateInterval.until(_dateTime);

	}

	/**
	 * Where condition defined by a closure.
	 *
	 * @param _closure
	 *            the closure.
	 */
	public void where(final Closure<Boolean> _closure) {
		whereClosure = new GroovyPredicate(_closure);
	}

	/**
	 * Where condition defined by a closure.
	 *
	 * @param _closure
	 *            the closure.
	 */
	public void where(final Predicate<FlatEvent> _closure) {
		whereClosure = _closure;
	}

}
