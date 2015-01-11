/**
 *
 */
package org.komea.event.queries.executor.impl;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.komea.event.model.beans.FlatEvent;
import org.komea.event.queries.result.IResultMapper;
import org.komea.event.queries.rows.IRow;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.skife.jdbi.v2.ResultIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class defines the query executor.
 *
 * @author sleroy
 */
public class EventQueryExecutor {

	private final IEventDBFactory eventDBFactory;
	private final EventQueryResult eventQueryResult = new EventQueryResult();
	private final EventQuery eventQuery;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EventQueryExecutor.class);

	/**
	 * @param _eventDBFactory
	 */
	public EventQueryExecutor(final IEventDBFactory _eventDBFactory,
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
	public EventQueryResult execute() {
		final IResultMapper<FlatEvent> resultMapper = eventQuery
				.getResultMapper();

		try {
			LOGGER.debug("Preparing the query");
			resultMapper.begin();
			LOGGER.debug("Processing event types {}",
					eventQuery.getFromEventTypes());
			// For all event types
			for (final String eventType : eventQuery.getFromEventTypes()) {
				LOGGER.debug("=> Querying event type {}", eventType);
				performQueryOnEventType(eventType);

			}
		} finally {
			addRowIfExist(resultMapper.end());
		}
		// Browsing
		// Produces the result
		return eventQueryResult;
	}

	/**
	 * Add a row if existing (not null)
	 *
	 * @param _list
	 *            the row or null.
	 */
	private void addRowIfExist(final List<? extends IRow> _list) {
		if (_list != null) {
			eventQueryResult.addRows((List) _list);
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
		if (_event == null) {
			return false;
		}
		return eventQuery.getWhereClosure().apply(_event);
	}

	private void performQueryOnEventType(final String eventType) {
		final IEventDB eventDB = eventDBFactory.getEventDB(eventType);
		ResultIterator<FlatEvent> eventSetIterator = null;
		try {
			if (eventQuery.hasInterval()) {
				eventSetIterator = eventDB.loadOnPeriod(eventQuery.getPeriod());
			} else {
				eventSetIterator = eventDB.loadAll();
			}
			while (eventSetIterator.hasNext() && !reachLimit()) {
				final FlatEvent next = eventSetIterator.next();
				if (match(next)) {
					addRowIfExist(eventQuery.getResultMapper().process(next));
				}
			}
		} finally {
			IOUtils.closeQuietly(eventSetIterator);
		}
	}

	/**
	 * Tests if the number of records is reached.
	 *
	 * @return the reach limit.
	 */
	private boolean reachLimit() {

		return eventQuery.getRecordsNumber() != -1
				&& eventQueryResult.countRows() >= eventQuery
						.getRecordsNumber();
	}
}
