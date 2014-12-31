package org.komea.event.storage.mysql.impl;

import java.io.Closeable;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.DateInterval;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.ResultIterator;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ConnectionFactory;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.LongMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Richard on 4/9/14.
 *
 * @modified to remove the key support for primary key.
 */
public abstract class MySQLStorageSupport implements Closeable {

	public static final class EmptyResultSetIterator implements
	ResultIterator<FlatEvent> {
		@Override
		public void close() {
			//
		}

		@Override
		public boolean hasNext() {

			return false;
		}

		@Override
		public FlatEvent next() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove() {
			//
		}
	}

	/**
	 * @author sleroy
	 */
	private final class MysqlResultHandlerMapper implements
	ResultSetMapper<FlatEvent> {
		@Override
		public FlatEvent map(final int _index,
				final ResultSet _r, final StatementContext _ctx)
						throws SQLException {

			return unserialize(_r.getBytes(1));
		}
	}

	private static final EmptyResultSetIterator	EMPTY_RESULT_SET_ITERATOR	= new EmptyResultSetIterator();

	protected static final Logger	            LOGGER	                  = LoggerFactory
			.getLogger(MySQLStorageSupport.class);
	private DBI	                                db;

	protected final String	                    sqlColumnType;
	protected String	                        databaseTable;
	protected String	                        insertStatementSQL;
	protected String	                        createStatementSQL;
	protected String	                        deleteSQL;
	protected String	                        countSQL;
	private String	                            loadAllSQL;
	private final String	                    eventType;

	public MySQLStorageSupport(final ConnectionFactory _dataSource,
			final String _table, final String _eventType) {
		Validate.notEmpty(_eventType);
		Validate.notNull(_dataSource, "database connection required");
		eventType = _eventType;
		db = new DBI(_dataSource);

		sqlColumnType = "BLOB";
		createSQL(_table);

	}

	@Override
	public void close() throws IOException {
		//
		db = null;
	}

	/**
	 * Count the number of elements
	 */
	public long count() {
		final Long res = 0L;
		Handle open = null;
		try {
			open = db.open();
			final Query<Long> map = open.createQuery(countSQL).map(
					LongMapper.FIRST);
			final Object obj = map.first();
			return ((Long) obj).longValue();

		} catch (final Throwable e) {
			handle("Error with count query", (Exception) e);
		} finally {
			IOUtils.closeQuietly(open);
		}
		return res.longValue();
	}

	public void createSQL(final String _table) {
		insertStatementSQL = "INSERT INTO `"
				+ _table
				+ "` (date, provider, eventType, data) VALUES (:date, :provider, :eventType, :data);";
		loadAllSQL = "SELECT data from `" + _table + "` WHERE eventType='"
				+ eventType + "';";
		countSQL = "SELECT COUNT(*) from `" + _table + "` WHERE eventType= '"
				+ eventType + "'";

		deleteSQL = "DELETE FROM `" + _table + "` WHERE eventType='"
				+ eventType + "';";

		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("The following SQL statements will be used {}",
					new String[] { "insert",
					insertStatementSQL, "LOAD", loadAllSQL, "DELETE",
					deleteSQL, "CREATE_TABLE",
					createStatementSQL, "COUNT", countSQL });
		}

	}

	public ResultIterator<FlatEvent> loadAll() {

		LOGGER.debug("LOAD ALL  ");
		Handle open = null;
		try {
			open = db.open();

			return open.createQuery(loadAllSQL)
					.map(new ResultSetMapper<FlatEvent>() {

						@Override
						public FlatEvent map(final int _index,
								final ResultSet _r, final StatementContext _ctx)
										throws SQLException {

							return unserialize(_r.getBytes(1));
						}
					}).iterator();

		} catch (final Exception e) {
			handle("Error with loadAll query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}
		return EMPTY_RESULT_SET_ITERATOR;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.komea.event.storage.IEventDB#loadOnPeriod(org.komea.event.storage
	 * .DateInterval)
	 */
	public ResultIterator<FlatEvent> loadOnPeriod(final DateInterval _period) {
		final ResultIterator<FlatEvent> iterator = new EmptyResultSetIterator();
		Handle open = null;
		try {
			open = db.open();

			if (_period.isCompleteInterval()) {
				final String query = "SELECT * FROM " + eventType
						+ " WHERE date BETWEEN :from AND :to";
				final Query<FlatEvent> map = open.createQuery(query)
						.map(new MysqlResultHandlerMapper())
						.bind("from", _period.getFrom())
						.bind("to", _period.getTo());
				return map.iterator();

			} else if (_period.hasFrom()) {
				final String query = "SELECT * FROM " + eventType
						+ " WHERE date > :from";
				final Query<FlatEvent> map = open.createQuery(query)
						.map(new MysqlResultHandlerMapper())
						.bind("from", _period.getFrom());
				return map.iterator();
			} else if (_period.hasTo()) {
				final String query = "SELECT * FROM " + eventType
						+ " WHERE date < :to";
				final Query<FlatEvent> map = open.createQuery(query)
						.map(new MysqlResultHandlerMapper())
						.bind("to", _period.getTo());
				return map.iterator();
			}

		} catch (final Exception e) {
			handle("Error with removeAll query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}

		return iterator;

	}

	public void put(final FlatEvent _entry) {

		LOGGER.debug("PUT VALUE {}", _entry);

		Handle open = null;
		try {
			open = db.open();

			// date, provider, eventType, data
			open.createStatement(insertStatementSQL)
			.bind("date", _entry.getDate())
			.bind("provider", _entry.getProvider())
			.bind("eventType", _entry.getEventType())
			.bind("data", serialize(_entry)).execute();
		} catch (final Exception e) {
			handle("Error with put query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}

	}

	public void putAll(final Collection<FlatEvent> values) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("PUT ALL {}", values);
		}

		Handle open = null;
		try {
			open = db.open();

			final Collection<FlatEvent> entries = values;
			open.begin();// WRAPS A TRANSACTION IF NOT ALREADY CREATED
			for (final FlatEvent entry : entries) {
				put(entry);

			}
			open.commit();
		} catch (final Exception e) {
			handle("Error with putAll query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}
	}

	/**
	 * Removes all the elements of a given type.
	 *
	 * @param _eventType
	 * @throws SQLException
	 */
	public void removeAll() {
		Handle open = null;
		try {
			open = db.open();

			open.execute(deleteSQL);
		} catch (final Exception e) {
			handle("Error with removeAll query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}

	}

	public abstract byte[] serialize(FlatEvent _entry);

	public abstract FlatEvent unserialize(byte[] _entry);

	private void handle(final String _message, final Exception _e) {
		LOGGER.error(_message, _e);

	}
}
