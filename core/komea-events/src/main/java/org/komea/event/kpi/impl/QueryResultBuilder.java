/**
 *
 */
package org.komea.event.kpi.impl;

import org.komea.event.queries.executor.impl.EventQueryResult;
import org.komea.event.queries.rows.IRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sleroy
 */
public class QueryResultBuilder {

	private final EventQueryResult	execute;
	private final String	       aggregateFieldName;
	private final String	       valueFieldName;

	private static final Logger	   LOGGER	= LoggerFactory
			.getLogger(QueryResultBuilder.class);

	/**
	 * @param _execute
	 * @param _aggregateField
	 * @param _valueField
	 */
	public QueryResultBuilder(final EventQueryResult _execute,
			final String _aggregateField, final String _valueField) {
		execute = _execute;
		aggregateFieldName = _aggregateField;
		valueFieldName = _valueField;
	}

	/**
	 * Builds a query result from a list of rows.
	 *
	 * @param _execute
	 *            the result of the event query.
	 */
	public QueryResult build() {
		final QueryResult queryResult = new QueryResult("author");
		for (final IRow row : execute.getRows()) {
			final Object aggField = row.field(aggregateFieldName);
			final Number valueField = row.field(valueFieldName);
			if (aggField == null) {
				LOGGER.warn(
						"Skipped row {}, field aggregated returns null. [{}, {}]",
						row, aggField, valueField);
			} else {
				queryResult.setValue(aggField.toString(),
				        valueField.doubleValue());
			}
		}
		return queryResult;
	}
}
