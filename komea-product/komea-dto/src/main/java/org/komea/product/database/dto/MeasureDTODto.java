
package org.komea.product.database.dto;


import java.io.Serializable;
import java.util.List;

import org.komea.product.database.enums.Severity;

import com.google.common.collect.Lists;

public class MeasureDTODto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Severity          severityMin;
    private List<String>      entityKeys;
    private List<String>      eventTypeKeys;
    
    public MeasureDTODto(final Severity severityMin, final List<String> entityKeys, final List<String> eventTypeKeys) {
    
        this.severityMin = severityMin;
        this.entityKeys = entityKeys;
        this.eventTypeKeys = eventTypeKeys;
    }
    
    public MeasureDTODto() {
    
        entityKeys = Lists.newArrayList();
        eventTypeKeys = Lists.newArrayList();
    }
    
    public Severity getSeverityMin() {
    
        return severityMin;
    }
    
    public void setSeverityMin(final Severity severityMin) {
    
        this.severityMin = severityMin;
    }
    
    public List<String> getEntityKeys() {
    
        return entityKeys;
    }
    
    public void setEntityKeys(final List<String> entityKeys) {
    
        this.entityKeys = entityKeys;
    }
    
    public List<String> getEventTypeKeys() {
    
        return eventTypeKeys;
    }
    
    public void setEventTypeKeys(final List<String> eventTypeKeys) {
    
        this.eventTypeKeys = eventTypeKeys;
    }
    
}
