package org.komea.event.storage.api;

import java.io.Closeable;

import org.komea.event.model.api.IBasicEvent;
import org.komea.event.model.api.IComplexEvent;
import org.komea.event.model.api.IFlatEvent;

public interface IEventStorage extends Closeable {
	/**
	 * Stores a event with basic informations.
	 *
	 * @param _event
	 *            the event.
	 */
	public void storeComplexEvent(IComplexEvent _event);

	/**
	 * Stores a event with basic informations.
	 *
	 * @param _event
	 *            the event.
	 */
	public void storeEvent(IBasicEvent _event);

	/**
	 * Stores a event with a flattened structure.
	 *
	 * @param _event
	 *            the event.
	 */
	public void storeFlatEvent(IFlatEvent _event);
}
