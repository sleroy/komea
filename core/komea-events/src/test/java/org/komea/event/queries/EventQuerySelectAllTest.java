package org.komea.event.queries;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.queries.column.impl.ColumnMapper;
import org.komea.event.queries.column.impl.CountColumn;
import org.komea.event.queries.executor.impl.EventQuery;
import org.komea.event.queries.executor.impl.EventQueryExecutor;
import org.komea.event.queries.executor.impl.EventQueryResult;
import org.komea.event.queries.factory.EventStorageFactory;
import org.komea.event.queries.factory.Impl;
import org.komea.event.storage.impl.EventStorage;

import com.tocea.frameworks.bench4j.BenchmarkOptions;

@BenchmarkOptions(warmupRounds = 5, benchmarkRounds = 20)
public class EventQuerySelectAllTest {

	/**
	 *
	 */
	private static final String EVENT_NAME = "new_bug";
	private static final int MAX_EVENTS = 10000;
	private EventStorage es;

	@After
	public void after() throws IOException {
		es.close();
	}

	@Before
	public void before() throws Exception {
		es = EventStorageFactory.get().newEventStorage(Impl.H2_DISK_JACKSON);
		for (int i = 0; i < MAX_EVENTS; ++i) {
			es.storeBasicEvent(new BasicEvent("bugzilla", EVENT_NAME));
		}
	}

	@Test
	public void testQueryCountRows() throws Exception {

		final EventQuery eventQuery = new EventQuery();
		eventQuery.eventTypes(EVENT_NAME);
		eventQuery.returnsEvents();
		final EventQueryExecutor eventQueryExecutor = new EventQueryExecutor(
				es.getEventDBFactory(), eventQuery);
		final EventQueryResult eventQueryResult = eventQueryExecutor.execute();
		assertEquals(MAX_EVENTS, eventQueryResult.countRows());
	}

	@Test
	public void testQueryCountRowsWithMapper() throws Exception {

		final EventQuery eventQuery = new EventQuery();
		eventQuery.eventTypes(EVENT_NAME);
		eventQuery.returnsResultMapper(ColumnMapper.create()
				.newColumn("count", new CountColumn()).build());

		final EventQueryExecutor eventQueryExecutor = new EventQueryExecutor(
				es.getEventDBFactory(), eventQuery);
		final EventQueryResult eventQueryResult = eventQueryExecutor.execute();
		assertEquals(MAX_EVENTS, eventQueryResult.firstValue());
	}
}
