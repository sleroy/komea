/**
 *
 */

package org.komea.product.backend.service.olap;



import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.stereotype.Service;



/**
 * @author sleroy
 */
@Service
public class KpiRefreshingScheduler
{
    
    
    private final ExecutorService   executorService;
    private BlockingQueue<Runnable> workQueue;
    
    
    
    private public KpiRefreshingScheduler() {
    
    
        executorService =
                new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                        workQueue);
        
    }
    
}
