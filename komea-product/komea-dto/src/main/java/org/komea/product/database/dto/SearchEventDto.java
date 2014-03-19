package org.komea.product.database.dto;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.Severity;

public class SearchEventDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Severity severityMin = Severity.INFO;
    private int maxEvents = 100;
    private EntityType entityType;
    @NotNull
    private List<String> entityKeys = Lists.newArrayList();
    @NotNull
    private List<String> eventTypeKeys = Lists.newArrayList();

    public SearchEventDto() {
    }

    public SearchEventDto(int maxEvents, EntityType entityType,
            List<String> entityKeys, List<String> eventTypeKeys) {
        this.maxEvents = maxEvents;
        this.entityType = entityType;
        this.entityKeys = entityKeys;
        this.eventTypeKeys = eventTypeKeys;
    }

    public SearchEventDto(EntityType entityType,
            List<String> entityKeys, List<String> eventTypeKeys) {
        this.entityType = entityType;
        this.entityKeys = entityKeys;
        this.eventTypeKeys = eventTypeKeys;
    }

    public SearchEventDto(Severity severityMin, EntityType entityType,
            List<String> entityKeys, List<String> eventTypeKeys) {
        this.severityMin = severityMin;
        this.entityType = entityType;
        this.entityKeys = entityKeys;
        this.eventTypeKeys = eventTypeKeys;
    }

    public SearchEventDto(Severity severityMin, int maxEvents, EntityType entityType,
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

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
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
                + ", entityType=" + entityType + ", entityKeys=" + entityKeys + ", eventTypeKeys=" + eventTypeKeys + '}';
    }

}
