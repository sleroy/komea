
package org.komea.product.backend.service.standardkpi;



import java.util.Date;
import java.util.Random;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.alert.EventDtoBuilder;
import org.komea.product.database.dto.EventSimpleDto;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



/**
 */
@DisallowConcurrentExecution
public final class AlertJobDemo implements Job
{
    
    
    private IEventPushService esper;
    
    
    
    public AlertJobDemo() {
    
    
        super();
        
    }
    
    
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
    
    
        if (new Date().getSeconds() % 2 == 0) {
            final int random = new Random().nextInt(12);
            final EventSimpleDto event =
                    EventDtoBuilder.newAlert().message("Demo alert " + random)
                            .project("SYSTEM" + random).provided("http://komea.tocea.com/demo")
                            .eventType("demo_alert").build();
            esper.sendEventDto(event);
        }
        
    }
    
    
    public IEventPushService getEsper() {
    
    
        return esper;
    }
    
    
    public void setEsper(final IEventPushService _esper) {
    
    
        esper = _esper;
    }
}
