
package org.komea.product.backend.service.kpi;



import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This cron performs the backup of the kpi value into an history regularly.
 */
@DisallowConcurrentExecution
public class KpiHistoryJob implements Job
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KpiHistoryJob.class);
    
    
    
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
    
    
        LOGGER.trace("Executing the cron {}", _context.getJobDetail().getKey());
        // Expected : final JobDataMap properties = new JobDataMap();
        // properties.put("entity", _entity);
        // properties.put("kpi", _kpi);
        // properties.put("service", this);
        
        final IEntity entity = (IEntity) _context.getMergedJobDataMap().get("entity");
        final Kpi kpi = (Kpi) _context.getMergedJobDataMap().get("kpi");
        final IKPIService kpiService = (IKPIService) _context.getMergedJobDataMap().get("service");
        
        kpiService.storeValueInHistory(KpiKey.ofKpiAndEntityOrNull(kpi, entity));
    }
    
}
