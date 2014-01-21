package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;

public class EventDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private EventType eventType;
    private Provider provider;
    private String message;
    private Map<String, String> properties;
    private String entityName;
    private Date date;

    public EventDto(EventType eventType, Provider provider, String message, Map<String, String> properties, String entityName, Date date) {
        this.eventType = eventType;
        this.provider = provider;
        this.message = message;
        this.properties = properties;
        this.entityName = entityName;
        this.date = date;
    }

    public EventDto() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

}
