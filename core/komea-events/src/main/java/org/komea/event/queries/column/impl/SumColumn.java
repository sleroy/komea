/**
 *
 */
package org.komea.event.queries.column.impl;

import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.column.IColumn;

/**
 * This class defines a column producing a sum
 *
 * @author sleroy
 */
public class SumColumn implements IColumn<FlatEvent, Double> {
	
	private Sum	         sum;
	private final String	fieldName;
	
	/**
	 * Defines the column where the sum will be computed
	 */
	public SumColumn(final String _fieldName) {
		fieldName = _fieldName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.IColumn#begin()
	 */
	@Override
	public void begin() {
		sum = new Sum();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.IColumn#end()
	 */
	@Override
	public Double end() {
		return sum.evaluate();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.IColumn#process(java.lang.Object)
	 */
	@Override
	public void process(final FlatEvent _event) {
		final Number field = _event.field(fieldName, Number.class);
		if (field != null) {
			sum.increment(field.doubleValue());
		}
	}
	
}
