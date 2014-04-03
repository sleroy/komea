package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.List;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.Severity;

public class SearchKpiAlertsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private EntityType entityType;
    private List<String> kpiAlertTypeKeys;
    private List<String> entityKeys;
    private Severity severityMin;
    private boolean activatedOnly;

    public SearchKpiAlertsDto(EntityType entityType, List<String> kpiAlertTypeKeys,
            List<String> entityKeys, Severity severityMin, boolean activatedOnly) {
        this.entityType = entityType;
        this.kpiAlertTypeKeys = kpiAlertTypeKeys;
        this.entityKeys = entityKeys;
        this.severityMin = severityMin;
        this.activatedOnly = activatedOnly;
    }

    public SearchKpiAlertsDto() {
    }

    public List<String> getKpiAlertTypeKeys() {
        return kpiAlertTypeKeys;
    }

    public void setKpiAlertTypeKeys(List<String> kpiAlertTypeKeys) {
        this.kpiAlertTypeKeys = kpiAlertTypeKeys;
    }

    public List<String> getEntityKeys() {
        return entityKeys;
    }

    public void setEntityKeys(List<String> entityKeys) {
        this.entityKeys = entityKeys;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Severity getSeverityMin() {
        return severityMin;
    }

    public void setSeverityMin(Severity severityMin) {
        this.severityMin = severityMin;
    }

    public boolean isActivatedOnly() {
        return activatedOnly;
    }

    public void setActivatedOnly(boolean activatedOnly) {
        this.activatedOnly = activatedOnly;
    }

}
