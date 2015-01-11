package org.komea.event.queries.rows.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.komea.event.queries.rows.IRow;

/**
 * A row with column names and table-style access patterns.
 *
 * @author sleroy
 */
public class ListRow<T> extends ArrayList<T> implements IRow {
	
	private final List<String>	columnNames;
	
	public ListRow(final List<String> columnNames) {
		super();
		this.columnNames = columnNames;
	}
	
	public ListRow(final List<T> row, final List<String> columnNames) {
		super(row);
		this.columnNames = columnNames;
	}
	
	public ListRow(final String... _columnNames) {
		super();
		columnNames = Arrays.asList(_columnNames);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#field(java.lang.String)
	 */
	@Override
	public <T> T field(final String _fieldName) {
		final int indexOf = columnNames.indexOf(_fieldName);
		if (indexOf == -1) { return null; }
		return (T) get(indexOf);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#field(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> T field(final String _fieldName, final Class<T> _class) {
		
		final int indexOf = columnNames.indexOf(_fieldName);
		if (indexOf == -1) { return null; }
		return _class.cast(get(indexOf));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#fieldCount()
	 */
	@Override
	public int fieldCount() {
		
		return columnNames.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.komea.event.queries.rows.IRow#firstValue()
	 */
	@Override
	public <T> T firstValue() {
		
		return (T) get(0);
	}
	
	public List<String> getColumnNames() {
		return this.columnNames;
	}
	
	@Override
	public String toString() {
		
		final StringBuilder buffer = new StringBuilder("[");
		for (int i = 0; i < size(); i++) {
			if (columnNames.size() > 0) {
				buffer.append(columnNames.get(i));
				buffer.append(":");
			}
			buffer.append(get(i));
			if (i < size() - 1) {
				buffer.append(", ");
			}
		}
		buffer.append("]");
		return buffer.toString();
		
	}
}
