
package org.komea.product.service.dto;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;



public class KpiKey
{
    
    
    /**
     * Creates a KPI.
     * 
     * @param _kpiName
     *            the kpi name
     * @return the kpi key.
     */
    public static KpiKey ofKpi(final Kpi _kpiName) {
    
    
        return new KpiKey(_kpiName.getEntityType(), _kpiName.getEntityID(), _kpiName.getKpiKey());
    }
    
    
    public static KpiKey ofKpiAndEntity(final Kpi _kpiName, final IEntity _entity) {
    
    
        return new KpiKey(_entity.entityType(), _entity.getId(), _kpiName.getKpiKey());
    }
    
    
    public static KpiKey ofKpiAndEntityDetails(
            final Kpi _kpiName,
            final EntityType _entityType,
            final int _entityID) {
    
    
        return new KpiKey(_entityType, _entityID, _kpiName.getKpiKey());
    }
    
    
    /**
     * Return a kpi key from a kpi and a potential entity.
     * 
     * @param _kpi
     * @param _entity
     * @return the kpi key.
     */
    public static KpiKey ofKpiAndEntityOrNull(final Kpi _kpi, final IEntity _entity) {
    
    
        if (_entity == null) { return ofKpi(_kpi); }
        return ofKpiAndEntity(_kpi, _entity);
    }
    
    
    public static KpiKey ofKpiName(final String _kpiName) {
    
    
        return new KpiKey(null, null, _kpiName);
    }
    
    
    public static KpiKey ofKpiNameAndEntity(final String _kpiName, final IEntity _entity) {
    
    
        return new KpiKey(_entity.entityType(), _entity.getId(), _kpiName);
    }
    
    
    public static KpiKey ofKpiNameAndEntityDetails(
            final String _kpiName,
            final EntityType _entityTYpe,
            final int _entityID) {
    
    
        return new KpiKey(_entityTYpe, _entityID, _kpiName);
    }
    
    
    /**
     * @param _kpiName
     * @param _entity
     * @return
     */
    public static KpiKey ofKpiNameAndEntityOrNull(final String _kpiName, final IEntity _entity) {
    
    
        if (_entity == null) { return ofKpiName(_kpiName); }
        return ofKpiNameAndEntity(_kpiName, _entity);
    }
    
    
    /**
     * Creates a new KpiKey referencing a GLOBAL KPI working for a given entityType.
     * 
     * @param _kpiName
     *            the kpi name
     * @param _entityType
     *            the entity type
     * @return the kpi Key.
     */
    public static KpiKey ofKpiNameAndEntityType(final String _kpiName, final EntityType _entityType) {
    
    
        return new KpiKey(_entityType, null, _kpiName);
    }
    
    
    
    private Integer    entityID;
    
    
    private EntityType entityType;
    
    
    @NotNull
    @Size(min = 0, max = 255)
    private String     kpiName;
    
    
    
    /**
     * @param _entityType
     * @param _entityID
     * @param _kpiName
     */
    
    public KpiKey() {
    
    
        super();
    }
    
    
    public KpiKey(final EntityType _entityType, final Integer _entityID, final String _kpiName) {
    
    
        super();
        entityType = _entityType;
        entityID = _entityID;
        kpiName = _kpiName;
    }
    
    
    public Integer getEntityID() {
    
    
        return entityID;
    }
    
    
    public EntityType getEntityType() {
    
    
        return entityType;
    }
    
    
    public String getKpiName() {
    
    
        return kpiName;
    }
    
    
    public void setEntityID(final Integer _entityID) {
    
    
        entityID = _entityID;
    }
    
    
    public void setEntityType(final EntityType _entityType) {
    
    
        entityType = _entityType;
    }
    
    
    public void setKpiName(final String _kpiName) {
    
    
        kpiName = _kpiName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "KpiKey [entityType="
                + entityType + ", entityID=" + entityID + ", kpiName=" + kpiName + "]";
    }
    
    
    @JsonIgnore
    public boolean verifiyIfIsAssociatedToEntity() {
    
    
        return entityType != null && entityID != null;
    }
}
