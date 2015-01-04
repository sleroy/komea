/**
 *
 */
package org.komea.event.queries.demo;

import java.io.IOException;
import java.util.Date;

import org.junit.rules.TemporaryFolder;
import org.komea.event.storage.impl.EventStorage;
import org.komea.event.storage.mysql.impl.MySQLAdvancedEventDBFactory;
import org.komea.event.storage.mysql.impl.MySQLEventDBFactory;
import org.komea.event.storage.orient.impl.OEventDBFactory;
import org.komea.event.utils.dpool.impl.DataSourceConnectionFactory;
import org.springframework.orientdb.session.impl.LocalDiskDatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration;
import org.vibur.dbcp.ViburDBCPDataSource;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

/**
 * @author sleroy
 */
public class EventStorageFactory {
	private static final String	H2_EXTRA_OPTONS	= ";MODE=MYSQL;INIT=RUNSCRIPT FROM 'src/main/resources/schema-eventsh2.sql'";

	/**
	 * Table name.
	 */
	private static final String	TABLE_EVENTS	= "events";

	private static final int	MAX_SIZE	    = 100;

	private static final int	MIN_SIZE	    = 2;

	/**
	 * @param _mpl
	 * @return
	 * @throws Exception
	 */
	public EventStorage build(final Impl _mpl) throws Exception {
		final TemporaryFolder temporaryFolder = new TemporaryFolder();
		temporaryFolder.create();

		switch (_mpl) {

			case H2_MEM:
				return buildH2Mem();
			case H2_DISK:
				return buildH2Disk();

			case H2_ADV_MEM:
				return buildH2AdvMem();
			case H2_ADV_DISK:
				return buildH2AdvDisk();

			case MYSQL:
				return buildMySql();

			case MYSQL_ADVANCED:
				return buildMySqlAdv();
			case ORIENTDB_MEM:
				return buildOrientDBMem();
			case ORIENTDB_DISK:
				return buildOrientDBDIsk();

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
	 * @return
	 * @throws IOException
	 */
	private EventStorage buildH2AdvDisk() throws IOException {
		final TemporaryFolder temporaryFolder2 = new TemporaryFolder();
		temporaryFolder2.create();
		final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
				"jdbc:h2:" + temporaryFolder2.getRoot().getPath()
				+ H2_EXTRA_OPTONS, "sa", "");
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
		final TemporaryFolder temporaryFolder2 = new TemporaryFolder();
		temporaryFolder2.create();
		final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
				"jdbc:h2:" + temporaryFolder2.getRoot().getPath()
				+ H2_EXTRA_OPTONS, "sa", "");
		final MySQLEventDBFactory eventDBFactory = new MySQLEventDBFactory(
				new DataSourceConnectionFactory(dataSource), TABLE_EVENTS);
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
	 * @return
	 * @throws IOException
	 */
	private EventStorage buildMySql() throws IOException {
		final TemporaryFolder temporaryFolder2 = new TemporaryFolder();
		temporaryFolder2.create();
		final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
				"jdbc:mysql://localhost/events", "root", "root");
		final MySQLEventDBFactory eventDBFactory = new MySQLEventDBFactory(
				new DataSourceConnectionFactory(dataSource), TABLE_EVENTS);
		return new EventStorage(eventDBFactory);
	}

	private EventStorage buildMySqlAdv() throws IOException {
		final TemporaryFolder temporaryFolder2 = new TemporaryFolder();
		temporaryFolder2.create();
		final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
				"jdbc:mysql://localhost/events", "root", "root");
		final MySQLAdvancedEventDBFactory eventDBFactory = new MySQLAdvancedEventDBFactory(
				new DataSourceConnectionFactory(dataSource));
		return new EventStorage(eventDBFactory);
	}

	private EventStorage buildOrientDBDIsk() throws IOException {
		final TemporaryFolder temporaryFolder2 = new TemporaryFolder();
		temporaryFolder2.create();
		final LocalDiskDatabaseConfiguration configuration = new LocalDiskDatabaseConfiguration(
				temporaryFolder2.getRoot().getPath(), TABLE_EVENTS
				+ new Date().getTime());
		configuration.setMaxPoolSize(MAX_SIZE);//
		configuration.setMinPoolSize(MIN_SIZE);
		final OrientSessionFactory<ODatabaseDocumentTx> orientSessionFactory = new OrientSessionFactory<>(
				configuration);
		return new EventStorage(new OEventDBFactory(orientSessionFactory));
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private EventStorage buildOrientDBMem() throws IOException {
		final TemporaryFolder temporaryFolder2 = new TemporaryFolder();
		temporaryFolder2.create();
		// BUG moisi avec les storages restant ouverts...
		final TestDatabaseConfiguration configuration = new TestDatabaseConfiguration();
		configuration.setMaxPoolSize(MAX_SIZE);//
		configuration.setMinPoolSize(MIN_SIZE);
		final OrientSessionFactory<ODatabaseDocumentTx> orientSessionFactory = new OrientSessionFactory<>(
				configuration);
		return new EventStorage(new OEventDBFactory(orientSessionFactory));
	}

}
