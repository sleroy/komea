/**
 *
 */
package org.komea.event.generator;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * @author sleroy
 */
public interface IEventArrayDefinition {
	
	/**
	 * @param _date
	 * @param _nthEventOfDay
	 * @return
	 */
	Serializable[][] decorateEvent(DateTime _date, int _nthEventOfDay);

}
