/**
 *
 */

package org.komea.product.backend.service.olap;



import java.util.Arrays;
import java.util.concurrent.Callable;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.database.model.Kpi;



/**
 * @author sleroy
 */
public class RefreshAndStoreKpiValue implements Callable<Void>
{
    
    
    private final Kpi kpi;
    
    
    
    private/**
            *
            */
    public RefreshAndStoreKpiValue(final BackupDelay _backupDelay, final Kpi kpi) {
    
    
        this.kpi = kpi;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public Void call() throws Exception {
    
    
        final FormulaID of = FormulaID.of(kpi);
        final IQuery query = engineService.getQuery(of);
        if (query == null) {
            LOGGER.error(
                    "Kpi implements a formula thought this query has not been created and registered in the query service : formula {}\n\t Queries present {}",
                    kpi.getKpiKey(), of, Arrays.toString(engineService.getQueryNames()));
            return;
        }
        
        if (_backupDelay.equals(query.getBackupDelay())) {
            LOGGER.debug("Kpi {} is backuping...", kpi.getKey());
            storeActualValueInHistory(kpi.getId(), _backupDelay);
        }
        LOGGER.debug("Kpi {} backup finished", kpi.getKey());
    }
    
}
