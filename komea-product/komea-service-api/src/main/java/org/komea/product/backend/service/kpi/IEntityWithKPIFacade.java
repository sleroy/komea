
package org.komea.product.backend.service.kpi;



import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;



/**
 * THis interfae defines a facade that provides basic informations on a entity. Informations are the id, the underlying object and list of
 * kpis.
 * 
 * @author sleroy

 * @version $Revision: 1.0 $
 */
public interface IEntityWithKPIFacade<T extends IEntity>
{
    
    
    /**
     * Returns the entity
     * 
    
     * @return T
     */
    T getEntity();
    
    
    /**
     * Returns the list of KPI of this entity
     * 
     * @param _kpiName
    
     * @return the list of Kpi. */
    Kpi getKpi(String _kpiName);
    
    
    /**
     * Returns the list of KPI of this entity
     * 
    
    
     * @return the Kpi. */
    List<Kpi> getListofKpis();
    
    
}
