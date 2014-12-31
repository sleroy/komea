/**
 *
 */
package org.komea.event.queries;

import org.komea.event.queries.rows.IRow;

/**
 * @author sleroy
 */
public interface ResultMapper<TEvent> {

	/**
	 * Initialises the result mapper.
	 */
	void begin();

	/**
	 * Returns the result of the mapper/
	 *
	 * @return a new row or null.
	 */
	IRow end();

	/**
	 * Converts the event into the expected result.
	 *
	 * @return the result or null if does not provide results
	 */
	IRow process(TEvent _event);

}
