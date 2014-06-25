/**
 * 
 */

package org.komea.product.backend.api;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.exceptions.KPINotFoundException;



/**
 * @author sleroy
 *
 */
public interface IMeasureStorageService
{
    
    
    /**
     * Stores actual value in history.
     *
     * @param _kpiID
     *            the kpi ID
     * @param _backupDelay
     *            the backup delay.
     * @throws KPINotFoundException
     */
    
    public abstract void storeActualValueInHistory(Integer _kpiID, BackupDelay _backupDelay)
            throws KPINotFoundException;
    
}
