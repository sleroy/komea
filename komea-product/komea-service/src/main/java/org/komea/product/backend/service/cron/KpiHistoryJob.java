
package org.komea.product.backend.service.cron;



import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * This cron performs the backup of the kpi value into an history regularly.
 */
@DisallowConcurrentExecution
public class KpiHistoryJob implements Job
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-history-job");
    
    private BackupDelay         delay  = null;
    @Autowired
    private IStatisticsAPI      statisticsService;
    
    
    
    /**
     * Method execute.
     * 
     * @param _context
     *            JobExecutionContext
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        LOGGER.info("*** Backup of Kpi values into the history* **");
        
        Validate.notNull(delay);
        statisticsService.backupKpiValuesIntoHistory(delay);
    }
    
    
    public BackupDelay getDelay() {
    
    
        return delay;
    }
    
    
    public IStatisticsAPI getStatisticsService() {
    
    
        return statisticsService;
    }
    
    
    public void setDelay(final BackupDelay _delay) {
    
    
        delay = _delay;
    }
    
    
    public void setStatisticsService(final IStatisticsAPI _statisticsService) {
    
    
        statisticsService = _statisticsService;
    }
    
}
