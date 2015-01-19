package org.komea.event.storage.mysql.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.DateInterval;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.SerializerType;
import org.komea.event.storage.impl.EmptyResultIterator;
import org.komea.event.utils.jackson.impl.JacksonByteArrayToObjectConverter;
import org.komea.event.utils.jackson.impl.JacksonObjectToByteArrayConverter;
import org.komea.event.utils.kryo.impl.KryoByteArrayToObjectConverter;
import org.komea.event.utils.kryo.impl.KryoObjectToByteArrayConverter;
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

import com.esotericsoftware.kryo.Kryo;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;

public class EventDB implements IEventDB {

	private class MysqlResultHandlerMapper implements
			ResultSetMapper<FlatEvent> {

		@Override
		public FlatEvent map(final int _index, final ResultSet _r,
				final StatementContext _ctx) throws SQLException {
			return unserialize(_r.getBytes(COLUMN_DATA));
		}
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(EventDB.class);
	public static final String TABLE_PREFIX = "event_";
	public static final String COLUMN_DATA = "data";
	public static final String COLUMN_PROVIDER = "provider";
	public static final String COLUMN_DATE = "date";

	public static final String SQL_FILE_CREATE_TABLE = "sql/create_table.sql";
	private DBI db;
	private String insertStatementSQL;
	private String createStatementSQL;
	private String deleteSQL;
	private String countSQL;
	private String loadAllSQL;

	private String table;
	private Function<byte[], FlatEvent> unserializer;

	private Function<FlatEvent, byte[]> serializer;

	public EventDB(final ConnectionFactory _dataSource,
			final String _eventType, final SerializerType serializerType) {
		Validate.notEmpty(_eventType);
		Validate.notNull(_dataSource, "database connection required");
		db = new DBI(_dataSource);
		createTable(_eventType);
		createSQL(table);
		initSerializer(serializerType);
	}

	@Override
	public void close() throws IOException {
		db = null;
	}

	/**
	 * Count the number of elements
	 */
	@Override
	public long count() {
		final Long res = 0L;
		Handle handle = null;
		try {
			handle = db.open();
			final Query<Long> map = handle.createQuery(countSQL).map(
					LongMapper.FIRST);
			final Object obj = map.first();
			return (Long) obj;
		} catch (final Throwable e) {
			handle("Error with count query", (Exception) e);
		} finally {
			IOUtils.closeQuietly(handle);
		}
		return res;
	}

	@Override
	public ResultIterator<FlatEvent> loadAll() {
		LOGGER.debug("LOAD ALL  ");
		Handle handle = null;
		try {
			handle = db.open();
			return handle.createQuery(loadAllSQL)
					.map(new ResultSetMapper<FlatEvent>() {

						@Override
						public FlatEvent map(final int _index,
								final ResultSet _r, final StatementContext _ctx)
								throws SQLException {
							return unserialize(_r.getBytes(COLUMN_DATA));
						}
					}).iterator();
		} catch (final Exception e) {
			handle("Error with loadAll query", e);
		} finally {
			IOUtils.closeQuietly(handle);
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
		final String query = getQuery(_period);
		Handle handle = null;
		try {
			handle = db.open();
			final Query<FlatEvent> map = handle.createQuery(query)
					.map(new MysqlResultHandlerMapper())
					.bind("from", _period.getFrom().toDate())
					.bind("to", _period.getTo().toDate());
			return map.iterator();
		} catch (final Exception e) {
			handle("Error with removeAll query", e);
		} finally {
			IOUtils.closeQuietly(handle);
		}
		return iterator;
	}

	@Override
	public void put(final FlatEvent _entry) {
		Handle handle = null;
		try {
			handle = db.open();
			handle.createStatement(insertStatementSQL)
					.bind(COLUMN_DATE, _entry.getDate())
					.bind(COLUMN_PROVIDER, _entry.getProvider())
					.bind(COLUMN_DATA, serialize(_entry)).execute();
		} catch (final Exception e) {
			handle("Error with put query", e);
		} finally {
			IOUtils.closeQuietly(handle);
		}
	}

	@Override
	public void putAll(final Collection<FlatEvent> values) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("PUT ALL {}", values);
		}
		Handle handle = null;
		try {
			handle = db.open();
			final Collection<FlatEvent> entries = values;
			handle.begin();// WRAPS A TRANSACTION IF NOT ALREADY CREATED
			for (final FlatEvent entry : entries) {
				put(entry);
			}
			handle.commit();
		} catch (final Exception e) {
			handle("Error with putAll query", e);
		} finally {
			IOUtils.closeQuietly(handle);
		}
	}

