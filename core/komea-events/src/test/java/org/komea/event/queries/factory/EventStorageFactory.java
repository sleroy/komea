package org.komea.event.queries.factory;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.SerializerType;
import org.komea.event.storage.impl.EventStorage;
import org.komea.event.storage.mysql.impl.EventDBFactory;
import org.komea.event.storage.orient.impl.OEventDBFactory;
import org.komea.event.utils.dpool.impl.DataSourceConnectionFactory;
import org.springframework.orientdb.session.impl.LocalDiskDatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration;
import org.vibur.dbcp.ViburDBCPDataSource;

public class EventStorageFactory {

    private static final String TABLE_EVENTS = "events";

    private static final int MAX_SIZE = 100;

    private static final int MIN_SIZE = 2;

    public static EventStorageFactory get() {
        return EventStorageFactoryHolder.INSTANCE;
    }

    private EventStorageFactory() {
    }

    /**
     * @param _mpl
     * @return
     * @throws Exception
     */
    public EventStorage newEventStorage(final Impl _mpl) throws Exception {
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
            case ORIENTDB_MEM:
                return buildOrientDBMem();
            case ORIENTDB_DISK:
                return buildOrientDBDIsk();
            default:
                return null;
        }
    }

    public EventStorage newEventStorage(final DbType db_type, final SerializerType serializerType) {
        return newEventStorage(db_type, serializerType, db_type.getDefaultUrl());
    }

    public EventStorage newEventStorage(final DbType db_type, final SerializerType serializerType, final String url) {
        return newEventStorage(db_type, serializerType, url, db_type.getDefaultUser(), db_type.getDefaultPassword());
    }

    public EventStorage newEventStorage(final DbType db_type, final SerializerType serializerType, final String url, final String user, final String password) {
        final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
                "jdbc:" + db_type.getType() + ":" + url + db_type.getExtraOptions(), user, password);
        final IEventDBFactory eventDBFactory = new EventDBFactory(new DataSourceConnectionFactory(dataSource));
        return new EventStorage(eventDBFactory);
    }

    private ViburDBCPDataSource createDataSourceWithStatementsCache(final String _url, final String user, final String password) {
        final ViburDBCPDataSource ds = new ViburDBCPDataSource();
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

    private EventStorage buildOrientDBDIsk() throws IOException {
        final String path = new File(FileUtils.getTempDirectory(),
                String.valueOf(new Random().nextLong())).getAbsolutePath();
        final LocalDiskDatabaseConfiguration configuration = new LocalDiskDatabaseConfiguration(
                path, TABLE_EVENTS + new Date().getTime());
        configuration.setMaxPoolSize(MAX_SIZE);//
        configuration.setMinPoolSize(MIN_SIZE);
        final OrientSessionFactory<ODatabaseDocumentTx> orientSessionFactory = new OrientSessionFactory<>(
                configuration);
        return new EventStorage(new OEventDBFactory(orientSessionFactory));
    }

    /**
     * @return @throws IOException
     */
    private EventStorage buildOrientDBMem() throws IOException {
        // BUG moisi avec les storages restant ouverts...
        final TestDatabaseConfiguration configuration = new TestDatabaseConfiguration();
        configuration.setMaxPoolSize(MAX_SIZE);//
        configuration.setMinPoolSize(MIN_SIZE);
        final OrientSessionFactory<ODatabaseDocumentTx> orientSessionFactory = new OrientSessionFactory<>(
                configuration);
        return new EventStorage(new OEventDBFactory(orientSessionFactory));
    }

    private static class EventStorageFactoryHolder {

        private static final EventStorageFactory INSTANCE = new EventStorageFactory();

        private EventStorageFactoryHolder() {
        }
    }
}
