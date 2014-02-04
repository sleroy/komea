
package org.komea.product.backend.service.business;



import java.util.List;

import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;



/**
 * THis interfae defines a entity with a kpi.
 * 
 * @author sleroy
 * @param <T>
 */
public interface IEntityWithKPI<T>
{
    
    
    int getId();
    
    
    /**
     * Returns the list of KPI of this entity
     * 
     * @param _kpiName
     * @return the Kpi.
     */
    List<Kpi> getListofKpis();
    
    
    /**
     * Returns the list of KPI of this entity
     * 
     * @param _kpiName
     * @return the list of Kpi.
     */
    Kpi getKpi(String _kpiName);
    
    
    /**
     * Returns the enitty type.
     * 
     * @return the entity type.
     */
    EntityType getType();
    
    
    T getUnderlyingObject();
}
