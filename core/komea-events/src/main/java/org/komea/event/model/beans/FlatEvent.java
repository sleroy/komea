package org.komea.event.model.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
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
		this(Maps.newHashMap());
	}

	/**
	 * Builds a complex event with a map of properties
	 *
	 * @param _map
	 *            the map.
	 */
	public FlatEvent(final Map<String, Serializable> _map) {
		this.properties = _map;
	}

	public FlatEvent(final Object _pojo) {
		this(Maps.newHashMap(new BeanMap(_pojo)));

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

		return Collections.unmodifiableMap(this.properties);
	}

	@Override
	public String toString() {
		return "FlatEvent [properties=" + this.properties + "]";
	}

}