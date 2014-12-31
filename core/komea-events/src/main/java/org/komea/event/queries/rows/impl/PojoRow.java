/**
 *
 */
package org.komea.event.queries.rows.impl;

import java.io.Serializable;
import java.util.Map;

import org.komea.core.utils.PojoToMap;
import org.komea.event.queries.rows.IRow;

/**
 * Returns a pojo as a row.
 *
 * @author sleroy
 */
public class PojoRow implements IRow {
	
	private final Object	          underlyingPojo;
	private Map<String, Serializable>	pojoMap;
	
	public PojoRow(final Object _pojo) {
		underlyingPojo = _pojo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#field(java.lang.String)
	 */
	@Override
	public <T> T field(final String _fieldName) {
		initIfNeeded();
		return (T) pojoMap.get(_fieldName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#field(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> T field(final String _fieldName, final Class<T> _class) {
		
		return _class.cast(this.field(_fieldName));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#fieldCount()
	 */
	@Override
	public int fieldCount() {
		initIfNeeded();
		return pojoMap.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#firstValue()
	 */
	@Override
	public <T> T firstValue() {
		
		throw new UnsupportedOperationException();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#getUnderlyingObject()
	 */
	@Override
	public Object getUnderlyingObject() {
		
		return underlyingPojo;
	}
	
	private void initIfNeeded() {
		if (pojoMap == null) {
			pojoMap = new PojoToMap().convertPojoInMap(underlyingPojo);
		}
	}
	
}
