package org.komea.event.storage;

import java.io.Closeable;

/**
 * This interface defines a factory providing a key-value storage. Each
 * key-value storage is associated to a name.
 *
 * @author sleroy
 */
public interface IEventDBFactory extends Closeable {

	/**
	 * @param _type
	 */
	void declareEventType(String _type);

	/**
	 * Returns a storage with the given name
	 *
	 * @param _eventType
	 *            the name of the storage
	 * @return the storage.
	 */
	IEventDB getEventDB(String _eventType);
}
