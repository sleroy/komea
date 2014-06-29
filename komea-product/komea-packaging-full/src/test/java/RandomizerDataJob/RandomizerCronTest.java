
package RandomizerDataJob;


import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

public class RandomizerCronTest extends AbstractSpringIntegrationTestCase {
    
    @Autowired
    private ICronRegistryService cronService;
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @Test
    public void testLaunchCron() throws InterruptedException {
    
        cronService.forceNow("RANDOMIZER_JOB");
        Thread.sleep(60000);
    }
}
