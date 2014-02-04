
package org.komea.product.backend.service.business;



import java.util.List;

import org.komea.product.backend.service.kpi.IEPMetric;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;



/**
 * This interface provides more functions than the simple KPI Pojo. You may get the active value of a metric, its history and launch a
 * saving. You cannot serialize this object!
 * 
 * @author sleroy
 */
public interface IKPIFacade<TEntity>
{
    
    
    /**
     * Returns the associated entity.
     * 
     * @return the associated entity
     */
    IEntityWithKPI<TEntity> getEntity();
    
    
    /**
     * Returns the full history of a KPI
     * 
     * @return the history.
     */
    List<Measure> getHistory();
    
    
    /**
     * Returns the KPI
     */
    Kpi getKPI();
    
    
    /**
     * Returns the metric.
     * 
     * @return the metric.
     */
    IEPMetric getMetric();
    
    
}
