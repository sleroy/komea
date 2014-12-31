/**
 *
 */
package org.komea.event.queries.column;

/**
 * This interface defines a column
 *
 * @author sleroy
 */
public interface IColumn<TEvent, TRes> {
	/**
	 * Triggered when the query is evaluated.
	 */
	public void begin();

	/**
	 * Triggered when the query has been evaluated.
	 */
	public TRes end();

	/**
	 * Triggered for each event
	 *
	 * @param _event
	 *            the event
	 */
	public TRes process(TEvent _event);
}
