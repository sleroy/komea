package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.List;

public class SearchLastMeasuresDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> kpiKeys;
    private List<String> entityKeys;

    public SearchLastMeasuresDto(List<String> kpiKeys, List<String> entityKeys) {
        this.kpiKeys = kpiKeys;
        this.entityKeys = entityKeys;
    }

    public SearchLastMeasuresDto() {
    }

    public List<String> getKpiKeys() {
        return kpiKeys;
    }

    public void setKpiKeys(List<String> kpiKeys) {
        this.kpiKeys = kpiKeys;
    }

    public List<String> getEntityKeys() {
        return entityKeys;
    }

    public void setEntityKeys(List<String> entityKeys) {
        this.entityKeys = entityKeys;
    }

}
