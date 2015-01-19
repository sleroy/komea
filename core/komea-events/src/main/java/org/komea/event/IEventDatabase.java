package org.komea.event;

import org.komea.event.storage.IEventStorage;

public interface IEventDatabase {

	/**
	 * @return the event storage service
	 */
	IEventStorage getStorageManager();
}
