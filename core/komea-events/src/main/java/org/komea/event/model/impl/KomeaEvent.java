package org.komea.event.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.komea.core.utils.PojoToMap;
import org.komea.event.model.IKomeaEvent;

/**
 * This class defines the implementation of a complex event.
 *
 * @author sleroy
 */
public class KomeaEvent implements IKomeaEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Object> properties = new HashMap<>();

    public KomeaEvent() {
        this.properties.put(IKomeaEvent.FIELD_DATE, new Date());
    }

    /**
     * Builds a complex event with a map of properties
     *
     * @param _map the map.
     */
    public KomeaEvent(final Map<String, Object> _map) {
        this.properties = new HashMap(_map);
    }

    public KomeaEvent(final Object _pojo) {
        this();
        final Map<String, Serializable> convertPojoInMap = new PojoToMap().convertPojoInMap(_pojo);
        this.properties.putAll(convertPojoInMap);

    }

    public KomeaEvent(final String _provider, final String _eventType) {
        this();
        this.properties.put(IKomeaEvent.FIELD_PROVIDER, _provider);
        this.properties.put(IKomeaEvent.FIELD_EVENT_TYPE, _eventType);
    }

    public KomeaEvent(final String _provider, final String _eventType,
            final Date _date) {
        this(_provider, _eventType);
        this.properties.put(IKomeaEvent.FIELD_DATE, new Date());
    }

    /**
     * Adds a field to the complex even.
     *
     * @param _fieldName the field name
     * @param _object
     */
    @JsonIgnore
    public void addField(final String _fieldName, final Object _object) {
        this.properties.put(_fieldName, _object);

    }

    @JsonIgnore
    public void addFields(final Map<String, ?> map) {
        this.properties.putAll(map);
    }

    @JsonIgnore
    public boolean containsField(final String _fieldDate) {

        return this.properties.containsKey(_fieldDate);
    }

    /**
     * Returns the field associated to the given key and cast it in the expected
     * return type
     *
     * @param <T>
     * @param _key the key
     * @return the field value or null
     */
    @JsonIgnore
    @SuppressWarnings("unchecked")
    public <T> T field(final String _key) {

        return (T) this.properties.get(_key);
    }

    /**
     * Returns a field expecting the given thpe
     *
     * @param <T>
     * @param _fieldName the field name
     * @param _class the field typ
     * @return the value or null.
     */
    @JsonIgnore
    @SuppressWarnings("unchecked")
    public <T> T field(final String _fieldName, final Class<T> _class) {

        if (Date.class.equals(_class)) {
            return (T) getDateObject(_fieldName);
        }
        return _class.cast(this.field(_fieldName));
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
            return this.field(_fieldName);
        }
        return _value.equals(this.field(_fieldName, _value.getClass()));
    }

    public Date getDate() {
        return field(FIELD_DATE, Date.class);
    }

    private Date getDateObject(final String key) {
        final Object date = field(key);
        if (date instanceof Long) {
            return new Date((long) date);
        }
        return (Date) date;
    }

    /**
     * Returns the event type
     *
     * @return the event type.
     */
    @JsonIgnore
    public String getEventType() {
        return field(FIELD_EVENT_TYPE, String.class);
    }

    public Map<String, Object> getProperties() {

        return Collections.unmodifiableMap(this.properties);
    }

    @JsonIgnore
    public String getProvider() {
        return field(FIELD_PROVIDER, String.class);
    }

    /**
     * Tests if the event is after that time.
     *
     * @param _previousDate
     * @return true if the event has been sent before this date.
     */
    @JsonIgnore
    public boolean isAfter(final DateTime _previousDate) {
        return this.getDateTime().isAfter(_previousDate);
    }

    /**
     * Tests if the event is before that time.
     *
     * @param _dateTime the date time.
     * @return true if the event has been sent before this date.
     */
    @JsonIgnore
    public boolean isBefore(final DateTime _dateTime) {

        return this.getDateTime().isBefore(_dateTime);
    }

    /**
     * Adds a new field to the event
     *
     * @param _key the key
     * @param _object the object;
     */
    @JsonIgnore
    public void put(final String _key, final Object _object) {
        this.properties.put(_key, _object);
    }

    @JsonIgnore
    public void setDate(final Date _date) {
        this.properties.put(FIELD_DATE, _date);

    }

    @JsonIgnore
    public void setDateTime(final DateTime _date) {
        Validate.notNull(_date);
        this.properties.put(FIELD_DATE, _date.toDate());

    }

    /**
     * @param _string
     */
    @JsonIgnore
    public void setEventType(final String _string) {
        this.properties.put(FIELD_EVENT_TYPE, _string);

    }

    /**
     * @param _properties the properties to set
     */
    public void setProperties(final Map<String, Object> _properties) {
        this.properties = _properties;
    }

    /**
     * @param _string
     */
    @JsonIgnore
    public void setProvider(final String _string) {
        this.properties.put(FIELD_PROVIDER, _string);

    }

    @Override
    public String toString() {
        return "KomeaEvent{" + "properties=" + this.properties + '}';
    }

    /**
     * Return the date in joda time.
     *
     * @return the date
     */
    @JsonIgnore
    private DateTime getDateTime() {
        return new DateTime(this.getDate());
    }

}
