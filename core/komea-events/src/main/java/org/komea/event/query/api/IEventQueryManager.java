package org.komea.event.query.api;

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;

import org.komea.orientdb.session.document.IODocument;

import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

public interface IEventQueryManager extends Closeable {
	/**
	 * Returns the number of events of the type provided in parameter
	 *
	 * @param _eventType
	 *            the event typê
	 * @return the number of events
	 */
	long countEventsOfType(String _eventType);

	/**
	 * Counts number of events of a given type with a condition provided as a
	 * where filter.
	 *
	 * @param _eventType
	 *            the event type
	 * @param whereFilter
	 *            the filter
	 * @return the number of events.
	 */
	long countEventsPerQuery(String _eventType, String whereFilter);

	/**
	 * Returns all the events of a given type
	 *
	 * @param _eventType
	 *            the event type
	 * @return the list of event types.
	 */
	ORecordIteratorClass<ODocument> browseEvents(String _eventType);

	/**
	 * Executes a query on the event database and returns the results as
	 * proxies.
	 *
	 * @param _query
	 *            the queyr
	 * @return the results as a proxy.
	 */
	Iterator<IODocument> query(String _query);

	/**
	 * Executes a raw query on the document database, returns the record as
	 * OrientDB documents (non proxified)
	 *
	 * @param _query
	 *            the queyr
	 * @return the list of ODocuments.
	 */
	List<ODocument> rawQuery(String _query);

}
