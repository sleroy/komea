/**
 *
 */
package org.komea.event.kpi.impl;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * This class defines a query result.
 *
 * @author sleroy
 */
public class QueryResult {
	private String	                  entityColumn;
	
	private final Map<String, Double>	values	= Maps.newHashMap();

	/**
	 * QUery result.
	 */
	public QueryResult() {
		super();
	}

	/**
	 * "
	 *
	 * @param _entityColumn
	 */
	public QueryResult(final String _entityColumn) {
		super();
		entityColumn = _entityColumn;
	}

	public String getEntityColumn() {
		return entityColumn;
	}

	public void setEntityColumn(final String _entityColumn) {
		entityColumn = _entityColumn;
	}

	public void setValue(final String _entity, final Number _value) {
		values.put(_entity, _value.doubleValue());
	}

	public void setValues(final Map<String, Double> _values) {
		values.putAll(_values);
	}

	@Override
	public String toString() {
		return "QueryResult [entityColumn=" + entityColumn + ", values="
		        + values + "]";
	}
}
