/**
 *
 */
package org.komea.event.queries.rows;

/**
 * @author sleroy
 */
public interface IRow {

	public <T> T field(final String _fieldName);

	/**
	 * Returns the field value or null if does not exist;
	 *
	 * @param _fieldName
	 * @param _class
	 * @return
	 */
	public <T> T field(String _fieldName, Class<T> _class);

	public int fieldCount();

	/**
	 * Returns the first column value.
	 *
	 * @return the first value.
	 */
	public <T> T firstValue();

	public Object getUnderlyingObject();

}