package org.komea.event.queries.factory;

import org.komea.events.dao.EventsDao;
import org.komea.events.dto.SerializerType;
import org.komea.events.serializer.EventsSerializer;
import org.komea.events.serializer.IEventsSerializer;
import org.komea.events.service.EventStorageValidatorService;
import org.komea.events.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vibur.dbcp.ViburDBCPDataSource;

public class EventStorageFactory {

    private static class EventStorageFactoryHolder {

        private static final EventStorageFactory INSTANCE = new EventStorageFactory();

        private EventStorageFactoryHolder() {
        }
    }

    public static EventStorageFactory get() {
        return EventStorageFactoryHolder.INSTANCE;
    }

    private static final int MAX_SIZE = 100;

    private static final int MIN_SIZE = 2;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(EventStorageFactory.class);

    private EventStorageFactory() {
    }

    public EventsService newEventStorage(final DbType db_type,
            final SerializerType serializerType) {
        return newEventStorage(db_type, serializerType, db_type.getDefaultUrl());
    }

    public EventsService newEventStorage(final DbType db_type,
            final SerializerType serializerType, final String url) {
        return newEventStorage(db_type, serializerType, url,
                db_type.getDefaultUser(), db_type.getDefaultPassword(),
                db_type.getDriverName());
    }

    public EventsService newEventStorage(final DbType db_type,
            final SerializerType serializerType, final String url,
            final String user, final String password, final String _driver) {
        final String jdbcURL = "jdbc:" + db_type.getType() + ":" + url
                + db_type.getExtraOptions();
        LOGGER.info("URL {}", url);
        LOGGER.info("JDBC URL {}", jdbcURL);
        LOGGER.info("JDBC DRIVER {}", _driver);

        final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
                jdbcURL, user, password, _driver);
        final IEventsSerializer serializer = new EventsSerializer();
        serializer.setSerializerType(SerializerType.DEFAULT_SERIALIZER);
        final EventsDao eventsDao = new EventsDao();
        eventsDao.setSerializer(serializer);
        eventsDao.setDataSource(dataSource);
        final EventsService eventsService = new EventsService();
        eventsService.setEventsDao(eventsDao);
        eventsService.setValidator(new EventStorageValidatorService());
        return eventsService;
    }

    /**
     * @param _mpl
     * @return
     * @throws Exception
     */
    public EventsService newEventStorage(final Impl _mpl) throws Exception {
        switch (_mpl) {
            case H2_DISK_JACKSON:
                return newEventStorage(DbType.H2_FILE, SerializerType.JACKSON);
            case H2_DISK_KRYO:
                return newEventStorage(DbType.H2_FILE, SerializerType.KRYO);
            case H2_MEM_JACKSON:
                return newEventStorage(DbType.H2_MEM, SerializerType.JACKSON);
            case H2_MEM_KRYO:
                return newEventStorage(DbType.H2_MEM, SerializerType.KRYO);
            case MYSQL_JACKSON:
                return newEventStorage(DbType.MYSQL, SerializerType.JACKSON);
            case MYSQL_KRYO:
                return newEventStorage(DbType.MYSQL, SerializerType.KRYO);
            default:
                return null;
        }
    }

    private ViburDBCPDataSource createDataSourceWithStatementsCache(
            final String _url, final String user, final String password,
            final String _driver) {
        final ViburDBCPDataSource ds = new ViburDBCPDataSource();
        ds.setDriverClassName(_driver);
        ds.setJdbcUrl(_url);
        ds.setUsername(user);
        ds.setPassword(password);

        ds.setPoolInitialSize(MIN_SIZE);
        ds.setPoolMaxSize(MAX_SIZE);

        ds.setConnectionIdleLimitInSeconds(30);
        ds.setTestConnectionQuery("isValid");

        ds.setLogQueryExecutionLongerThanMs(500);
        ds.setLogStackTraceForLongQueryExecution(true);

        ds.setStatementCacheMaxSize(200);

        ds.start();
        return ds;
    }
}
