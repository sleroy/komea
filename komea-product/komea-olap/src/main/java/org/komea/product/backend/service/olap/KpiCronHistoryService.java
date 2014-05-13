/**
 * 
 */

package org.komea.product.backend.service.olap;



import javax.annotation.PostConstruct;

import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.cron.KpiHistoryJob;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class KpiCronHistoryService
{
    
    
    private static final String  KPI_HISTORY_INTERVAL = "0 0/60 * * * ?";
    
    @Autowired
    private ICronRegistryService cronRegistryService;
    
    
    
    @PostConstruct
    public void initCron() {
    
    
        cronRegistryService.registerCronTask("KPI_HISTORY_JOB", KPI_HISTORY_INTERVAL,
                KpiHistoryJob.class, new JobDataMap());
    }
}
