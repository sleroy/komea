
package org.komea.product.service.dto;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    
    
        return new KpiKey(_kpiName.getKpiKey(), _kpiName.getEntityType());
    }
    
    
    /**
     * Return a kpi key from a kpi and a potential entity.
     * 
     * @param _kpi
     * @param _entity
     * @return the kpi key.
     */
    public static KpiKey ofKpiAndEntityOrNull(final Kpi _kpi, final IEntity _entity) {
    
    
        if (_entity == null) {
            return ofKpi(_kpi);
        }
        return new KpiKey(_kpi.getKpiKey(), _entity.getEntityKey().getEntityType());
    }
    
    
    public static KpiKey ofKpiName(final String _kpiName) {
    
    
        return new KpiKey(_kpiName, null);
    }
    
    
    public static KpiKey ofKpiNameAndEntity(final String _kpiName, final IEntity _entity) {
    
    
        return new KpiKey(_kpiName, _entity.entityType());
    }
    
    
    /**
     * @param _kpiName
     * @param _entity
     * @return
     */
    public static KpiKey ofKpiNameAndEntityOrNull(final String _kpiName, final IEntity _entity) {
    
    
        if (_entity == null) {
            return ofKpiName(_kpiName);
        }
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
    
    
        return new KpiKey(_kpiName, _entityType);
    }
    
    
    
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
    
    
    public KpiKey(final String _kpiName, final EntityType _entityType) {
    
    
        super();
        kpiName = _kpiName;
        entityType = _entityType;
    }
    
    
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KpiKey other = (KpiKey) obj;
        if (entityType != other.entityType) {
            return false;
        }
        if (kpiName == null) {
            if (other.kpiName != null) {
                return false;
            }
        } else if (!kpiName.equals(other.kpiName)) {
            return false;
        }
        return true;
    }
    
    
    public EntityType getEntityType() {
    
    
        return entityType;
    }
    
    
    public String getKpiName() {
    
    
        return kpiName;
    }
    
    
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (entityType == null ? 0 : entityType.hashCode());
        result = prime * result + (kpiName == null ? 0 : kpiName.hashCode());
        return result;
    }
    
    
    public void setEntityType(final EntityType _entityType) {
    
    
        entityType = _entityType;
    }
    
    
    public void setKpiName(final String _kpiName) {
    
    
        kpiName = _kpiName;
    }
    
    
    @Override
    public String toString() {
    
    
        return "KpiKey [entityType=" + entityType + ", kpiName=" + kpiName + "]";
    }
    
}
