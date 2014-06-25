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

            LOGGER.info("Store the actual value of the kpi {} into the history",
                    kpi.getDisplayName());
            statisticsAPI.storeActualValueInHistory(kpi.getId(), backupDelay);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }


}
