
package org.komea.product.backend.kpi;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.service.business.IEntityWithKPI;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;



/**
 * Builds a EntityWithKPI with KPI.
 * 
 * @author sleroy
 * @param <T>
 */
public class EntityWithKPI<T> implements IEntityWithKPI<T>
{
    
    
    private final int  id;
    
    
    private final T    entity;
    
    private List<Kpi>  kpis       = new ArrayList<Kpi>();
    
    private EntityType entityType = null;
    
    
    
    /**
     * Builds an entity wrapper.
     * 
     * @param _id
     * @param _entity
     * @param _kpis
     * @param _entityType
     */
    public EntityWithKPI(
            final int _id,
            final T _entity,
            final List<Kpi> _kpis,
            final EntityType _entityType) {
    
    
        super();
        id = _id;
        entity = _entity;
        kpis = _kpis;
        entityType = _entityType;
    }
    
    
    public T getEntity() {
    
    
        return entity;
    }
    
    
    @Override
    public int getId() {
    
    
        return id;
    }
    
    
    @Override
    public List<Kpi> getListofKpis() {
    
    
        return kpis;
    }
    
    
    @Override
    public Kpi getKpi(final String _kpiName) {
    
    
        for (final Kpi kpi : kpis) {
            if (_kpiName.equals(kpi.getKpiKey())) { return kpi; }
        }
        
        return null;
    }
    
    
    @Override
    public EntityType getType() {
    
    
        return entityType;
    }
    
    
    @Override
    public T getUnderlyingObject() {
    
    
        return entity;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "EntityWithKPI [id="
                + id + ", " + (entity != null ? "entity=" + entity + ", " : "")
                + (kpis != null ? "kpis=" + kpis + ", " : "")
                + (entityType != null ? "entityType=" + entityType : "") + "]";
    }
    
    
}
