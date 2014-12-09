package org.komea.event.storage;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.komea.event.model.IFlatEvent;
import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;

public interface IEventStorage {
	/**
	 * Closes the document database used by this service.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException;

	/**
	 * Stores a event with basic informations.
	 *
	 * @param _event
	 *            the event.
	 */
	public void storeBasicEvent(BasicEvent _event);

	/**
	 * Stores a event with basic informations.
	 *
	 * @param _event
	 *            the event.
	 */
	public void storeComplexEvent(ComplexEvent _event);

	/**
	 * Stores a event with a flattened structure.
	 *
	 * @param _event
	 *            the event.
	 */
	public void storeEvent(AbstractEvent _event);

	/**
	 * Stores a event with a flattened structure.
	 *
	 * @param _event
	 *            the event.
	 */
	public void storeFlatEvent(IFlatEvent _event);

	/**
	 * Stores a event with a flattened structure.
	 *
	 * @param _fieldMap
	 *            the event.
	 */
	public void storeMap(Map<String, Serializable> _fieldMap);

	/**
	 * Provides directly a pojo that will be converted into a flat event.
	 *
	 * @param _pojo
	 */
	public void storePojo(Object _pojo);
}
