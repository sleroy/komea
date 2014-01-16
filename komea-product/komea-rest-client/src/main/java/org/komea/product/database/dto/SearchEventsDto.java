package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.List;
import org.komea.product.database.enums.Severity;

public class SearchEventsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Severity severityMin;
    private List<String> entityKeys;
    private List<String> eventTypeKeys;

    public SearchEventsDto(Severity severityMin, List<String> entityKeys, List<String> eventTypeKeys) {
        this.severityMin = severityMin;
        this.entityKeys = entityKeys;
        this.eventTypeKeys = eventTypeKeys;
    }

    public SearchEventsDto() {
    }

    public Severity getSeverityMin() {
        return severityMin;
    }

    public void setSeverityMin(Severity severityMin) {
        this.severityMin = severityMin;
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

}
