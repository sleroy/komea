package org.komea.event.model.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.komea.core.utils.PojoToMap;
import org.komea.event.model.api.IFlatEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * This class defines the implementation of a complex event.
 *
 * @author sleroy
 *
 */
public class FlatEvent implements IFlatEvent {

	private Map<String, Serializable>	properties	= Maps.newHashMap();

	private static final Logger	      LOGGER	   = LoggerFactory.getLogger(FlatEvent.class);

	public FlatEvent() {
		this.properties = Maps.newHashMap();
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
		this();
		final Map<String, Object> convertPojoInMap = new PojoToMap().convertPojoInMap(_pojo);
		for (final Entry<String, Object> entry : convertPojoInMap.entrySet()) {
			if (entry.getValue() instanceof Serializable) {
				this.properties.put(entry.getKey(), (Serializable) entry.getValue());
			} else {
				LOGGER.error("This field is not serializable {}", entry.getKey());
			}
		}

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