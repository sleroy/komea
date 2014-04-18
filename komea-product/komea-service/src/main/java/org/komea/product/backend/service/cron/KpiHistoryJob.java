
package org.komea.product.backend.service.cron;



import org.komea.product.backend.service.kpi.IKpiValueService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;
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
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KpiHistoryJob.class);
    
    private IEntity             entity;
    
    private Kpi                 kpi;
    
    @Autowired
    private IKpiValueService    kpiService;
    
    
    
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
        kpiService.storeValueInHistory(KpiKey.ofKpiAndEntityOrNull(kpi, entity));
    }
    
    
    public IEntity getEntity() {
    
    
        return entity;
    }
    
    
    public Kpi getKpi() {
    
    
        return kpi;
    }
    
    
    public IKpiValueService getKpiService() {
    
    
        return kpiService;
    }
    
    
    public void setEntity(final IEntity _entity) {
    
    
        entity = _entity;
    }
    
    
    public void setKpi(final Kpi _kpi) {
    
    
        kpi = _kpi;
    }
    
    
    public void setKpiService(final IKpiValueService _kpiService) {
    
    
        kpiService = _kpiService;
    }
    
}
