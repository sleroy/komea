package org.komea.events.dao;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import org.apache.commons.io.IOUtils;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.KomeaEvent;
import org.komea.events.serializer.IEventsSerializer;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.LongMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsDao implements IEventsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsDao.class);

    public static final String TABLE_PREFIX = "event_";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PROVIDER = "provider";
    public static final String COLUMN_DATA = "data";

    @Autowired
    private IEventsSerializer serializer;

    @Autowired
    private DataSource dataSource;

    private Handle handle;

    @PostConstruct
    public void init() {
        final DBI dbi = new DBI(dataSource);
        handle = dbi.open();
    }

    @PreDestroy
    public void destroy() {
        IOUtils.closeQuietly(handle);
    }

    @Override
    public synchronized void declareEventType(final String eventType) {
        final String tableName = generateTableName(eventType);
        final String query = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id BIGINT(20) NOT NULL AUTO_INCREMENT,"
                + COLUMN_DATE + " DATETIME NOT NULL,"
                + COLUMN_PROVIDER + " VARCHAR(128) NOT NULL,"
                + COLUMN_DATA + " TEXT NOT NULL,"
                + "PRIMARY KEY (id),"
                + "INDEX (" + COLUMN_DATE + "))";
        final int insert = handle.insert(query);
        if (insert > 0) {
            LOGGER.info("Name of the table created = '{}'", tableName);
        }
    }

    @Override
    public void putEvent(final KomeaEvent _event) {
        handle.createStatement(insertStatementSQL(_event.getEventType()))
                .bind(COLUMN_DATE, _event.getDate())
                .bind(COLUMN_PROVIDER, _event.getProvider())
                .bind(COLUMN_DATA, serializer.serialize(_event)).execute();
    }

    @Override
    public long countEventsOfType(final String eventType) {
        return handle.createQuery(countSQL(eventType)).map(LongMapper.FIRST).first();
    }

    @Override
    public void clearEventsOfType(final String eventType) {
        handle.update(deleteSQL(eventType));
    }

    @Override
    public List<String> getEventTypes() {
        final List<String> tables = Lists.newArrayList();
        final Iterator<String> iterator = handle.createQuery("show tables")
                .map(new StringMapper()).iterator();
        while (iterator.hasNext()) {
            final String tableName = iterator.next();
            if (tableName.startsWith(TABLE_PREFIX)) {
                tables.add(tableName.substring(TABLE_PREFIX.length()));
            }
        }
        return tables;
    }

    @Override
    public List<KomeaEvent> getAllEventsOnPeriod(final DateInterval interval, final int limit) {
        final List<KomeaEvent> events = new ArrayList<>(limit);
        for (final String eventType : getEventTypes()) {
            final List<KomeaEvent> subList = Lists.newArrayList(loadEventsOfTypeOnPeriod(eventType, interval));
            events.addAll(subList);
            Collections.sort(events);
            if (events.size() > limit) {
                events.removeAll(events.subList(limit, events.size()));
            }
        }
        return events;
    }

    @Override
    public Iterator<KomeaEvent> loadEventsOfType(final String eventType) {
        LOGGER.debug("LOAD ALL  ");
        return handle.createQuery(loadAllSQL(eventType))
                .map(new ResultSetMapper<KomeaEvent>() {

                    @Override
                    public KomeaEvent map(final int _index,
                            final ResultSet _r, final StatementContext _ctx)
                    throws SQLException {
                        return serializer.deserialize(_r.getBytes(COLUMN_DATA));
                    }
                }).iterator();
    }

    @Override
    public Iterator<KomeaEvent> loadEventsOfTypeOnPeriod(final String eventType, final DateInterval _period) {
        final String query = getQuery(eventType, _period);
        final Query<KomeaEvent> map = handle.createQuery(query)
                .map(new KomeaEventMapper());
        return map.iterator();
    }

    private String generateTableName(final String _eventType) {
        final StringBuilder sb = new StringBuilder(TABLE_PREFIX);
        for (int i = 0; i < _eventType.length(); i++) {
            final char lowerCase = Character.toLowerCase(_eventType.charAt(i));
            if (Character.isDigit(lowerCase) || Character.isLetter(lowerCase)) {
                sb.append(lowerCase);
            }
        }
        return sb.toString();
    }

    private String insertStatementSQL(final String eventType) {
        final String tableName = generateTableName(eventType);
        return "INSERT INTO `" + tableName + "` (" + COLUMN_DATE
                + ", " + COLUMN_PROVIDER + ", " + COLUMN_DATA + ") VALUES (:"
                + COLUMN_DATE + ", :" + COLUMN_PROVIDER + ", :" + COLUMN_DATA + ")";
    }

    private String loadAllSQL(final String eventType) {
        final String tableName = generateTableName(eventType);
        return "SELECT " + COLUMN_DATA + " FROM `" + tableName + "`"
                + " ORDER BY " + COLUMN_DATE + " DESC";
    }

    private String countSQL(final String eventType) {
        final String tableName = generateTableName(eventType);
        return "SELECT COUNT(*) from `" + tableName + "`";
    }

    private String deleteSQL(final String eventType) {
        final String tableName = generateTableName(eventType);
        return "DELETE FROM `" + tableName + "`";
    }

    private Timestamp getTimestamp(final Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    private String getQuery(final String eventType, final DateInterval _period) {
        final String tableName = generateTableName(eventType);
        final Timestamp from = getTimestamp(_period.getFrom());
        final Timestamp to = getTimestamp(_period.getTo());
        String query = "SELECT " + COLUMN_DATA + " FROM " + tableName + " WHERE " + COLUMN_DATE + " ";
        if (_period.isCompleteInterval()) {
            query += "BETWEEN '" + from + "' AND '" + to + "'";
        } else if (_period.hasFrom()) {
            query += "> '" + from + "'";
        } else if (_period.hasTo()) {
            query += "< '" + to + "'";
        }
        query += " ORDER BY " + COLUMN_DATE + " DESC";
        return query;
    }

    private class KomeaEventMapper implements ResultSetMapper<KomeaEvent> {

        @Override
        public KomeaEvent map(final int _index, final ResultSet _r,
                final StatementContext _ctx) throws SQLException {
            return serializer.deserialize(_r.getBytes(COLUMN_DATA));
        }
    }

    private class StringMapper implements ResultSetMapper<String> {

        @Override
        public String map(final int _index, final ResultSet _r,
                final StatementContext _ctx) throws SQLException {
            return _r.getString(1);
        }
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(handle);
    }

    public void setSerializer(IEventsSerializer serializer) {
        this.serializer = serializer;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        init();
    }

}
