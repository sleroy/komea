package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SearchHistoricalMeasuresDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> kpiKeys;
    private List<String> entityKeys;
    private Date start;
    private Date end;
    private Integer number;

    public SearchHistoricalMeasuresDto(List<String> kpiKeys, List<String> entityKeys, Date start, Date end, Integer number) {
        this.kpiKeys = kpiKeys;
        this.entityKeys = entityKeys;
        this.start = start;
        this.end = end;
        this.number = number;
    }

    public SearchHistoricalMeasuresDto() {
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
