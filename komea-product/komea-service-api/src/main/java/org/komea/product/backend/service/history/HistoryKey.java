
package org.komea.product.backend.service.history;



import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.EntityKey;



/**
 */
public class HistoryKey
{
    
    
    public static HistoryKey of(final Integer _kpiID) {
    
    
        return new HistoryKey(_kpiID, null, 0);
    }
    
    
    public static HistoryKey of(final Integer _id, final EntityKey _entityKey) {
    
    
        return new HistoryKey(_id, _entityKey);
    }
    
    
    public static HistoryKey of(final Integer _id, final EntityType _entity, final Integer entityID) {
    
    
        return new HistoryKey(_id, _entity, entityID);
    }
    
    
    public static HistoryKey of(final Kpi _kpi) {
    
    
        return new HistoryKey(_kpi.getId(), null, 0);
    }
    
    
    public static HistoryKey of(final Kpi _findKPI, final EntityKey _key) {
    
    
        return new HistoryKey(_findKPI.getId(), _key);
    }
    
    
    public static HistoryKey of(final Kpi _kpi, final EntityType _entity, final Integer entityID) {
    
    
        return new HistoryKey(_kpi.getId(), _entity, entityID);
    }
    
    
    public static HistoryKey of(final Kpi _findKPIOrFail, final IEntity _entityAssociatedToKpi) {
    
    
        return of(_findKPIOrFail, _entityAssociatedToKpi.entityType(),
                _entityAssociatedToKpi.getId());
    }
    
    
    
    private EntityKey     entityKey;
    
    
    private final Integer kpiID;
    
    
    
    public HistoryKey(final Integer _kpiId, final EntityKey _entityKey) {
    
    
        super();
        
        kpiID = _kpiId;
        entityKey = _entityKey;
        
        
    }
    
    
    /**
     * Constructor for HistoryKey.
     * 
     * @param _kpiID
     *            Integer
     * @param _entity
     *            entity type.
     * @param _entityID
     *            entity id
     */
    private HistoryKey(final Integer _kpiID, final EntityType _entity, final Integer _entityID) {
    
    
        kpiID = _kpiID;
        entityKey = EntityKey.of(_entity, _entityID);
        
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
        final HistoryKey other = (HistoryKey) obj;
        if (entityKey == null) {
            if (other.entityKey != null) {
                return false;
            }
        } else if (!entityKey.equals(other.entityKey)) {
            return false;
        }
        if (kpiID == null) {
            if (other.kpiID != null) {
                return false;
            }
        } else if (!kpiID.equals(other.kpiID)) {
            return false;
        }
        return true;
    }
    
    
    public EntityKey getEntityKey() {
    
    
        return entityKey;
    }
    
    
    /**
     * Method getKpiID.
     * 
     * @return Integer
     */
    public Integer getKpiID() {
    
    
        return kpiID;
    }
    
    
    /**
     * Tests if the history key has a reference to a specific entity.
     * 
     * @return true
     */
    @JsonIgnore
    public boolean hasEntityReference() {
    
    
        return entityKey != null && entityKey.isEntityReferenceKey();
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
        result = prime * result + (kpiID == null ? 0 : kpiID.hashCode());
        return result;
    }
    
    
    public void setEntityKey(final EntityKey _entityKey) {
    
    
        entityKey = _entityKey;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "HistoryKey [entityKey=" + entityKey + ", kpiID=" + kpiID + "]";
    }
    
    
}
