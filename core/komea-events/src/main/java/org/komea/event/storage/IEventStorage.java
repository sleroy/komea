package org.komea.event.storage;

import java.io.Closeable;

import org.komea.event.model.impl.KomeaEvent;

public interface IEventStorage extends Closeable {

	/**
	 * Clear of all the events of the type
	 *
	 * @param _eventType
	 *            the event type;
	 */
	public void clearEventsOfType(String _eventType);

	/**
	 * Declares a new event type.
	 *
	 * @param type
	 *            the new event type.
	 */
	public void declareEventType(String type);

	/**
	 * Tests if a storage exist for the event type.
	 * 
	 * @param _eventType
	 * @return
	 */
	public boolean existStorage(String _eventType);

	/**
	 * Stores a event with a flattened structure.
	 *
	 * @param _event
	 *            the event.
	 */
	public void storeEvent(KomeaEvent _event);

	/**
	 * Converts and store a pojo as a event.
	 *
	 * @param _object
	 *            the object
	 */
	public void storeEvent(Object _object);

	IEventDB getEventDB(String eventType);
}
