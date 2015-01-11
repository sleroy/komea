/**
 *
 */
package org.komea.event.queries.column.impl;

import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.column.IColumn;

/**
 * This class defines a column producing a sum
 *
 * @author sleroy
 */
public class CountColumn implements IColumn<FlatEvent, Integer> {
	
	private int	sum	= 0;
	
	/**
	 * Defines the column where the sum will be computed
	 */
	public CountColumn() {
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.IColumn#begin()
	 */
	@Override
	public void begin() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.IColumn#end()
	 */
	@Override
	public Integer end() {
		return sum;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.IColumn#process(java.lang.Object)
	 */
	@Override
	public void process(final FlatEvent _event) {
		sum++;
	}
	
}
