
package org.komea.product.backend.service.history;



import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;



/**
 */
public class HistoryKey
{
    
    
    /**
     * Method of.
     * @param _kpiID int
     * @return HistoryKey
     */
    public static HistoryKey of(final int _kpiID) {
    
    
        return new HistoryKey(_kpiID, null, 0);
    }
    
    
    /**
     * Method of.
     * @param _id int
     * @param _entity EntityType
     * @param entityID int
     * @return HistoryKey
     */
    public static HistoryKey of(final int _id, final EntityType _entity, final int entityID) {
    
    
        return new HistoryKey(_id, _entity, entityID);
    }
    
    
    /**
     * Method of.
     * @param _kpi Kpi
     * @param _entity EntityType
     * @param entityID int
     * @return HistoryKey
     */
    public static HistoryKey of(final Kpi _kpi, final EntityType _entity, final int entityID) {
    
    
        return new HistoryKey(_kpi.getId(), _entity, entityID);
    }
    
    
    /**
     * Method of.
     * @param _findKPIOrFail Kpi
     * @param _entityAssociatedToKpi IEntity
     * @return HistoryKey
     */
    public static HistoryKey of(final Kpi _findKPIOrFail, final IEntity _entityAssociatedToKpi) {
    
    
        return of(_findKPIOrFail, _entityAssociatedToKpi.entityType(),
                _entityAssociatedToKpi.getId());
    }
    
    
    
    private final int        kpiID;
    
    
    private final EntityType entityType;
    
    
    private final int        entityID;
    
    
    
    /**
     * Constructor for HistoryKey.
     * @param _kpiID int
     * @param _entity EntityType
     * @param _entityID int
     */
    private HistoryKey(final int _kpiID, final EntityType _entity, final int _entityID) {
    
    
        kpiID = _kpiID;
        entityType = _entity;
        entityID = _entityID;
        
    }
    
    
    /**
     * Method getEntityID.
     * @return int
     */
    public int getEntityID() {
    
    
        return entityID;
    }
    
    
    /**
     * Method getEntityType.
     * @return EntityType
     */
    public EntityType getEntityType() {
    
    
        return entityType;
    }
    
    
    /**
     * Method getKpiID.
     * @return int
     */
    public int getKpiID() {
    
    
        return kpiID;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "MeasureKey [kpiID=" + kpiID + "]";
    }
    
    
}
