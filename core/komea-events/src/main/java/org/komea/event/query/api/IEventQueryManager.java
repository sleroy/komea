package org.komea.event.query.api;

import java.io.Closeable;

public interface IEventQueryManager extends Closeable {
	/**
	 * Returns the number of events of the type provided in parameter
	 *
	 * @param _eventType
	 *            the event typê
	 * @return the number of events
	 */
	long countEventsOfType(String _eventType);

}
