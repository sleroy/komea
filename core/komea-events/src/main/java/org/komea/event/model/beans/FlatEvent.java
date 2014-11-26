package org.komea.event.model.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import org.komea.event.model.api.IFlatEvent;

import com.google.common.collect.Maps;

/**
 * This class defines the implementation of a complex event.
 *
 * @author sleroy
 *
 */
public class FlatEvent implements IFlatEvent {

	private Map<String, Serializable> properties = Maps.newHashMap();

	public FlatEvent() {
		super();
	}

	/**
	 * Builds a complex event with a map of properties
	 *
	 * @param _map
	 *            the map.
	 */
	public FlatEvent(final Map<String, ? extends Serializable> _map) {
		super();
		this.properties = Collections.unmodifiableMap(_map);
	}

	/**
	 * Adds a field to the complex even.
	 *
	 * @param _fieldName
	 *            the field name
	 * @param _object
	 *            the object
	 */
	public void addField(final String _fieldName, final Serializable _object) {
		this.properties.put(_fieldName, _object);

	}

	@Override
	public Map<String, ? extends Serializable> getProperties() {

		return this.properties;
	}

	@Override
	public String toString() {
		return "FlatEvent [properties=" + this.properties + "]";
	}

}