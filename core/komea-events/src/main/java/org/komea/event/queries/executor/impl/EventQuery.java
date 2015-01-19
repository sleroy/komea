/**
 *
 */
package org.komea.event.queries.executor.impl;

import groovy.lang.Closure;

import org.joda.time.DateTime;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.predicate.impl.GroovyPredicate;
import org.komea.event.queries.result.IResultMapper;
import org.komea.event.queries.result.impl.EventResultMapper;
import org.komea.event.storage.DateInterval;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * This class defines the event query.
 *
 * @author sleroy
 */
public class EventQuery {
	private String[]	             fromEventTypes	  = new String[0];
	private Predicate<FlatEvent>	 whereClosure	  = Predicates.alwaysTrue();
	private IResultMapper<FlatEvent>	iResultMapper	= null;
	private int	                     recordsNumber	  = -1;
	
	private DateInterval	         period	          = null;
	private String[]	             groupBy;
	
	/**
	 * Defines the event types to filter.
	 *
	 * @param _eventTypes
	 *            the event types
	 */
	public void eventTypes(final String... _eventTypes) {
		fromEventTypes = _eventTypes;
		
	}
	
	/**
	 * Defines the period to analyze events.
	 *
	 * @param _to
	 *            the date
	 */
	public void forPeriod(final DateTime _from, final DateTime _to) {
		period = new DateInterval(_from, _to);
		
	}
	
	public String[] getFromEventTypes() {
		return fromEventTypes;
	}
	
	public String[] getGroupBy() {
		return groupBy;
	}
	
	public DateInterval getPeriod() {
		return period;
	}
	
	public int getRecordsNumber() {
		return recordsNumber;
	}
	
	public IResultMapper<FlatEvent> getResultMapper() {
		return iResultMapper;
	}
	
	public Predicate<FlatEvent> getWhereClosure() {
		return whereClosure;
	}
	
	/**
	 * Group by the field names
	 *
	 * @param _fieldNames
	 *            the field names
	 */
	public void groupBy(final String... _fieldNames) {
		groupBy = _fieldNames;
		
	}
	
	public boolean hasInterval() {
		return period != null;
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
		iResultMapper = new EventResultMapper();
	}
	
	/**
	 * @param _resultMapper
	 */
	public void returnsResultMapper(final IResultMapper<FlatEvent> _resultMapper) {
		iResultMapper = _resultMapper;
		
	}
	
	public void setFromEventTypes(final String[] _fromEventTypes) {
		fromEventTypes = _fromEventTypes;
	}
	
	public void setPeriod(final DateInterval _period) {
		period = _period;
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
