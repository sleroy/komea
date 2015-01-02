package org.komea.event.queries;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.queries.column.impl.ColumnMapper;
import org.komea.event.queries.column.impl.CountColumn;
import org.komea.event.queries.executor.QueryExecutor;
import org.komea.event.queries.executor.QueryResult;
import org.komea.event.queries.impl.EventQuery;
import org.komea.event.storage.impl.EventStorage;
import org.komea.event.storage.orient.impl.OEventDBFactory;
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration;

import com.orientechnologies.orient.core.Orient;
import com.tocea.frameworks.bench4j.BenchmarkOptions;

@BenchmarkOptions(warmupRounds = 5, benchmarkRounds = 20)
public class EventQueryLimitTest {

	/**
	 *
	 */
	private static final String	EVENT_NAME	= "new_bug";
	private static final int	MAX_EVENTS	= 10000;
	private EventStorage	    es;
	private OEventDBFactory	    eventDBFactory;
	
	@After
	public void after() throws IOException {
		es.close();
		Orient.instance().closeAllStorages();
	}

	@Before
	public void before() {
		eventDBFactory = new OEventDBFactory(new TestDatabaseConfiguration());
		es = new EventStorage(eventDBFactory);
		for (int i = 0; i < MAX_EVENTS; ++i) {
			es.storeBasicEvent(new BasicEvent("bugzilla", EVENT_NAME));
		}
	}

	@Test
	public void testQueryCountRows() throws Exception {

		final EventQuery eventQuery = new EventQuery();
		eventQuery.eventTypes(EVENT_NAME);
		eventQuery.returnsEvents();
		eventQuery.limit(1);
		final QueryExecutor queryExecutor = new QueryExecutor(eventDBFactory,
				eventQuery);
		final QueryResult queryResult = queryExecutor.execute();
		assertEquals(1, queryResult.countRows());
	}

	@Test
	public void testQueryCountRowsWithMapper() throws Exception {

		final EventQuery eventQuery = new EventQuery();
		eventQuery.eventTypes(EVENT_NAME);
		eventQuery.returnsResultMapper(ColumnMapper
				.create()
				.newColumn(
						"count",
						new CountColumn())
						.build());
		eventQuery.limit(1);
		final QueryExecutor queryExecutor = new QueryExecutor(eventDBFactory,
				eventQuery);
		final QueryResult queryResult = queryExecutor.execute();
		assertEquals(MAX_EVENTS, queryResult.firstValue());
	}
}
