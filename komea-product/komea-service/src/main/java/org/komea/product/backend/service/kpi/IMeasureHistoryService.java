
package org.komea.product.backend.service.kpi;



import org.komea.product.backend.service.history.IHistoryService;
import org.komea.product.database.model.Kpi;



public interface IMeasureHistoryService extends IHistoryService
{
    
    
    /**
     * Builds an history purge action from a kpi.
     * 
     * @param _kpi
     *            the kpi
     * @return the history purge action.
     */
    IHistoryPurgeAction buildHistoryPurgeAction(Kpi _kpi);
    
    
}
