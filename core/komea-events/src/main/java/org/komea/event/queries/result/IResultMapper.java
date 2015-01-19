/**
 *
 */
package org.komea.event.queries.result;

import java.util.List;

import org.komea.event.queries.rows.IRow;

/**
 * @author sleroy
 */
public interface IResultMapper<TEvent> {
	
	/**
	 * Initialises the result mapper.
	 */
	void begin();
	
	/**
	 * Returns the result of the mapper/
	 *
	 * @return a new row or null.
	 */
	List<? extends IRow> end();
	
	/**
	 * Converts the event into the expected result.
	 *
	 * @return the result or null if does not provide results
	 */
	List<? extends IRow> process(TEvent _event);
	
}
