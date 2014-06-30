/**
 *
 */

package org.komea.product.backend.service.cron;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.api.IMeasureStorageService;
import org.komea.product.database.model.Kpi;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
@DisallowConcurrentExecution()
public class CronRefreshAndStoreKpiValue implements Job
{
    
    
    private static final Logger    LOGGER = LoggerFactory
            .getLogger(CronRefreshAndStoreKpiValue.class);


    private BackupDelay            backupDelay;


    private Kpi                    kpi;


    @Autowired
    private IMeasureStorageService statisticsAPI;



    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {


        run();
        
    }


    /**
     * Returns the value of the field backupDelay.
     *
     * @return the backupDelay
     */
    public BackupDelay getBackupDelay() {
    
    
        return backupDelay;
    }


    /**
     * Returns the value of the field kpi.
     *
     * @return the kpi
     */
    public Kpi getKpi() {
    
    
        return kpi;
    }
    
    
    /**
     * Returns the value of the field statisticsAPI.
     *
     * @return the statisticsAPI
     */
    public IMeasureStorageService getStatisticsAPI() {


        return statisticsAPI;
    }


    /**
     * Sets the field backupDelay with the value of _backupDelay.
     *
     * @param _backupDelay
     *            the backupDelay to set
     */
    public void setBackupDelay(final BackupDelay _backupDelay) {
    
    
        backupDelay = _backupDelay;
    }
    
    
    /**
     * Sets the field kpi with the value of _kpi.
     *
     * @param _kpi
     *            the kpi to set
     */
    public void setKpi(final Kpi _kpi) {
    
    
        kpi = _kpi;
    }
    
    
    /**
     * Sets the field statisticsAPI with the value of _statisticsAPI.
     *
     * @param _statisticsAPI
     *            the statisticsAPI to set
     */
    public void setStatisticsAPI(final IMeasureStorageService _statisticsAPI) {


        statisticsAPI = _statisticsAPI;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {


        return "CronRefreshAndStoreKpiValue [\\n\\tbackupDelay="
                + backupDelay + ", \\n\\tkpi=" + kpi + ", \\n\\tstatisticsAPI=" + statisticsAPI
                + ", \\n\\tgetClass()=" + getClass() + ", \\n\\thashCode()=" + hashCode()
                + ", \\n\\ttoString()=" + super.toString() + "]";
    }
    
    
    private void run() {


        try {
            
            LOGGER.trace("Store the actual value of the kpi {} into the history",
                    kpi.getDisplayName());
            statisticsAPI.storeActualValueInHistory(kpi.getId(), backupDelay);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        
    }
    
    
}
