/**
 * 
 */

package org.komea.product.plugins.git.cron;



import static org.mockito.Mockito.mock;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.plugins.git.cron.GitCronJob;
import org.komea.product.plugins.git.cron.GitScheduleCronJob;
import org.komea.product.plugins.git.model.GitRepo;
import org.komea.product.plugins.git.repositories.api.IGitRepository;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.quartz.JobDataMap;



/**
 * @author sleroy
 */
public class GitScheduleCronJobTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.git.cron.GitScheduleCronJob#checkIfGitRepositoryHaveJobs(org.quartz.JobExecutionContext, org.komea.product.plugins.git.repositories.api.IGitRepository, org.komea.product.backend.service.cron.ICronRegistryService)}
     * .
     */
    @Test
    public final void testCheckIfGitRepositoryHaveJobs() throws Exception {
    
    
        final GitScheduleCronJob gitScheduleCronJob = new GitScheduleCronJob();
        final IGitRepository repository = mock(IGitRepository.class);
        final ICronRegistryService registry = mock(ICronRegistryService.class);
        final JobDataMap _dataMap = mock(JobDataMap.class);
        final GitRepo gitRepo = new GitRepo();
        Mockito.when(repository.getAllRepositories())
                .thenReturn(Collections.singletonList(gitRepo));
        // We want to force association
        Mockito.when(repository.isAssociatedToCron(gitRepo)).thenReturn(false);
        gitScheduleCronJob.checkIfGitRepositoryHaveJobs(repository, registry, _dataMap);
        Mockito.verify(repository, Mockito.times(1)).initializeCronName(gitRepo);
        Mockito.verify(registry, Mockito.times(1))
                .registerCronTask(Matchers.anyString(), Matchers.anyString(),
                        Matchers.eq(GitCronJob.class), Matchers.any(JobDataMap.class));
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.git.cron.GitScheduleCronJob#prepareJobMapForCron(org.quartz.JobDataMap, org.komea.product.plugins.git.model.GitRepo)}
     * .
     */
    @Test
    public final void testPrepareJobMapForCron() throws Exception {
    
    
        final GitScheduleCronJob gitScheduleCronJob = new GitScheduleCronJob();
        final GitRepo gitRepo = new GitRepo();
        final JobDataMap parentDataMap = new JobDataMap();
        parentDataMap.put("v1", "example");
        final JobDataMap resDataMap =
                gitScheduleCronJob.prepareJobMapForCron(parentDataMap, gitRepo);
        Assert.assertTrue("Repository must be inserted", resDataMap.get("repo").equals(gitRepo));
        Assert.assertTrue("Parent key must be inserted", resDataMap.get("v1").equals("example"));
        
        
    }
    
}
