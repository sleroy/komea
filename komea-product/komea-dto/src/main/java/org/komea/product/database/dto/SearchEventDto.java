package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.enums.Severity;

import com.google.common.collect.Lists;

public class SearchEventDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Severity severityMin = Severity.INFO;
    private int maxEvents = 100;
    private ExtendedEntityType entityType;
    @NotNull
    private List<String> entityKeys = Lists.newArrayList();
    @NotNull
    private List<String> eventTypeKeys = Lists.newArrayList();

    public SearchEventDto() {
    }

    public SearchEventDto(int maxEvents, ExtendedEntityType entityType,
            List<String> entityKeys, List<String> eventTypeKeys) {
        this.maxEvents = maxEvents;
        this.entityType = entityType;
        this.entityKeys = entityKeys;
        this.eventTypeKeys = eventTypeKeys;
    }

    public SearchEventDto(ExtendedEntityType entityType,
            List<String> entityKeys, List<String> eventTypeKeys) {
        this.entityType = entityType;
        this.entityKeys = entityKeys;
        this.eventTypeKeys = eventTypeKeys;
    }

    public SearchEventDto(Severity severityMin, ExtendedEntityType entityType,
            List<String> entityKeys, List<String> eventTypeKeys) {
        this.severityMin = severityMin;
        this.entityType = entityType;
        this.entityKeys = entityKeys;
        this.eventTypeKeys = eventTypeKeys;
    }

    public SearchEventDto(Severity severityMin, int maxEvents, ExtendedEntityType entityType,
            List<String> entityKeys, List<String> eventTypeKeys) {
        this.severityMin = severityMin;
        this.maxEvents = maxEvents;
        this.entityType = entityType;
        this.entityKeys = entityKeys;
        this.eventTypeKeys = eventTypeKeys;
    }

    public Severity getSeverityMin() {
        return severityMin;
    }

    public void setSeverityMin(Severity severityMin) {
        this.severityMin = severityMin;
    }

    public int getMaxEvents() {
        return maxEvents;
    }

    public void setMaxEvents(int maxEvents) {
        this.maxEvents = maxEvents;
    }

    public ExtendedEntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(ExtendedEntityType entityType) {
        this.entityType = entityType;
    }

    public List<String> getEntityKeys() {
        return entityKeys;
    }

    public void setEntityKeys(List<String> entityKeys) {
        this.entityKeys = entityKeys;
    }

    public List<String> getEventTypeKeys() {
        return eventTypeKeys;
    }

    public void setEventTypeKeys(List<String> eventTypeKeys) {
        this.eventTypeKeys = eventTypeKeys;
    }

    @Override
    public String toString() {
        return "SearchEventDto{" + "severityMin=" + severityMin + ", maxEvents=" + maxEvents
                + ", extendedEntityType=" + entityType + ", entityKeys=" + entityKeys + ", eventTypeKeys=" + eventTypeKeys + '}';
    }

}
