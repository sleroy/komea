
package org.komea.product.service.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;

public class KpiStringKey {
    
    /**
     * Creates a KPI.
     * 
     * @param _kpiName
     *            the kpi name
     * @return the kpi key.
     */
    public static KpiStringKey ofKpi(final Kpi _kpiName) {
    
        return new KpiStringKey(_kpiName.getEntityType(), null, _kpiName.getKpiKey());
    }
    
    public static KpiStringKey ofKpiAndEntity(final Kpi _kpiName, final IEntity _entity) {
    
        return new KpiStringKey(_entity.entityType(), _entity.getKey(), _kpiName.getKpiKey());
    }
    
    public static KpiStringKey ofKpiAndEntityDetails(final Kpi _kpiName, final EntityType _entityType, final String _entityKey) {
    
        return new KpiStringKey(_entityType, _entityKey, _kpiName.getKpiKey());
    }
    
    /**
     * Return a kpi key from a kpi and a potential entity.
     * 
     * @param _kpi
     * @param _entity
     * @return the kpi key.
     */
    public static KpiStringKey ofKpiAndEntityOrNull(final Kpi _kpi, final IEntity _entity) {
    
        if (_entity == null) {
            return ofKpi(_kpi);
        }
        return ofKpiAndEntity(_kpi, _entity);
    }
    
    public static KpiStringKey ofKpiName(final String _kpiName) {
    
        return new KpiStringKey(null, null, _kpiName);
    }
    
    public static KpiStringKey ofKpiNameAndEntity(final String _kpiName, final IEntity _entity) {
    
        return new KpiStringKey(_entity.entityType(), _entity.getKey(), _kpiName);
    }
    
    public static KpiStringKey ofKpiNameAndEntityDetails(final String _kpiName, final EntityType _entityType, final String _entityKey) {
    
        return new KpiStringKey(_entityType, _entityKey, _kpiName);
    }
    
    /**
     * Builds a kpi key from a kpi name and the entity key.
     * 
     * @param _kpiName
     *            the kpi name
     * @param _entityKey
     *            the entity key
     * @return the kpi key.
     */
    public static KpiStringKey ofKpiNameAndEntityKey(final String _kpiName, final EntityStringKey _entityKey) {
    
        return new KpiStringKey(_kpiName, _entityKey);
    }
    
    /**
     * @param _kpiName
     * @param _entity
     * @return
     */
    public static KpiStringKey ofKpiNameAndEntityOrNull(final String _kpiName, final IEntity _entity) {
    
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
    public static KpiStringKey ofKpiNameAndEntityType(final String _kpiName, final EntityType _entityType) {
    
        return new KpiStringKey(_entityType, null, _kpiName);
    }
    
    private EntityStringKey entityKey;
    
    @NotNull
    @Size(min = 0, max = 255)
    private String          kpiName;
    
    /**
     * @param _entityType
     * @param _entityID
     * @param _kpiName
     */
    
    public KpiStringKey() {
    
        super();
    }
    
    /**
     * Kpi Key
     * 
     * @param _kpiName
     *            the kpi name
     * @param _entityKey
     *            the entity key.
     */
    public KpiStringKey(final String _kpiName, final EntityStringKey _entityKey) {
    
        super();
        kpiName = _kpiName;
        entityKey = _entityKey;
    }
    
    /**
     * Builds a kpi.
     * 
     * @param _entityType
     * @param _entityID
     * @param _kpiName
     */
    private KpiStringKey(final EntityType _entityType, final String _entityKey, final String _kpiName) {
    
        super();
        entityKey = new EntityStringKey(_entityType, _entityKey);
        kpiName = _kpiName;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
        final KpiStringKey other = (KpiStringKey) obj;
        if (entityKey == null) {
            if (other.entityKey != null) {
                return false;
            }
        } else if (!entityKey.equals(other.entityKey)) {
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
    
    public EntityStringKey getEntityKey() {
    
        return entityKey;
    }
    
    public String getKpiName() {
    
        return kpiName;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (entityKey == null ? 0 : entityKey.hashCode());
        result = prime * result + (kpiName == null ? 0 : kpiName.hashCode());
        return result;
    }
    
    @JsonIgnore
    public boolean isAssociatedToEntity() {
    
        return entityKey != null && entityKey.isEntityReferenceKey();
    }
    
    public void setEntityKey(final EntityStringKey _entityKey) {
    
        entityKey = _entityKey;
    }
    
    @Override
    public String toString() {
    
        return "KpiStringKey [entityKey=" + entityKey + ", kpiName=" + kpiName + "]";
    }
    
}
