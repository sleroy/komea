package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.komea.product.database.enums.EntityType;

public class SearchMeasuresDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private EntityType entityType;
    @NotNull
    private List<String> kpiKeys = new ArrayList<String>(0);
    @NotNull
    private List<String> entityKeys = new ArrayList<String>(0);
    private Integer nbMeasures;
    private Date fromDate;
    private Date toDate;

    public SearchMeasuresDto() {
    }

    public SearchMeasuresDto(EntityType entityType, List<String> kpiKeys,
            List<String> entityKeys, Integer nbMeasures) {
        this(entityType, kpiKeys, entityKeys, nbMeasures, null, null);
    }

    public SearchMeasuresDto(EntityType entityType, List<String> kpiKeys,
            List<String> entityKeys, Date fromDate) {
        this(entityType, kpiKeys, entityKeys, null, fromDate, null);
    }

    public SearchMeasuresDto(EntityType entityType, List<String> kpiKeys,
            List<String> entityKeys, Date fromDate, Date toDate) {
        this(entityType, kpiKeys, entityKeys, null, fromDate, toDate);
    }

    private SearchMeasuresDto(EntityType entityType, List<String> kpiKeys,
            List<String> entityKeys, Integer nbMeasures, Date fromDate, Date toDate) {
        this.entityType = entityType;
        this.kpiKeys = kpiKeys;
        this.entityKeys = entityKeys;
        this.nbMeasures = nbMeasures;
        this.fromDate = fromDate;
        this.toDate = toDate;
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

    public Integer getNbMeasures() {
        return nbMeasures;
    }

    public void setNbMeasures(Integer nbMeasures) {
        this.nbMeasures = nbMeasures;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    @Override
    public String toString() {
        return "SearchMeasuresDto{" + "entityType=" + entityType + ", kpiKeys="
                + kpiKeys + ", entityKeys=" + entityKeys + ", nbMeasures="
                + nbMeasures + ", fromDate=" + fromDate + ", toDate=" + toDate + '}';
    }

}
