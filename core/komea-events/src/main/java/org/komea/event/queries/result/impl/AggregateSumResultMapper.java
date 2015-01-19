/**
 *
 */
package org.komea.event.queries.result.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.result.IResultMapper;
import org.komea.event.queries.rows.IRow;
import org.komea.event.queries.rows.impl.ListRow;

/**
 * Aggregate and produces as result a map for each field value aggregated, the
 * sum of another field value;
 *
 * @author sleroy
 */
public class AggregateSumResultMapper implements IResultMapper<FlatEvent> {

	private final String	              fieldAggregation;
	private final String	              fieldValue;

	private final Map<Object, AtomicLong>	aggregation	= new ConcurrentHashMap<Object, AtomicLong>();

	/**
	 * Defines the field to perform the aggregation.
	 *
	 * @param _fieldAggregation
	 *            the aggregation fields
	 * @param _fieldValue
	 *            the field containing the value;
	 */
	public AggregateSumResultMapper(final String _fieldAggregation,
			final String _fieldValue) {
		fieldAggregation = _fieldAggregation;
		fieldValue = _fieldValue;
	}

	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.column.IColumn#begin()
	 */
	@Override
	public void begin() {
		//

	}

	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.column.IColumn#end()
	 */
	@Override
	public List<? extends IRow> end() {
		final List<IRow> rows = new ArrayList<>(aggregation.size());
		for (final Entry<Object, AtomicLong> resultSet : aggregation.entrySet()) {
			final ListRow<Object> row = new ListRow<Object>(fieldAggregation,
					fieldValue);
			row.add(resultSet.getKey());
			row.add(resultSet.getValue().get());
			rows.add(row);
		}
		return rows;
	}

	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.column.IColumn#process(java.lang.Object)
	 */
	@Override
	public List<? extends IRow> process(final FlatEvent _event) {
		final Object field = _event.field(fieldAggregation);
		AtomicLong atomicLong = aggregation.get(field);
		if (atomicLong == null) {
			atomicLong = new AtomicLong();
			aggregation.put(field, atomicLong);
		}
		final Number value = _event.field(fieldValue, Number.class);
		if (value != null) {
			atomicLong.addAndGet(value.longValue());
		}
		return null;
	}

}
