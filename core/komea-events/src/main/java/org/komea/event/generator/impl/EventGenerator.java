/**
 *
 */
package org.komea.event.generator.impl;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.komea.event.generator.IEventArrayDefinition;
import org.komea.event.generator.IEventDefinition;
import org.komea.event.generator.KpiRange;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sleroy
 */
public class EventGenerator {
	
	private final IEventDefinition	definition;
	
	private static final Logger	   LOGGER	= LoggerFactory
	                                              .getLogger(EventGenerator.class);
	
	private final IEventStorage	   storage;
	
	public EventGenerator(final IEventStorage _storage,
	        final IEventArrayDefinition _definition) {
		this(_storage, new ArrayEventDefinition(_definition));
	}
	
	public EventGenerator(final IEventStorage _storage,
	        final IEventDefinition _definition) {
		storage = _storage;
		definition = _definition;
		
	}
	
	/**
	 * Generates a list of events
	 *
	 * @param _numberOfEventsPerDay
	 * @param _interval
	 * @param _numberOfIntervals
	 * @return
	 */
	public int generate(final int _numberOfEventsPerDay,
	        final KpiRange _interval,
	        final Interval _period) {
		DateTime iter = _period.getStart();
		int res = 0;
		while (iter.isBefore(_period.getEnd())) {
			for (int j = 0; j < _numberOfEventsPerDay; ++j) {
				final FlatEvent flatEvent = new FlatEvent();
				flatEvent.setDateTime(iter);
				definition.decorateEvent(iter, j,
				        flatEvent);
				storage.storeFlatEvent(flatEvent);
				++res;
			}
			
			iter = plus(iter, _interval);
		}
		return res;
	}
	
	/**
	 * Decreases the interval by a timeunit.
	 *
	 * @param _iter
	 * @param _interval
	 * @return
	 */
	private DateTime plus(final DateTime _iter, final KpiRange _interval) {
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
