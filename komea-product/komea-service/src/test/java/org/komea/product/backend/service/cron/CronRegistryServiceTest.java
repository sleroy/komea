/**
 * 
 */

package org.komea.product.backend.service.cron;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class CronRegistryServiceTest
{
    
    
    @Mock
    private ApplicationContext  applicationContext;
    @InjectMocks
    private CronRegistryService cronRegistryService;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.cron.CronRegistryService#disableCronTask(java.lang.String)}.
     */
    @Test
    public final void testDisableCronTask() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.cron.CronRegistryService#enableCronTask(java.lang.String)}.
     */
    @Test
    public final void testEnableCronTask() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.cron.CronRegistryService#existCron(java.lang.String)}.
     */
    @Test
    public final void testExistCron() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.cron.CronRegistryService#forceNow(java.lang.String)}.
     */
    @Test
    public final void testForceNow() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.cron.CronRegistryService#getCronTasks()}.
     */
    @Test
    public final void testGetCronTasks() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.cron.CronRegistryService#registerCronTask(java.lang.String, java.lang.String, java.lang.Class, org.quartz.JobDataMap)}
     * .
     */
    @Test
    public final void testRegisterCronTask() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.cron.CronRegistryService#removeCronTask(java.lang.String)}.
     */
    @Test
    public final void testRemoveCronTask() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.cron.CronRegistryService#updateCronFrequency(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testUpdateCronFrequency() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
