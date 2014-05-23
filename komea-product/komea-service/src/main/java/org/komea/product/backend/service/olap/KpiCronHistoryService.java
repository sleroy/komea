/**
 * 
 */

package org.komea.product.backend.service.olap;



import javax.annotation.PostConstruct;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.cron.KpiHistoryJob;
import org.komea.product.backend.service.cron.KpiValueRefresherCron;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class KpiCronHistoryService
{
    
    
    @Autowired
    private ICronRegistryService cronRegistryService;
    
    
    
    @PostConstruct
    public void initCron() {
    
    
        createCron(BackupDelay.HOUR);
        createCron(BackupDelay.DAY);
        createCron(BackupDelay.MONTH);
        createCron(BackupDelay.WEEK);
        
        // KPI Refresher
        cronRegistryService.registerCronTask("KPI_REFRESH_HISTORY_JOB", "0 0/10 * 1/1 * ? *",
                KpiValueRefresherCron.class, new JobDataMap());
        
        
    }
    
    
    private JobDataMap configFor(final BackupDelay _delay) {
    
    
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("delay", _delay);
        return jobDataMap;
    }
    
    
    private void createCron(final BackupDelay backupDelay) {
    
    
        cronRegistryService.registerCronTask("KPI_" + backupDelay.name() + "_HISTORY_JOB",
                backupDelay, KpiHistoryJob.class, configFor(backupDelay));
    }
}
