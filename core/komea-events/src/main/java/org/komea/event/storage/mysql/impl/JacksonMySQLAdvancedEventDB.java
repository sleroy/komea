package org.komea.event.storage.mysql.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.DateInterval;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.impl.EmptyResultIterator;
import org.komea.event.utils.jackson.impl.JacksonByteArrayToObjectConverter;
import org.komea.event.utils.jackson.impl.JacksonObjectToByteArrayConverter;
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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Richard on 4/9/14.
 *
 * @modified to remove the key support for primary key.
 */
public class JacksonMySQLAdvancedEventDB implements IEventDB {

	/**
	 * @author sleroy
	 */
	private final class MysqlResultHandlerMapper implements
			ResultSetMapper<FlatEvent> {

		@Override
		public FlatEvent map(final int _index, final ResultSet _r,
				final StatementContext _ctx) throws SQLException {

			return unserialize(_r.getBytes("data"));
		}
	}

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(JacksonMySQLAdvancedEventDB.class);

	private DBI db;

	protected final String sqlColumnType;
	protected String databaseTable;
	protected String insertStatementSQL;
	protected String createStatementSQL;
	protected String deleteSQL;
	protected String countSQL;
	private String loadAllSQL;
	private final String eventType;
	private final JacksonByteArrayToObjectConverter<FlatEvent> unserializer;

	private final JacksonObjectToByteArrayConverter<FlatEvent> serializer;
	private final String table;
	private final String sourceSchema;

	public JacksonMySQLAdvancedEventDB(final ConnectionFactory _dataSource,
			final String _eventType, final String _sourceSchema) {
		sourceSchema = _sourceSchema;
		Validate.notEmpty(_eventType);
		Validate.notNull(_dataSource, "database connection required");
		eventType = _eventType;
		db = new DBI(_dataSource);
		table = createTable(eventType);
		sqlColumnType = "BLOB";
		createSQL(table);
		final ObjectMapper jackson = initJackson();
		unserializer = new JacksonByteArrayToObjectConverter<FlatEvent>(
				jackson, FlatEvent.class);
		serializer = new JacksonObjectToByteArrayConverter<FlatEvent>(jackson);
	}

	@Override
	public void close() throws IOException {
		//
		db = null;
	}

	/**
	 * Count the number of elements
	 */
	@Override
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
		insertStatementSQL = "INSERT INTO `" + _table
				+ "` (date, provider, data) VALUES (:date, :provider, :data);";
		loadAllSQL = "SELECT data from `" + _table + "`;";
		countSQL = "SELECT COUNT(*) from `" + _table + "`";
		deleteSQL = "DELETE FROM `" + _table + "`;";

		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("The following SQL statements will be used {}",
					new String[] { "insert", insertStatementSQL, "LOAD",
							loadAllSQL, "DELETE", deleteSQL, "CREATE_TABLE",
							createStatementSQL, "COUNT", countSQL });
		}

	}

	@Override
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

							return unserialize(_r.getBytes("data"));
						}
					}).iterator();

		} catch (final Exception e) {
			handle("Error with loadAll query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}
		return EmptyResultIterator.EMPTY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.event.storage.IEventDB#loadOnPeriod(org.komea.event.storage
	 * .DateInterval)
	 */
	@Override
	public ResultIterator<FlatEvent> loadOnPeriod(final DateInterval _period) {
		final ResultIterator<FlatEvent> iterator = EmptyResultIterator.EMPTY;
		Handle open = null;
		try {
			open = db.open();

			if (_period.isCompleteInterval()) {
				final String query = "SELECT * FROM " + table
						+ " WHERE date BETWEEN :from AND :to";
				final Query<FlatEvent> map = open.createQuery(query)
						.map(new MysqlResultHandlerMapper())
						.bind("from", _period.getFrom().toDate())
						.bind("to", _period.getTo().toDate());
				return map.iterator();

			} else if (_period.hasFrom()) {
				final String query = "SELECT * FROM " + table
						+ " WHERE date > :from";
				final Query<FlatEvent> map = open.createQuery(query)
						.map(new MysqlResultHandlerMapper())
						.bind("from", _period.getFrom().toDate());
				return map.iterator();
			} else if (_period.hasTo()) {
				final String query = "SELECT * FROM " + table
						+ " WHERE date < :to";
				final Query<FlatEvent> map = open.createQuery(query)
						.map(new MysqlResultHandlerMapper())
						.bind("to", _period.getTo().toDate());
				return map.iterator();
			} else {
				final String query = "SELECT * FROM " + table;
				final Query<FlatEvent> map = open.createQuery(query).map(
						new MysqlResultHandlerMapper());
				return map.iterator();
			}

		} catch (final Exception e) {
			handle("Error with removeAll query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}

		return iterator;

	}

	@Override
	public void put(final FlatEvent _entry) {
		Handle open = null;
		try {
			open = db.open();

			// date, data
			open.createStatement(insertStatementSQL)
					.bind("date", _entry.getDate())
					.bind("provider", _entry.getProvider())
					.bind("data", serialize(_entry)).execute();
		} catch (final Exception e) {
			handle("Error with put query", e);
		} finally {
			IOUtils.closeQuietly(open);
		}

	}

	@Override
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
	@Override
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

	public byte[] serialize(final FlatEvent _entry) {
		return serializer.apply(_entry);
	}

	public FlatEvent unserialize(final byte[] _entry) {

		return unserializer.apply(_entry);
	}

	/**
	 * Creates a table from the event type.
	 *
	 * @param _eventType
	 *            the
	 * @return the new table.
	 */
	private String createTable(final String _eventType) {
		final String newTable = "event_" + _eventType.hashCode();
		Handle open = null;
		try {
			open = db.open();
			open.begin();

			try (InputStream resourceAsStream = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(sourceSchema)) {

				String sqlQuery = IOUtils.toString(resourceAsStream);
				sqlQuery = sqlQuery.replaceAll("#table#", newTable);
				LOGGER.info("Name of the table created {}", newTable);
				open.execute(sqlQuery);
				open.commit();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			}
		} finally {
			IOUtils.closeQuietly(open);
		}
		return newTable;
	}

	private void handle(final String _message, final Exception _e) {
		LOGGER.error(_message, _e);

	}

	private ObjectMapper initJackson() {
		final JsonFactory jf = new JsonFactory();
		return new ObjectMapper(jf);
	}

}
