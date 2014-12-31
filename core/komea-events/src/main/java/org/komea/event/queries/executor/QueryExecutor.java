/**
 *
 */
package org.komea.event.queries.executor;

import org.apache.commons.io.IOUtils;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.ResultMapper;
import org.komea.event.queries.impl.EventQuery;
import org.komea.event.queries.rows.IRow;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.skife.jdbi.v2.ResultIterator;

/**
 * This class defines the query executor.
 *
 * @author sleroy
 */
public class QueryExecutor {

	private final IEventDBFactory	eventDBFactory;
	private final QueryResult	  queryResult	= new QueryResult();
	private final EventQuery	  eventQuery;

	/**
	 * @param _eventDBFactory
	 */
	public QueryExecutor(final IEventDBFactory _eventDBFactory,
			final EventQuery _eventQuery) {
		eventDBFactory = _eventDBFactory;
		eventQuery = _eventQuery;
	}

	/**
	 * Executes the event query.
	 *
	 * @param _eventQuery
	 * @return
	 */
	public QueryResult execute() {
		final ResultMapper<FlatEvent> resultMapper = eventQuery
				.getResultMapper();

		try {
			resultMapper.begin();

			// For all event types
			for (final String eventType : eventQuery.getFromEventTypes()) {
				performQueryOnEventType(eventType);

			}
		} finally {
			addRowIfExist(resultMapper.end());
		}
		// Browsing
		// Produces the result
		return queryResult;
	}

	/**
	 * Add a row if existing (not null)
	 *
	 * @param _row
	 *            the row or null.
	 */
	private void addRowIfExist(final IRow _row) {
		if (_row != null) {
			queryResult.addRow(_row);
		}

	}

	/**
	 * Test if the event match the clause
	 *
	 * @param _event
	 *            the event
	 * @return result of the clause evaluation.
	 */
	private boolean match(final FlatEvent _event) {

		return eventQuery.getWhereClosure().apply(_event);
	}

	private void performQueryOnEventType(final String eventType) {
		final IEventDB eventDB = eventDBFactory.getEventDB(eventType);
		ResultIterator<FlatEvent> loadAll = null;
		try {
			loadAll = eventDB.loadAll();
			while (loadAll.hasNext() && !reachLimit()) {
				final FlatEvent next = loadAll.next();
				if (match(next)) {
					addRowIfExist(eventQuery.getResultMapper().process(next));
				}
			}
		} finally {
			IOUtils.closeQuietly(loadAll);
		}
	}

	/**
	 * Tests if the number of records is reached.
	 *
	 * @return the reach limit.
	 */
	private boolean reachLimit() {

		return eventQuery.getRecordsNumber() != -1
				&& queryResult.countRows() >= eventQuery.getRecordsNumber();
	}
}
