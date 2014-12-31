/**
 *
 */
package org.komea.event.queries.executor;

import java.util.List;

import org.komea.event.queries.rows.IRow;

import com.google.common.collect.Lists;

/**
 * @author sleroy
 */
public class QueryResult {

	private final List<IRow>	rows	= Lists.newArrayList();

	/**
	 * Adds the row
	 *
	 * @param _end
	 *            the end
	 */
	public void addRow(final IRow _end) {
		rows.add(_end);

	}

	/**
	 * Returns the number of rows returned by the query.
	 *
	 * @return the number of rows.
	 */
	public int countRows() {

		return rows.size();
	}

	/**
	 * Returns the first row or null
	 *
	 * @return the first row.
	 */
	public IRow firstRow() {
		if (rows.isEmpty()) { return null; }
		return rows.get(0);
	}

	/**
	 * Returns the first value
	 *
	 * @return
	 */
	public <T> T firstValue() {
		if (rows.isEmpty()) { return null; }
		return rows.get(0).firstValue();
	}

	public boolean isEmpty() {
		return rows.isEmpty();
	}
}
