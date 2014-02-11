
package org.komea.product.backend.service.demodata;


import java.util.Date;
import java.util.Random;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.alert.EventDtoBuilder;
import org.komea.product.database.dto.EventSimpleDto;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public final class AlertJobDemo implements Job {
    
    public AlertJobDemo() {
    
        super();
        
    }
    
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
        if (new Date().getSeconds() % 2 == 0) {
            int random = new Random().nextInt(12);
            final EventSimpleDto event = EventDtoBuilder.newAlert().message("Demo alert " + random).project("SYSTEM" + random)
                    .provided("http://komea.tocea.com/demo").eventType("demo_alert").build();
            ((IEventPushService) _context.getMergedJobDataMap().get("esper")).sendEventDto(event);
        }
        
    }
}
