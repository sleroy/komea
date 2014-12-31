/**
 *
 */
package org.komea.event.queries;

/**
 * @author sleroy
 *
 */
public class Sort {

	private final String	    field;
	private final SortDirection	order;

	/**
	 * @param _field
	 * @param _order
	 */
	public Sort(String _field, SortDirection _order) {
		field = _field;
		order = _order;

	}

}
