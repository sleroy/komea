package org.komea.event.storage.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.skife.jdbi.v2.util.TypedMapper;

/**
 * Convenience ResultSetMapper for extracting a single value result from a
 * query.
 */
public class DateMapper extends TypedMapper<Date> {
	/**
	 * An instance which extracts value from the first field
	 */
	public static final DateMapper FIRST = new DateMapper(1);

	/**
	 * Create a new instance which extracts the value from the first column
	 */
	public DateMapper() {
		super();
	}

	/**
	 * Create a new instance which extracts the value positionally in the result
	 * set
	 *
	 * @param index
	 *            1 based column index into the result set
	 */
	public DateMapper(final int index) {
		super(index);
	}

	/**
	 * Create a new instance which extracts the value by name or alias from the
	 * result set
	 *
	 * @param name
	 *            The name or alias for the field
	 */
	public DateMapper(final String name) {
		super(name);
	}

	@Override
	protected Date extractByIndex(final ResultSet r, final int index)
			throws SQLException {
		return r.getTimestamp(index);
	}

	@Override
	protected Date extractByName(final ResultSet r, final String name)
			throws SQLException {
		return r.getTimestamp(name);
	}
}
