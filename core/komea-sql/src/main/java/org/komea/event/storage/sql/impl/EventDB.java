package org.komea.event.storage.sql.impl;

import com.esotericsoftware.kryo.Kryo;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.komea.event.model.SerializerType;
import org.komea.event.model.impl.DateInterval;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.impl.DateMapper;
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
import org.skife.jdbi.v2.util.StringMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventDB implements IEventDB {

    private class MysqlResultHandlerMapper implements
            ResultSetMapper<KomeaEvent> {

        @Override
        public KomeaEvent map(final int _index, final ResultSet _r,
                final StatementContext _ctx) throws SQLException {
            return EventDB.this.unserialize(_r.getBytes(COLUMN_DATA));
        }
    }

    /**
     * Creates a table from the event type.
     *
     * @param _eventType the
     */
    public static String createTable(
            final ConnectionFactory _connectionFactory, final String _eventType) {
        final String table = generateTableName(_eventType);
        final DBI db = new DBI(_connectionFactory);
        Handle handle = null;
        try {
            handle = db.open();
            handle.begin();
            final InputStream resourceAsStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(SQL_FILE_CREATE_TABLE);
            String sqlQuery = IOUtils.toString(resourceAsStream);
            sqlQuery = sqlQuery.replaceAll("#table#", table);
            LOGGER.info("Name of the table created = '{}'", table);
            final int[] execute = handle.createScript(sqlQuery).execute();
            LOGGER.debug("Creation table result {}", execute);
            // handle.execute(sqlQuery);
            handle.commit();
        } catch (final Exception ex) {
            handleException("Error with createTable query", ex);
        } finally {
            IOUtils.closeQuietly(handle);
        }
        return table;
    }

    public static List<String> getEventTypes(final ConnectionFactory _connectionFactory) {
        final List<String> tables = Lists.newArrayList();
        final DBI db = new DBI(_connectionFactory);
        Handle handle = null;
        try {
            handle = db.open();
            final Iterator<String> iterator = handle.createQuery("show tables")
                    .map(new StringMapper()).iterator();
            while (iterator.hasNext()) {
                final String tableName = iterator.next();
                if (tableName.startsWith(TABLE_PREFIX)) {
                    tables.add(tableName.substring(TABLE_PREFIX.length()));
                }
            }
        } finally {
            IOUtils.closeQuietly(handle);
        }
        return tables;
    }

    /**
     * Generation of a table name
     *
     * @param _eventType the event type
     * @return the table name.
     */
    public static String generateTableName(final String _eventType) {
        final StringBuilder sb = new StringBuilder();
        sb.append(TABLE_PREFIX);
        for (int i = 0; i < _eventType.length(); i++) {
            final char lowerCase = Character.toLowerCase(_eventType.charAt(i));
            if (Character.isDigit(lowerCase) || Character.isLetter(lowerCase)) {
                sb.append(lowerCase);
            }
        }
        return sb.toString();
    }

    public static void handleException(final String _message, final Exception _e) {
        // LOGGER.error(_message, _e);
        throw new RuntimeException(_message, _e);
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
    private String lastEventSQL;

    private String loadAllSQL;

    private final String table;

    private Function<byte[], KomeaEvent> unserializer;

    private Function<KomeaEvent, byte[]> serializer;

    public EventDB(final ConnectionFactory _dataSource,
            final String _eventType, final SerializerType serializerType) {
        Validate.notEmpty(_eventType);
        Validate.notNull(_dataSource, "database connection required");
        this.db = new DBI(_dataSource);
        this.table = generateTableName(_eventType);
        this.createSQL(this.table);
        this.initSerializer(serializerType);
    }

    @Override
    public void close() throws IOException {
        this.db = null;
    }

    /**
     * Count the number of elements
     */
    @Override
    public long count() {
        Long res = 0L;
        Handle handle = null;
        try {
            handle = this.db.open();
            final Query<Long> map = handle.createQuery(this.countSQL).map(
                    LongMapper.FIRST);
            res = map.first();
        } catch (final Exception e) {
            handleException("Error with count query", e);
        } finally {
            IOUtils.closeQuietly(handle);
        }
        return res;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.event.storage.IEventDB#existStorage()
     */
    @Override
    public boolean existStorage() {
        Handle handle = null;
        try {
            handle = this.db.open();
            final Query<Long> map = handle.createQuery(this.countSQL).map(
                    LongMapper.FIRST);
            map.first();
        } catch (final Exception e) {
            return false;
        } finally {
            IOUtils.closeQuietly(handle);
        }
        return true;

    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.event.storage.IEventDB#getLastEvent()
     */
    @Override
    public DateTime getLastEvent() {
        Date res = null;
        Handle handle = null;
        try {
            handle = this.db.open();
            final Query<Date> map = handle.createQuery(this.lastEventSQL).map(
                    DateMapper.FIRST);
            res = map.first();
        } catch (final Exception e) {
            handleException("Error with lastEvent query", e);
        } finally {
            IOUtils.closeQuietly(handle);
        }
        return res == null ? null : new DateTime(res);
    }

    @Override
    public ResultIterator<KomeaEvent> loadAll() {
        LOGGER.debug("LOAD ALL  ");
        Handle handle = null;
        try {
            handle = this.db.open();
            return handle.createQuery(this.loadAllSQL)
                    .map(new ResultSetMapper<KomeaEvent>() {

                        @Override
                        public KomeaEvent map(final int _index,
                                final ResultSet _r, final StatementContext _ctx)
                        throws SQLException {
                            return EventDB.this.unserialize(_r
                                    .getBytes(COLUMN_DATA));
                        }
                    }).iterator();
        } catch (final Exception e) {
            handleException("Error with loadAll query", e);
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
    public ResultIterator<KomeaEvent> loadOnPeriod(final DateInterval _period) {
        final ResultIterator<KomeaEvent> iterator = EmptyResultIterator.EMPTY;
        final String query = this.getQuery(_period);
        Handle handle = null;
        try {
            handle = this.db.open();
            final Query<KomeaEvent> map = handle.createQuery(query)
                    .map(new MysqlResultHandlerMapper())
                    .bind("from", _period.getFrom())
                    .bind("to", _period.getTo());
            return map.iterator();
        } catch (final Exception e) {
            handleException("Error with removeAll query", e);
        } finally {
            IOUtils.closeQuietly(handle);
        }
        return iterator;
    }

    @Override
    public void put(final KomeaEvent _entry) {
        Handle handle = null;
        try {
            handle = this.db.open();
            final int execute = handle.createStatement(this.insertStatementSQL)
                    .bind(COLUMN_DATE, _entry.getDate())
                    .bind(COLUMN_PROVIDER, _entry.getProvider())
                    .bind(COLUMN_DATA, this.serialize(_entry)).execute();
            LOGGER.trace("Insertion result {}", execute);
        } catch (final Exception e) {
            handleException("Error with put query", e);
        } finally {
            IOUtils.closeQuietly(handle);
        }
    }

    @Override
    public void putAll(final Collection<KomeaEvent> values) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("PUT ALL {}", values);
        }
        Handle handle = null;
        try {
            handle = this.db.open();
            final Collection<KomeaEvent> entries = values;
            handle.begin();// WRAPS A TRANSACTION IF NOT ALREADY CREATED
            for (final KomeaEvent entry : entries) {
                this.put(entry);
            }
            handle.commit();
        } catch (final Exception e) {
            handleException("Error with putAll query", e);
        } finally {
            IOUtils.closeQuietly(handle);
        }
    }

    @Override
    public void removeAll() {
        Handle handle = null;
        try {
            handle = this.db.open();
            handle.execute(this.deleteSQL);
        } catch (final Exception e) {
            LOGGER.error("Error with removeAll query", e);
        } finally {
            IOUtils.closeQuietly(handle);
        }
    }

    public byte[] serialize(final KomeaEvent _entry) {
        return this.serializer.apply(_entry);
    }

    public KomeaEvent unserialize(final byte[] _entry) {
        return this.unserializer.apply(_entry);
    }

    private void createSQL(final String _table) {
        this.insertStatementSQL = "INSERT INTO `" + _table + "` ("
                + COLUMN_DATE + ", " + COLUMN_PROVIDER + ", " + COLUMN_DATA
                + ") VALUES (:" + COLUMN_DATE + ", :" + COLUMN_PROVIDER + ", :"
                + COLUMN_DATA + ");";
        this.loadAllSQL = "SELECT " + COLUMN_DATA + " FROM `" + _table + "`;";
        this.countSQL = "SELECT COUNT(*) from `" + _table + "`";
        this.deleteSQL = "DELETE FROM `" + _table + "`;";
        this.lastEventSQL = "SELECT MAX(" + COLUMN_DATE + ") from `" + _table
                + "`";
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("The following SQL statements will be used {}",
                    new Object[]{"insert", this.insertStatementSQL, "LOAD",
                        this.loadAllSQL, "DELETE", this.deleteSQL,
                        "CREATE_TABLE", this.createStatementSQL, "COUNT",
                        this.countSQL});
        }

    }

    private String getQuery(final DateInterval _period) {
        String query = "SELECT * FROM " + this.table;
        if (_period.isCompleteInterval()) {
            query += " WHERE " + COLUMN_DATE + " BETWEEN :from AND :to";
        } else if (_period.hasFrom()) {
            query += " WHERE " + COLUMN_DATE + " > :from";
        } else if (_period.hasTo()) {
            query += " WHERE " + COLUMN_DATE + " < :to";
        }
        return query;
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
        kryo.register(KomeaEvent.class);
        return kryo;
    }

    private void initSerializer(final SerializerType serializerType) {
        switch (serializerType) {
            case KRYO:
                final Kryo kryo = this.initKryo();
                this.unserializer = new KryoByteArrayToObjectConverter<>(kryo,
                        KomeaEvent.class);
                this.serializer = new KryoObjectToByteArrayConverter<>(kryo);
                break;
            default:
                final ObjectMapper jackson = this.initJackson();
                this.unserializer = new JacksonByteArrayToObjectConverter<>(
                        jackson, KomeaEvent.class);
                this.serializer = new JacksonObjectToByteArrayConverter<>(jackson);
                break;
        }
    }
}
