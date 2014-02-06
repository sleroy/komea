
package org.komea.product.backend.service.demodata;



import java.util.Random;

import org.komea.product.backend.service.esper.IAlertPushService;
import org.komea.product.database.alert.Alert;
import org.komea.product.database.alert.AlertBuilder;
import org.komea.product.database.alert.enums.Criticity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



public final class AlertJobDemo implements Job
{
    
    
    public AlertJobDemo() {
    
    
        super();
        
    }
    
    
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        final Alert alert =
                AlertBuilder.newAlert().category("SCM").criticity(Criticity.BLOCKING)
                        .fullMessage("Demo Alert").message("Demo alert").project("SYSTEM")
                        .provided("DEMO" + new Random().nextInt(12)).type("DemoAlert").build();
        ((IAlertPushService) _context.getMergedJobDataMap().get("esper")).sendEvent(alert);
        
        
    }
}
