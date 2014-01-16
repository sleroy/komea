package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.List;

public class SearchKpiAlertsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> kpiAlertTypeKeys;
    private List<String> entityKeys;

    public SearchKpiAlertsDto(List<String> kpiAlertTypeKeys, List<String> entityKeys) {
        this.kpiAlertTypeKeys = kpiAlertTypeKeys;
        this.entityKeys = entityKeys;
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

}