	@Override
	public void removeAll() {
		Handle handle = null;
		try {
			handle = db.open();
			handle.execute(deleteSQL);
		} catch (final Exception e) {
			handle("Error with removeAll query", e);
		} finally {
			IOUtils.closeQuietly(handle);
		}
	}

	public byte[] serialize(final FlatEvent _entry) {
		return serializer.apply(_entry);
	}

	public FlatEvent unserialize(final byte[] _entry) {
		return unserializer.apply(_entry);
	}

	private void createSQL(final String _table) {
		insertStatementSQL = "INSERT INTO `" + _table + "` (" + COLUMN_DATE
				+ ", " + COLUMN_PROVIDER + ", " + COLUMN_DATA + ") VALUES (:"
				+ COLUMN_DATE + ", :" + COLUMN_PROVIDER + ", :" + COLUMN_DATA
				+ ");";
		loadAllSQL = "SELECT " + COLUMN_DATA + " from `" + _table + "`;";
		countSQL = "SELECT COUNT(*) from `" + _table + "`";
		deleteSQL = "DELETE FROM `" + _table + "`;";

		if (LOGGER.isDebugEnabled()) {
			LOGGER.info("The following SQL statements will be used {}",
					new Object[] { "insert", insertStatementSQL, "LOAD",
							loadAllSQL, "DELETE", deleteSQL, "CREATE_TABLE",
							createStatementSQL, "COUNT", countSQL });
		}

	}

	/**
	 * Creates a table from the event type.
	 *
	 * @param _eventType
	 *            the
	 */
	private void createTable(final String _eventType) {
		table = TABLE_PREFIX + generateTableName(_eventType);
		Handle handle = null;
		try {
			handle = db.open();
			handle.begin();
			final InputStream resourceAsStream = Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(SQL_FILE_CREATE_TABLE);
			String sqlQuery = IOUtils.toString(resourceAsStream);
			sqlQuery = sqlQuery.replaceAll("#table#", table);
			LOGGER.info("Name of the table created {}", table);
			handle.execute(sqlQuery);
			handle.commit();
		} catch (final IOException ex) {
			handle("Error with createTable query", ex);
		} finally {
			IOUtils.closeQuietly(handle);
		}
	}

	/**
	 * Generation of a table name
	 *
	 * @param _eventType
	 *            the event type
	 * @return the table name.
	 */
	private String generateTableName(final String _eventType) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < _eventType.length(); i++) {
			final char lowerCase = Character.toLowerCase(_eventType.charAt(i));
			if (Character.isDigit(lowerCase) || Character.isLetter(lowerCase)) {
				sb.append(lowerCase);
			}
		}
		return sb.toString();
	}

	private String getQuery(final DateInterval _period) {
		String query = "SELECT * FROM " + table;
		if (_period.isCompleteInterval()) {
			query += " WHERE " + COLUMN_DATE + " BETWEEN :from AND :to";
		} else if (_period.hasFrom()) {
			query += " WHERE " + COLUMN_DATE + " > :from";
		} else if (_period.hasTo()) {
			query += " WHERE " + COLUMN_DATE + " < :to";
		}
		return query;
	}

	private void handle(final String _message, final Exception _e) {
		// LOGGER.error(_message, _e);
		throw new RuntimeException(_message, _e);
	}

	/**
	 * Initializing Jackson
	 */
	private ObjectMapper initJackson() {
		final JsonFactory jf = new JsonFactory();
		return new ObjectMapper(jf);
	}

	/**
	 * Initializing Kryo
	 *
	 * @return
	 */
	private Kryo initKryo() {
		final Kryo kryo = new Kryo();
		kryo.register(List.class);
		kryo.register(ArrayList.class);
		kryo.register(HashMap.class);
		kryo.register(Map.class);
		kryo.register(HashSet.class);
		kryo.register(String.class);
		kryo.register(FlatEvent.class);
		return kryo;
	}

	private void initSerializer(final SerializerType serializerType) {
		switch (serializerType) {
		case KRYO:
			final Kryo kryo = initKryo();
			unserializer = new KryoByteArrayToObjectConverter<>(kryo,
					FlatEvent.class);
			serializer = new KryoObjectToByteArrayConverter<>(kryo);
			break;
		default:
			final ObjectMapper jackson = initJackson();
			unserializer = new JacksonByteArrayToObjectConverter<>(jackson,
					FlatEvent.class);
			serializer = new JacksonObjectToByteArrayConverter<>(jackson);
			break;
		}
	}

}
