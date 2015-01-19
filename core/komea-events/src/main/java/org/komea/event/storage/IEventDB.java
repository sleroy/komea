package org.komea.event.storage;

import java.io.Closeable;
import java.util.Collection;

import org.komea.event.model.beans.FlatEvent;
import org.skife.jdbi.v2.ResultIterator;

public interface IEventDB extends Closeable {

	/**
	 * Count the number of values
	 *
	 * @return the number of values.
	 */
	long count();

	/**
	 * Load All Values. This is good for in-memory caches that have some keys
	 * that are persistent.
	 */
	ResultIterator<FlatEvent> loadAll();

	/**
	 * Load All Values. This is good for in-memory caches that have some keys
	 * that are persistent.
	 */
	ResultIterator<FlatEvent> loadOnPeriod(DateInterval _period);

	/**
	 * Puts a events into the storage.
	 *
	 * @param _flatEvent
	 *            the flat event.
	 */
	void put(FlatEvent _flatEvent);

	/**
	 * Put all values.
	 */
	void putAll(Collection<FlatEvent> values);

	/**
	 * Remove all values
	 */
	void removeAll();
}
