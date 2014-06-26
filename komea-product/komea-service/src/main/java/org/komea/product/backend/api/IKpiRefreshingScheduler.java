/**
 * 
 */

package org.komea.product.backend.api;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 *
 */
public interface IKpiRefreshingScheduler
{
    
    
    /**
     * Removes the cron associated to a kpi.
     *
     * @param _kpiName
     *            the kpi.
     */
    public abstract void deleteCron(Kpi _kpiName);
    
    
    /**
     * Submit a task to refresh a kpi value.
     *
     * @param _kpi
     *            the kpi object
     */
    public abstract void submitKpiRefresh(Kpi _kpi, BackupDelay _backupDelay);
    
    
    /**
     * @param _kpi
     */
    public abstract void triggerKpiCron(Kpi _kpi);
    
}
