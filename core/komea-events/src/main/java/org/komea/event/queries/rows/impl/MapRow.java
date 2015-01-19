/**
 *
 */
package org.komea.event.queries.rows.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.komea.event.queries.rows.IRow;

/**
 * This class defines rows
 *
 * @author sleroy
 */
public class MapRow implements IRow {
	private final Map<String, ?>	map;
	
	public MapRow(final Map<String, ?> _map) {
		super();
		map = Collections.unmodifiableMap(_map);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#field(java.lang.String)
	 */
	@Override
	public <T> T field(final String _fieldName) {
		return (T) map.get(_fieldName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#field(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> T field(final String _fieldName, final Class<T> _class) {
		
		return _class.cast(map.get(_fieldName));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#fieldCount()
	 */
	@Override
	public int fieldCount() {
		return map.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#firstValue()
	 */
	@Override
	public <T> T firstValue() {
		final Iterator<?> iterator = map.values().iterator();
		if (iterator.hasNext()) { return (T) iterator.next(); }
		return null;
	}
	
	@Override
	public String toString() {
		return "MapRow [map=" + map + "]";
	}
}
