package org.komea.events.dto;

import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;

/**
 * This class defines the implementation of a complex event.
 *
 * @author sleroy
 */
public class KomeaEvent implements Serializable, Comparable<KomeaEvent> {

    private static final long serialVersionUID = 1L;

    public static final String FIELD_PROVIDER = "komea_provider";
    public static final String FIELD_EVENT_TYPE = "komea_eventType";
    public static final String FIELD_DATE = "komea_date";

    private Map<String, Object> properties = Maps.newHashMap();

    public KomeaEvent() {
        properties.put(FIELD_DATE, new Date());
    }

    public KomeaEvent(final String _provider, final String _eventType) {
        this();
        properties.put(FIELD_PROVIDER, _provider);
        properties.put(FIELD_EVENT_TYPE, _eventType);
    }

    public KomeaEvent(final String _provider, final String _eventType, final Date _date) {
        this(_provider, _eventType);
        properties.put(FIELD_DATE, _date);
    }

    /**
     * Builds a complex event with a map of properties
     *
     * @param _map the map.
     */
    public KomeaEvent(final Map<String, Object> _map) {
        properties = _map;
    }

    /**
     * Adds a field to the complex even.
     *
     * @param _fieldName the field name
     * @param _object
     */
    public void addField(final String _fieldName, final Object _object) {
        properties.put(_fieldName, _object);

    }

    public void addFields(final Map<String, Object> map) {
        properties.putAll(map);
    }

    public boolean containsField(final String _fieldDate) {

        return properties.containsKey(_fieldDate);
    }

    /**
     * Returns the field associated to the given key and cast it in the expected
     * return type
     *
     * @param <T>
     * @param _key the key
     * @return the field value or null
     */
    @SuppressWarnings("unchecked")
    public <T> T field(final String _key) {

        return (T) properties.get(_key);
    }

    /**
     * Returns a field expecting the given thpe
     *
     * @param <T>
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
    public boolean fieldEquals(final String _fieldName, final Object _value) {
        if (_value == null) {
            return _value == field(_fieldName);
        }
        return _value.equals(field(_fieldName));
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
     * @param _previousDate
     * @return true if the event has been sent before this date.
     */
    public boolean isAfter(final DateTime _previousDate) {
        return getDateTime().isAfter(_previousDate);
    }

    /**
     * Tests if the event is before that time.
     *
     * @param _dateTime the date time.
     * @return true if the event has been sent before this date.
     */
    public boolean isBefore(final DateTime _dateTime) {

        return getDateTime().isBefore(_dateTime);
    }

    /**
     * Adds a new field to the event
     *
     * @param _key the key
     * @param _object the object;
     */
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
        return "KomeaEvent{" + "properties=" + properties + '}';
    }

    /**
     * Return the date in joda time.
     *
     * @return the date
     */
    private DateTime getDateTime() {
        return new DateTime(getDate());
    }

    @Override
    public int compareTo(final KomeaEvent o) {
        return o.getDate().compareTo(getDate());
    }

}
