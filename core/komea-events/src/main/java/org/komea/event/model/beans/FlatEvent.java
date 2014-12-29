package org.komea.event.model.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.komea.core.utils.PojoToMap;
import org.komea.event.model.IBasicEventInformations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

/**
 * This class defines the implementation of a complex event.
 *
 * @author sleroy
 *
 */
public class FlatEvent implements IBasicEventInformations {

	private Map<String, Serializable>	properties	= Maps.newHashMap();

	private static final Logger	      LOGGER	   = LoggerFactory.getLogger(FlatEvent.class);

	public FlatEvent() {
		this.properties = Maps.newHashMap();
		this.properties.put(IBasicEventInformations.FIELD_DATE, new Date());
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
		final Map<String, Serializable> convertPojoInMap = new PojoToMap().convertPojoInMap(_pojo);

		for (final Entry<String, Serializable> entry : convertPojoInMap.entrySet()) {
			this.properties.put(entry.getKey(), entry.getValue());

		}

	}

	/**
	 * Adds a field to the complex even.
	 *
	 * @param _fieldName
	 *            the field name
	 * @param _objecton
	 *            the object
	 */
	public void addField(final String _fieldName, final Serializable _object) {
		this.properties.put(_fieldName, _object);

	}

	@JsonIgnore
	public boolean containsField(final String _fieldDate) {

		return this.properties.containsKey(_fieldDate);
	}

	/**
	 * Returns the field associated to the given key and cast it in the expected
	 * return type
	 *
	 * @param _key
	 *            the key
	 * @return the field value or null
	 */
	@JsonIgnore
	public <T> T field(final String _key) {

		return (T) this.properties.get(_key);
	}

	/**
	 * Returns the event type
	 *
	 * @return the event type.
	 */
	public String getEventType() {
		return (String) this.properties.get(FIELD_EVENT_TYPE);
	}

	public Map<String, ? extends Serializable> getProperties() {

		return Collections.unmodifiableMap(this.properties);
	}

	/**
	 * Adds a new field to the event
	 *
	 * @param _key
	 *            the key
	 * @param _object
	 *            the object;
	 */
	public void put(final String _key, final Serializable _object) {
		this.properties.put(_key, _object);
	}

	@Override
	public String toString() {
		return "FlatEvent [properties=" + this.properties + "]";
	}

	public Date getDate() {

	    return (Date) properties.get(FIELD_DATE);
    }

	public String getProvider() {

	    return (String) properties.get(FIELD_PROVIDER);
    }

}