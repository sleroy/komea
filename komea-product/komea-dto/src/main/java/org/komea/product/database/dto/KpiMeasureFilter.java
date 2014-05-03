
package org.komea.product.database.dto;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.enums.ExtendedEntityType;



public class KpiMeasureFilter implements Serializable
{
    
    
    private static final long  serialVersionUID = 1L;
    
    @NotNull
    private List<String>       entityKeys       = new ArrayList<String>(0);
    @NotNull
    private ExtendedEntityType extendedEntityType;
    private Date               fromDate;
    private Integer            nbMeasures;
    private Date               toDate;
    
    
    
    public KpiMeasureFilter() {
    
    
    }
    
    
    public KpiMeasureFilter(final ExtendedEntityType entityType, final List<String> entityKeys) {
    
    
        this(entityType, entityKeys, null, null, null);
    }
    
    
    public KpiMeasureFilter(
            final ExtendedEntityType entityType,
            final List<String> entityKeys,
            final Date fromDate) {
    
    
        this(entityType, entityKeys, null, fromDate, null);
    }
    
    
    public KpiMeasureFilter(
            final ExtendedEntityType entityType,
            final List<String> entityKeys,
            final Date fromDate,
            final Date toDate) {
    
    
        this(entityType, entityKeys, null, fromDate, toDate);
    }
    
    
    public KpiMeasureFilter(
            final ExtendedEntityType entityType,
            final List<String> entityKeys,
            final Integer nbMeasures) {
    
    
        this(entityType, entityKeys, nbMeasures, null, null);
    }
    
    
    /**
     * @param _searchMeasuresDto
     */
    public KpiMeasureFilter(final SearchMeasuresDto _searchMeasuresDto) {
    
    
        this(_searchMeasuresDto.getExtendedEntityType(), _searchMeasuresDto.getEntityKeys(),
                _searchMeasuresDto.getNbMeasures(), _searchMeasuresDto.getFromDate(),
                _searchMeasuresDto.getToDate());
        
        
    }
    
    
    private KpiMeasureFilter(
            final ExtendedEntityType entityType,
            final List<String> entityKeys,
            final Integer nbMeasures,
            final Date fromDate,
            final Date toDate) {
    
    
        extendedEntityType = entityType;
        this.entityKeys = entityKeys;
        this.nbMeasures = nbMeasures;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    
    public List<String> getEntityKeys() {
    
    
        return entityKeys;
    }
    
    
    public ExtendedEntityType getExtendedEntityType() {
    
    
        return extendedEntityType;
    }
    
    
    public Date getFromDate() {
    
    
        return fromDate;
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
    
    
    public void setExtendedEntityType(final ExtendedEntityType entityType) {
    
    
        extendedEntityType = entityType;
    }
    
    
    public void setFromDate(final Date fromDate) {
    
    
        this.fromDate = fromDate;
    }
    
    
    public void setNbMeasures(final Integer nbMeasures) {
    
    
        this.nbMeasures = nbMeasures;
    }
    
    
    public void setToDate(final Date toDate) {
    
    
        this.toDate = toDate;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "KpiMeasureFilter [entityKeys="
                + entityKeys + ", extendedEntityType=" + extendedEntityType + ", fromDate="
                + fromDate + ", nbMeasures=" + nbMeasures + ", toDate=" + toDate + "]";
    }
    
}
