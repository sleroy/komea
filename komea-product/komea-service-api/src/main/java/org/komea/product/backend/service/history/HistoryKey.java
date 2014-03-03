
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
    
    
    /**
     * Method of.
     * 
     * @param _kpiID
     *            Integer
     * @return HistoryKey
     */
    public static HistoryKey of(final Integer _kpiID) {
    
    
        return new HistoryKey(_kpiID, null, 0);
    }
    
    
    /**
     * Method of.
     * 
     * @param _id
     *            Integer
     * @param _entity
     *            EntityType
     * @param entityID
     *            Integer
     * @return HistoryKey
     */
    public static HistoryKey of(final Integer _id, final EntityKey _entityKey) {
    
    
        return new HistoryKey(_id, _entityKey);
    }
    
    
    /**
     * Method of.
     * 
     * @param _id
     *            Integer
     * @param _entity
     *            EntityType
     * @param entityID
     *            Integer
     * @return HistoryKey
     */
    public static HistoryKey of(final Integer _id, final EntityType _entity, final Integer entityID) {
    
    
        return new HistoryKey(_id, _entity, entityID);
    }
    
    
    /**
     * Method of.
     * 
     * @param _kpi
     *            Kpi
     * @param _entity
     *            EntityType
     * @param entityID
     *            Integer
     * @return HistoryKey
     */
    public static HistoryKey of(final Kpi _kpi, final EntityType _entity, final Integer entityID) {
    
    
        return new HistoryKey(_kpi.getId(), _entity, entityID);
    }
    
    
    /**
     * Method of.
     * 
     * @param _findKPIOrFail
     *            Kpi
     * @param _entityAssociatedToKpi
     *            IEntity
     * @return HistoryKey
     */
    public static HistoryKey of(final Kpi _findKPIOrFail, final IEntity _entityAssociatedToKpi) {
    
    
        return of(_findKPIOrFail, _entityAssociatedToKpi.entityType(),
                _entityAssociatedToKpi.getId());
    }
    
    
    
    private EntityKey     entityKey;
    
    private final Integer kpiID;
    
    
    
    /**
     * @param _kpiId
     * @param _entityKey
     */
    public HistoryKey(final Integer _kpiId, final EntityKey _entityKey) {
    
    
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
    
    
        return entityKey != null;
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
