/**
 * 
 */

package org.komea.product.backend.service.esper.stats;



import org.joda.time.DateTime;
import org.komea.product.backend.service.esper.IEventStatisticsService;
import org.komea.product.database.model.Measure;
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
@DisallowConcurrentExecution
public class EventStatsCron implements Job
{
    
    
    private static final Logger     LOGGER = LoggerFactory.getLogger(EventStatsCron.class);
    
    @Autowired
    private IEventStatisticsService service;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        LOGGER.info("Backup of alert stats...");
        final long receivedAlertsIn24LastHours = service.getReceivedAlertsIn24LastHours();
        final Measure measure = new Measure();
        measure.setDate(new DateTime().toDate());
        measure.setIdKpi(-1);
        measure.setValue(new Double(receivedAlertsIn24LastHours));
        service.putHistory(new DateTime().hourOfDay().get(), measure);
        
    }
    
    
    public IEventStatisticsService getService() {
    
    
        return service;
    }
    
    
    public void setService(final IEventStatisticsService _service) {
    
    
        service = _service;
    }
    
    
}
