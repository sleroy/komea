package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.enums.EntityType;

public class SearchMeasuresDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private List<String> entityKeys = new ArrayList<String>(0);
    @NotNull
    private EntityType entityType;
    private Date fromDate;
    @NotNull
    private List<String> kpiKeys = new ArrayList<String>(0);
    private Integer nbMeasures;
    private Date toDate;

    public SearchMeasuresDto() {

    }

    public SearchMeasuresDto(
            final EntityType entityType,
            final List<String> kpiKeys,
            final List<String> entityKeys) {

        this(entityType, kpiKeys, entityKeys, null, null, null);
    }

    public SearchMeasuresDto(
            final EntityType entityType,
            final List<String> kpiKeys,
            final List<String> entityKeys,
            final Date fromDate) {

        this(entityType, kpiKeys, entityKeys, null, fromDate, null);
    }

    public SearchMeasuresDto(
            final EntityType entityType,
            final List<String> kpiKeys,
            final List<String> entityKeys,
            final Date fromDate,
            final Date toDate) {

        this(entityType, kpiKeys, entityKeys, null, fromDate, toDate);
    }

    public SearchMeasuresDto(
            final EntityType entityType,
            final List<String> kpiKeys,
            final List<String> entityKeys,
            final Integer nbMeasures) {

        this(entityType, kpiKeys, entityKeys, nbMeasures, null, null);
    }

    private SearchMeasuresDto(
            final EntityType entityType,
            final List<String> kpiKeys,
            final List<String> entityKeys,
            final Integer nbMeasures,
            final Date fromDate,
            final Date toDate) {

        this.entityType = entityType;
        this.kpiKeys = kpiKeys;
        this.entityKeys = entityKeys;
        this.nbMeasures = nbMeasures;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public List<String> getEntityKeys() {

        return entityKeys;
    }

    public EntityType getEntityType() {

        return entityType;
    }

    public Date getFromDate() {

        return fromDate;
    }

    public List<String> getKpiKeys() {

        return kpiKeys;
    }

    public Integer getNbMeasures() {

        return nbMeasures;
    }

    public Date getToDate() {

        return toDate;
    }

    /**
     * @return
     */
    @JsonIgnore
    public boolean hasFromDate() {

        return fromDate != null;
    }

    /**
     * @return
     */
    public boolean hasToDate() {

        return toDate != null;
    }

    public void setEntityKeys(final List<String> entityKeys) {

        this.entityKeys = entityKeys;
    }

    public void setEntityType(final EntityType entityType) {

        this.entityType = entityType;
    }

    public void setFromDate(final Date fromDate) {

        this.fromDate = fromDate;
    }

    public void setKpiKeys(final List<String> kpiKeys) {

        this.kpiKeys = kpiKeys;
    }

    public void setNbMeasures(final Integer nbMeasures) {

        this.nbMeasures = nbMeasures;
    }

    public void setToDate(final Date toDate) {

        this.toDate = toDate;
    }

    @Override
    public String toString() {

        return "SearchMeasuresDto{"
                + "entityType=" + entityType + ", kpiKeys=" + kpiKeys + ", entityKeys="
                + entityKeys + ", nbMeasures=" + nbMeasures + ", fromDate=" + fromDate
                + ", toDate=" + toDate + '}';
    }

}
