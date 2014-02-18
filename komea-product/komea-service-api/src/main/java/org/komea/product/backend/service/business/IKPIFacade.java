
package org.komea.product.backend.service.business;



import java.util.List;

import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;



/**
 * This interface provides more functions than the simple KPI Pojo. You may get the active value of a metric, its history and launch a
 * saving. You cannot serialize this object!
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface IKPIFacade<TEntity extends IEntity>
{
    
    
    /**
     * Returns the associated entity.
     * 
    
     * @return the associated entity */
    IEntity getEntity();
    
    
    /**
     * Returns the full history of a KPI
     * 
    
     * @return the history. */
    List<Measure> getHistory();
    
    
    /**
     * Returns the KPI
     * @return Kpi
     */
    Kpi getKPI();
    
    
    /**
     * Returns the object to obtain metric values.
     * 
    
     * @return the metric. */
    IEPMetric getMetric();
    
    
}
