/**
 *
 */
package org.komea.event.generator;

import org.joda.time.DateTime;
import org.komea.event.model.beans.FlatEvent;

/**
 * @author sleroy
 */
public interface IEventDefinition {
	
	/**
	 * Generates an event.
	 * 
	 * @param _date
	 * @param _nthEventOfDay
	 * @param _flatEvent
	 */
	void decorateEvent(DateTime _date, int _nthEventOfDay, FlatEvent _flatEvent);

}
