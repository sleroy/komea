package org.komea.event;

import org.komea.event.query.IEventQueryManager;
import org.komea.event.storage.IEventStorage;

public interface IEventDatabase {

	/**
	 *
	 * @return the query manager.
	 */
	IEventQueryManager getQueryManager();

	/**
	 * @return the event storage service
	 */
	IEventStorage getStorageManager();
}
