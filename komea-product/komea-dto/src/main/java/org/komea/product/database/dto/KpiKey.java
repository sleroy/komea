
package org.komea.product.database.dto;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;

public class KpiKey
{
    
    public static KpiKey newKpiWithEntityDetails(final Kpi _kpiName, final EntityType _entityTYpe, final int _entityID) {
    
        return new KpiKey(_entityTYpe, _entityID, _kpiName.getKpiKey());
    }
    
    public static KpiKey newKpiWithEntityDetails(final String _kpiName, final EntityType _entityTYpe, final int _entityID) {
    
        return new KpiKey(_entityTYpe, _entityID, _kpiName);
    }
    
    public static KpiKey withEntity(final Kpi _kpiName, final IEntity _entity) {
    
        return new KpiKey(_entity.entityType(), _entity.getId(), _kpiName.getKpiKey());
    }
    
    public static KpiKey withEntity(final String _kpiName, final IEntity _entity) {
    
        return new KpiKey(_entity.entityType(), _entity.getId(), _kpiName);
    }
    
    public static KpiKey withKpi(final Kpi _kpiName) {
    
        return new KpiKey(null, null, _kpiName.getKpiKey());
    }
    
    public static KpiKey withKpi(final String _kpiName) {
    
        return new KpiKey(null, null, _kpiName);
    }
    
    private EntityType entityType;
    
    private Integer    entityID;
    
    private String     kpiName;
    
    /**
     * @param _entityType
     * @param _entityID
     * @param _kpiName
     */
    
    public KpiKey() {
    
        // TODO Auto-generated KpiKey stub
    }
    
    public KpiKey(final EntityType _entityType, final Integer _entityID, final String _kpiName) {
    
        super();
        entityType = _entityType;
        entityID = _entityID;
        kpiName = _kpiName;
    }
    
    public int getEntityID() {
    
        return entityID;
    }
    
    public EntityType getEntityType() {
    
        return entityType;
    }
    
    public String getKpiName() {
    
        return kpiName;
    }
    
    @JsonIgnore
    public boolean isAssocoateWithEntity() {
    
        return entityType == null && entityID == null;
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
    
        return "KpiKey [entityType=" + entityType + ", entityID=" + entityID + ", kpiName=" + kpiName + "]";
    }
}
