package org.komea.event.model.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * This class defines the implementation of a complex event.
 *
 * @author sleroy
 *
 */
public class ComplexEvent extends AbstractEvent {

	private Map<String, Serializable>	properties	= Maps.newHashMap();

	public ComplexEvent() {
		super();
	}

	/**
	 * Builds a complex event with a map of properties
	 *
	 * @param _map
	 *            the map.
	 */
	public ComplexEvent(final Map<String, ? extends Serializable> _map) {
		super();
		this.properties = Collections.unmodifiableMap(_map);
	}

	/**
	 * Builds a complex event with default values
	 *
	 * @param _provider
	 *            the provider
	 * @param _eventType
	 *            the event type
	 * @param _newDate
	 *            the new date
	 */
	public ComplexEvent(final String _provider, final String _eventType, final Date _newDate) {
		super(_provider, _eventType, _newDate);
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

	public Map<String, ? extends Serializable> getProperties() {

		return this.properties;
	}

	public void setProperties(final Map<String, Serializable> _properties) {
		this.properties = _properties;
	}

	@Override
	public String toString() {
		return "ComplexEvent [properties=" + this.properties + "]";
	}

}