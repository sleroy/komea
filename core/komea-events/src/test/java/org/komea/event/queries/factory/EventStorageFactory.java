/**
 *
 */
package org.komea.event.queries.factory;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.komea.event.storage.impl.EventStorage;
import org.komea.event.storage.mysql.impl.JacksonMySQLAdvancedEventDBFactory;
import org.komea.event.storage.mysql.impl.MySQLAdvancedEventDBFactory;
import org.komea.event.storage.mysql.impl.MySQLEventDBFactory;
import org.komea.event.storage.orient.impl.OEventDBFactory;
import org.komea.event.utils.dpool.impl.DataSourceConnectionFactory;
import org.springframework.orientdb.session.impl.LocalDiskDatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration;
import org.vibur.dbcp.ViburDBCPDataSource;

/**
 * @author sleroy
 */
public class EventStorageFactory {

    private static final String H2_EXTRA_OPTONS = ";MODE=MYSQL;INIT=RUNSCRIPT FROM 'src/main/resources/schema-eventsh2.sql'";

    /**
     * Table name.
     */
    private static final String TABLE_EVENTS = "events";

    private static final int MAX_SIZE = 100;

    private static final int MIN_SIZE = 2;

    /**
     * @param _mpl
     * @return
     * @throws Exception
     */
    public EventStorage build(final Impl _mpl) throws Exception {

        switch (_mpl) {

            case H2_MEM:
                return buildH2Mem();
            case H2_DISK:
                return buildH2Disk();

            case H2_ADV_MEM:
                return buildH2AdvMem();
            case H2_ADV_DISK:
                return buildH2AdvDisk();
            case H2_JACKSON_ADV_DISK:
                return buildH2JacksonAdvDisk();

            case MYSQL:
                return buildMySql();

            case MYSQL_ADVANCED:
                return buildMySqlAdv();
            case ORIENTDB_MEM:
                return buildOrientDBMem();
            case ORIENTDB_DISK:
                return buildOrientDBDIsk();
            case MYSQL_JACKSON_ADV:
                return buildMysqlJacksonAdv();
            default:
                return null;

        }

    }

    public ViburDBCPDataSource createDataSourceWithStatementsCache(
            final String _url, final String user, final String password) {
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

    /**
     * @return @throws IOException
     */
    private EventStorage buildH2AdvDisk() throws IOException {
        final String path = getTemporaryPath();
        final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
                "jdbc:h2:file:" + path + H2_EXTRA_OPTONS, "sa", "");
        final MySQLAdvancedEventDBFactory eventDBFactory = new MySQLAdvancedEventDBFactory(
                new DataSourceConnectionFactory(dataSource));
        return new EventStorage(eventDBFactory);
    }

    /**
     * @return
     */
    private EventStorage buildH2AdvMem() {
        final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
                "jdbc:h2:mem:events" + new Date().getTime() + H2_EXTRA_OPTONS,
                "sa", "");
        final MySQLAdvancedEventDBFactory eventDBFactory = new MySQLAdvancedEventDBFactory(
                new DataSourceConnectionFactory(dataSource));
        return new EventStorage(eventDBFactory);
    }

    private EventStorage buildH2Disk() throws IOException {
        final String path = getTemporaryPath();
        final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
                "jdbc:h2:file:" + path + H2_EXTRA_OPTONS, "sa", "");
        final MySQLEventDBFactory eventDBFactory = new MySQLEventDBFactory(
                new DataSourceConnectionFactory(dataSource), TABLE_EVENTS);
        return new EventStorage(eventDBFactory);
    }

    /**
     * @return @throws IOException
     */
    private EventStorage buildH2JacksonAdvDisk() throws IOException {
        final String path = getTemporaryPath();
        final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
                "jdbc:h2:file:" + path + H2_EXTRA_OPTONS, "sa", "");
        final MySQLAdvancedEventDBFactory eventDBFactory = new MySQLAdvancedEventDBFactory(
                new DataSourceConnectionFactory(dataSource));
        return new EventStorage(eventDBFactory);
    }

    private EventStorage buildH2Mem() {
        final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
                "jdbc:h2:mem:events" + new Date().getTime() + H2_EXTRA_OPTONS,
                "sa", "");
        final MySQLEventDBFactory eventDBFactory = new MySQLEventDBFactory(
                new DataSourceConnectionFactory(dataSource), TABLE_EVENTS);
        return new EventStorage(eventDBFactory);
    }

    /**
     * @return @throws IOException
     */
    private EventStorage buildMySql() throws IOException {
        final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
                "jdbc:mysql://localhost/events", "root", "root");
        final MySQLEventDBFactory eventDBFactory = new MySQLEventDBFactory(
                new DataSourceConnectionFactory(dataSource), TABLE_EVENTS);
        return new EventStorage(eventDBFactory);
    }

    private EventStorage buildMySqlAdv() throws IOException {
        final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
                "jdbc:mysql://localhost/events", "root", "root");
        final MySQLAdvancedEventDBFactory eventDBFactory = new MySQLAdvancedEventDBFactory(
                new DataSourceConnectionFactory(dataSource));
        return new EventStorage(eventDBFactory);
    }

    private EventStorage buildMysqlJacksonAdv() throws IOException {
        final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
                "jdbc:mysql://localhost/events", "root", "root");
        final JacksonMySQLAdvancedEventDBFactory eventDBFactory = new JacksonMySQLAdvancedEventDBFactory(
                new DataSourceConnectionFactory(dataSource));
        return new EventStorage(eventDBFactory);
    }

    private EventStorage buildOrientDBDIsk() throws IOException {
        final String path = getTemporaryPath();
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

    private String getTemporaryPath() throws IOException {
        return new File(FileUtils.getTempDirectory(),
                String.valueOf(new Random().nextLong())).getAbsolutePath();
    }

}
