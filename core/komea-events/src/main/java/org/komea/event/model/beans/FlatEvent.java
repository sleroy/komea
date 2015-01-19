package org.komea.event.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.komea.core.utils.PojoToMap;
import org.komea.event.model.IBasicEventInformations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class defines the implementation of a complex event.
 *
 * @author sleroy
 */
public class FlatEvent implements IBasicEventInformations, Serializable {

    private Map<String, Object> properties = Maps.newHashMap();

    private static final Logger LOGGER = LoggerFactory
            .getLogger(FlatEvent.class);

    public FlatEvent() {
        properties = Maps.newHashMap();
        properties.put(IBasicEventInformations.FIELD_DATE, new Date());
    }

    /**
     * Builds a complex event with a map of properties
     *
     * @param _map the map.
     */
    public FlatEvent(final Map<String, Object> _map) {
        properties = _map;
    }

    public FlatEvent(final Object _pojo) {
        this();
        final Map<String, Object> convertPojoInMap = new PojoToMap()
                .convertPojoInMap(_pojo);

        for (final Entry<String, Object> entry : convertPojoInMap
                .entrySet()) {
            properties.put(entry.getKey(), entry.getValue());

        }

    }

    /**
     * Adds a field to the complex even.
     *
     * @param _fieldName the field name
     * @param _objecton the object
     */
    @JsonIgnore
    public void addField(final String _fieldName, final Object _object) {
        properties.put(_fieldName, _object);

    }

    @JsonIgnore
    public boolean containsField(final String _fieldDate) {

        return properties.containsKey(_fieldDate);
    }

    /**
     * Returns the field associated to the given key and cast it in the expected
     * return type
     *
     * @param _key the key
     * @return the field value or null
     */
    @JsonIgnore
    public <T> T field(final String _key) {

        return (T) properties.get(_key);
    }

    /**
     * Returns a field expecting the given thpe
     *
     * @param _fieldName the field name
     * @param _class the field typ
     * @return the value or null.
     */
    public <T> T field(final String _fieldName, final Class<T> _class) {

        return _class.cast(field(_fieldName));
    }

    /**
     * Tests if a fields is equals to the given value
     *
     * @param _fieldName
     * @param _value
     * @return
     */
    @JsonIgnore
    public boolean fieldEquals(final String _fieldName, final Object _value) {
        if (_value == null) {
            return _value == field(_fieldName);
        }
        return _value.equals(_fieldName);
    }

    public Date getDate() {

        return (Date) properties.get(FIELD_DATE);
    }

    /**
     * Returns the event type
     *
     * @return the event type.
     */
    public String getEventType() {
        return (String) properties.get(FIELD_EVENT_TYPE);
    }

    public Map<String, Object> getProperties() {

        return Collections.unmodifiableMap(properties);
    }

    public String getProvider() {

        return (String) properties.get(FIELD_PROVIDER);
    }

    /**
     * Tests if the event is after that time.
     *
     * @param _dateTime the date time.
     * @return true if the event has been sent before this date.
     */
    @JsonIgnore
    public boolean isAfter(final DateTime _previousDate) {
        return getDateTime().isAfter(_previousDate);
    }

    /**
     * Tests if the event is before that time.
     *
     * @param _dateTime the date time.
     * @return true if the event has been sent before this date.
     */
    @JsonIgnore
    public boolean isBefore(final DateTime _dateTime) {

        return getDateTime().isBefore(_dateTime);
    }

    /**
     * Adds a new field to the event
     *
     * @param _key the key
     * @param _object the object;
     */
    @JsonIgnore
    public void put(final String _key, final Object _object) {
        properties.put(_key, _object);
    }

    public void setDate(final Date _date) {
        properties.put(FIELD_DATE, _date);

    }

    public void setDateTime(final DateTime _date) {
        Validate.notNull(_date);
        properties.put(FIELD_DATE, _date.toDate());

    }

    /**
     * @param _string
     */
    public void setEventType(final String _string) {
        properties.put(FIELD_EVENT_TYPE, _string);

    }

    /**
     * @param _string
     */
    public void setProvider(final String _string) {
        properties.put(FIELD_PROVIDER, _string);

    }

    @Override
    public String toString() {
        return "FlatEvent [properties=" + properties + "]";
    }

    /**
     * Return the date in joda time.
     *
     * @return the date
     */
    @JsonIgnore
    private DateTime getDateTime() {

        return new DateTime(getDate());
    }

}
