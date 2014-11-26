package org.komea.event.database.api;

import org.komea.event.query.api.IEventQueryManager;
import org.komea.event.storage.api.IEventStorage;

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
