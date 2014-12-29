package org.komea.event.storage.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.komea.event.model.IBasicEventInformations;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.mysql.impl.DataSourceConnectionFactory;
import org.komea.event.storage.mysql.impl.MySQLEventDBFactory;
import org.komea.event.storage.orientdb.impl.OEventDBFactory;
import org.skife.jdbi.v2.ResultIterator;
import org.springframework.orientdb.session.impl.LocalDiskDatabaseConfiguration;
import org.springframework.orientdb.session.impl.OrientSessionFactory;
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration;
import org.vibur.dbcp.ViburDBCPDataSource;

import com.orientechnologies.orient.core.Orient;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.tocea.frameworks.bench4j.BenchRule;
import com.tocea.frameworks.bench4j.BenchmarkOptions;
import com.tocea.frameworks.bench4j.IBenchReport;
import com.tocea.frameworks.bench4j.reports.jfreechart.JFreeChartBenchmarkReport;

@RunWith(Parameterized.class)
public class EventStoragePerformanceTest {

	@Parameters(name = "events={0},threads={1},fetch_all={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 100, 1, false }, { 100, 10, true }, { 1000, 1, false },
		        { 1000, 10, false }, { 1000, 10, true }, { 1000, 10, false }, { 10000, 1, false }, { 10000, 10, true },
		        { 10000, 10, false } });
	}

	@BeforeClass
	public static void setup() {
		DEMO_EVENT.put(IBasicEventInformations.FIELD_EVENT_TYPE, NEW_BUG);
		DEMO_EVENT.put(IBasicEventInformations.FIELD_PROVIDER, "bugzilla");
		DEMO_EVENT.put("bug_id", 12);
		DEMO_EVENT.put("message", "Call to XXX creates premature exception");
		DEMO_EVENT.put("author", "sleroy");
		DEMO_EVENT.put("reporter", "rgalerme");
	}

	private static final int	     BENCH	         = 2;

	private static final int	     WARMUP	         = 1;

	private static final String	     H2_EXTRA_OPTONS	= ";MODE=MYSQL;INIT=RUNSCRIPT FROM 'src/main/resources/schema-eventsh2.sql'";

	public static final IBenchReport	report	     = new JFreeChartBenchmarkReport(new File("build/charts"), 1024,
	                                                         768);

	/**
	 * Enables the benchmark rule.
	 */
	@Rule
	public BenchRule	             benchmarkRun	 = new BenchRule(report);

	private static final String	     NEW_BUG	     = "new_bug";

	@Parameter(value = 0)
	public int	                     NUMBER_EVENTS	 = 2000;
	@Parameter(value = 1)
	public int	                     NUMBER_THREADS	 = 10;
	@Parameter(value = 2)
	public boolean	                 FETCH_ALL	     = true;

	public static final FlatEvent	 DEMO_EVENT	     = new FlatEvent();

	private static final int	     MAX_SIZE	     = 100;

	private static final int	     MIN_SIZE	     = 2;

	@Rule
	public final TemporaryFolder	 temporaryFolder	= new TemporaryFolder();

	public ViburDBCPDataSource createDataSourceWithStatementsCache(final String _url, String user, String password) {
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

	@BenchmarkOptions(warmupRounds = WARMUP, benchmarkRounds = BENCH)
	@Test
	public void testH2Local() throws IOException, InterruptedException {
		final TemporaryFolder temporaryFolder2 = new TemporaryFolder();
		temporaryFolder2.create();
		final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache("jdbc:h2:"
		        + temporaryFolder2.getRoot().getPath() + H2_EXTRA_OPTONS, "sa", "");
		try {
			final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_THREADS);
			final MySQLEventDBFactory eventDBFactory = new MySQLEventDBFactory(new DataSourceConnectionFactory(
			        dataSource), "events");
			performTests(executorService, eventDBFactory);
		} finally {

			dataSource.terminate();
		}
	}

	@BenchmarkOptions(warmupRounds = WARMUP, benchmarkRounds = BENCH)
	@Test
	public void testH2Mem() throws InterruptedException {
		final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache(
		        "jdbc:h2:mem:event" + new Date().getTime() + H2_EXTRA_OPTONS, "sa", "");
		try {
			final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_THREADS);
			final MySQLEventDBFactory eventDBFactory = new MySQLEventDBFactory(new DataSourceConnectionFactory(
			        dataSource), "events");
			performTests(executorService, eventDBFactory);
		} finally {
			dataSource.terminate();
		}
	}

	@BenchmarkOptions(warmupRounds = WARMUP, benchmarkRounds = BENCH)
	@Test
	public void testMySQL() throws IOException, InterruptedException {
		final TemporaryFolder temporaryFolder2 = new TemporaryFolder();
		temporaryFolder2.create();
		final ViburDBCPDataSource dataSource = createDataSourceWithStatementsCache("jdbc:mysql://localhost/events",
		        "root", "root");
		try {
			final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_THREADS);
			final MySQLEventDBFactory eventDBFactory = new MySQLEventDBFactory(new DataSourceConnectionFactory(
			        dataSource), "event");
			performTests(executorService, eventDBFactory);
		} finally {

			dataSource.terminate();
		}
	}

	@BenchmarkOptions(warmupRounds = WARMUP, benchmarkRounds = BENCH)
	@Test
	public void testOrientDB() throws IOException, InterruptedException {
		// BUG moisi avec les storages restant ouverts...
		final LocalDiskDatabaseConfiguration configuration = new LocalDiskDatabaseConfiguration(temporaryFolder
		        .getRoot().getPath(), "events" + new Date().getTime());
		configuration.setMaxPoolSize(MAX_SIZE);//
		configuration.setMinPoolSize(MIN_SIZE);
		final OrientSessionFactory<ODatabaseDocumentTx> orientSessionFactory = new OrientSessionFactory<>(configuration);
		try {
			// orientSessionFactory.db().drop();
			final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_THREADS);

			final OEventDBFactory eventDBFactory = new OEventDBFactory(orientSessionFactory);
			performTests(executorService, eventDBFactory);
		} finally {
			orientSessionFactory.getOrCreateDB().drop();
			orientSessionFactory.close();
			Orient.instance().closeAllStorages();

		}
	}

	@BenchmarkOptions(warmupRounds = WARMUP, benchmarkRounds = BENCH)
	@Test
	public void testOrientDBMem() throws IOException, InterruptedException {
		// BUG moisi avec les storages restant ouverts...
		final TestDatabaseConfiguration configuration = new TestDatabaseConfiguration();
		configuration.setMaxPoolSize(MAX_SIZE);//
		configuration.setMinPoolSize(MIN_SIZE);
		final OrientSessionFactory<ODatabaseDocumentTx> orientSessionFactory = new OrientSessionFactory<>(configuration);
		try {
			// orientSessionFactory.db().drop();
			final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_THREADS);

			final OEventDBFactory eventDBFactory = new OEventDBFactory(orientSessionFactory);
			performTests(executorService, eventDBFactory);
		} finally {
			orientSessionFactory.getOrCreateDB().drop();

			orientSessionFactory.close();
			Orient.instance().closeAllStorages();

		}
	}

	private void performTests(final ExecutorService executorService, final IEventDBFactory _eventDBFactory)
	        throws InterruptedException {
		_eventDBFactory.getEventDB(NEW_BUG).removeAll();
		assertEquals(0, _eventDBFactory.getEventDB(NEW_BUG).count());

		final EventStorage eventStorage = new EventStorage(_eventDBFactory);
		for (int i = 0; i < NUMBER_THREADS; ++i) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < NUMBER_EVENTS; ++j) {
						eventStorage.storeFlatEvent(EventStoragePerformanceTest.DEMO_EVENT);
					}
				}
			});
		}
		executorService.shutdown();
		final boolean awaitTermination = executorService.awaitTermination(10, TimeUnit.MINUTES);
		assertTrue(awaitTermination);

		final long count = _eventDBFactory.getEventDB(NEW_BUG).count();
		assertEquals(NUMBER_EVENTS * NUMBER_THREADS, count);
		if (FETCH_ALL) {
			int read = 0;
			final ResultIterator<FlatEvent> loadAll = _eventDBFactory.getEventDB(NEW_BUG).loadAll();
			while (loadAll.hasNext()) {

				assertNotNull(loadAll.next());
				read++;
			}
			assertEquals(count, read);
		}

		_eventDBFactory.getEventDB(NEW_BUG).removeAll();
		assertEquals(0, _eventDBFactory.getEventDB(NEW_BUG).count());
	}

}
