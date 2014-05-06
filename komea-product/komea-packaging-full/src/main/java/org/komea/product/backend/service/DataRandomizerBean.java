/**
 * 
 */

package org.komea.product.backend.service;



import javax.annotation.PostConstruct;

import org.komea.product.backend.service.cron.CronRegistryService;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class DataRandomizerBean
{
    
    
    @Autowired
    private CronRegistryService cronRegistryService;
    
    
    
    @PostConstruct
    public void init() {
    
    
        cronRegistryService.registerCronTask("RANDOM_DATA", "0 0/1   * * * ?",
                RandomizerDataJob.class, new JobDataMap());
        
    }
    
    
}
