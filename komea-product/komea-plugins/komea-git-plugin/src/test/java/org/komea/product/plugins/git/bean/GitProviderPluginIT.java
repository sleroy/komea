/**
 * 
 */

package org.komea.product.plugins.git.bean;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.plugins.git.cron.GitCronJob;
import org.komea.product.plugins.git.cron.GitScheduleCronJob;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */

public class GitProviderPluginIT extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private GitProviderPlugin gitProviderPlugin;
    
    
    
    @Test
    public void testExecuteJobExecutionContext() {
    
    
        final GitRepositoryDefinition gitRepo =
                GitRepositoryDefinition.newGitRepository("DEMO1", "http://");
        
        
        final GitScheduleCronJob gitScheduleCronJob = new GitScheduleCronJob();
        
        final JobDataMap prepareJobMapForCron = gitProviderPlugin.prepareJobMapForCron();
        
        final JobDataMap prepareJobMapForCron2 =
                gitScheduleCronJob.prepareJobMapForCron(prepareJobMapForCron, gitRepo);
        
        for (final String requiredKey : GitCronJob.requiredKeys()) {
            Assert.assertTrue("provider must provide key",
                    prepareJobMapForCron2.containsKey(requiredKey));
            Assert.assertNotNull("Key " + requiredKey + " should not be null",
                    prepareJobMapForCron2.get(requiredKey));
        }
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.git.bean.GitProviderPlugin#prepareJobMapForCron()}.
     */
    @Test
    public final void testPrepareJobMapForCron() throws Exception {
    
    
        final JobDataMap prepareJobMapForCron = gitProviderPlugin.prepareJobMapForCron();
        for (final String requiredKey : GitScheduleCronJob.requiredKeys()) {
            Assert.assertTrue("provider must provide key",
                    prepareJobMapForCron.containsKey(requiredKey));
            Assert.assertNotNull("Key " + requiredKey + " should not be null",
                    prepareJobMapForCron.get(requiredKey));
        }
        
        
        for (final String requiredKey : GitCronJob.requiredKeys()) {
            Assert.assertTrue("provider must provide key",
                    prepareJobMapForCron.containsKey(requiredKey));
            Assert.assertNotNull("Key " + requiredKey + " should not be null",
                    prepareJobMapForCron.get(requiredKey));
        }
        
    }
    
}
