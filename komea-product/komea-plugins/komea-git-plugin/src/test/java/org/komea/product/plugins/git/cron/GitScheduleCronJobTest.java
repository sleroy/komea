/**
 * 
 */

package org.komea.product.plugins.git.cron;






/**
 * @author sleroy
 */
public class GitScheduleCronJobTest
{
    //
    //
    // /**
    // * Test method for
    // * {@link org.komea.product.plugins.git.cron.GitScheduleCronJob#checkIfGitRepositoryHaveJobs(org.quartz.JobExecutionContext,
    // org.komea.product.plugins.git.repositories.api.IGitRepositoryService, org.komea.product.backend.service.cron.ICronRegistryService)}
    // * .
    // */
    // @Test
    // public final void testCheckIfGitRepositoryHaveJobs() throws Exception {
    //
    //
    // final ScmScheduleCronJob gitScheduleCronJob = new ScmScheduleCronJob();
    // final IGitRepositoryService repository = mock(IGitRepositoryService.class);
    // final ICronRegistryService registry = mock(ICronRegistryService.class);
    // final JobDataMap _dataMap = mock(JobDataMap.class);
    // final GitRepositoryDefinition gitRepo =
    // GitRepositoryDefinition.newGitRepository("DEMO", "http://");
    // Mockito.when(repository.getAllRepositories())
    // .thenReturn(Collections.singletonList(gitRepo));
    // // We want to force association
    // Mockito.when(repository.isAssociatedToCron(gitRepo)).thenReturn(false);
    // gitScheduleCronJob.checkIfScmRepositoriesAreAssociatedToAJob(repository, _dataMap);
    //
    // Mockito.verify(repository, Mockito.times(1)).initializeCronName(gitRepo);
    // Mockito.verify(registry, Mockito.times(1))
    // .registerCronTask(Matchers.anyString(), Matchers.anyString(),
    // Matchers.eq(ScmCronJob.class), Matchers.any(JobDataMap.class));
    // }
    //
    //
    // /**
    // * Test method for
    // * {@link org.komea.product.plugins.git.cron.GitScheduleCronJob#prepareJobMapForCron(org.quartz.JobDataMap,
    // org.komea.product.plugins.repository.model.GitRepositoryDefinition)}
    // * .
    // */
    // @Test
    // public final void testPrepareJobMapForCron() throws Exception {
    //
    //
    // final ScmScheduleCronJob gitScheduleCronJob = new ScmScheduleCronJob();
    // final GitRepositoryDefinition gitRepo =
    // GitRepositoryDefinition.newGitRepository("DEMO", "http://");
    // final JobDataMap parentDataMap = new JobDataMap();
    // parentDataMap.put("v1", "example");
    // final JobDataMap resDataMap =
    // gitScheduleCronJob.prepareJobMapForCron(parentDataMap, gitRepo);
    // Assert.assertTrue("Repository must be inserted", resDataMap.get("repo").equals(gitRepo));
    // Assert.assertTrue("Parent key must be inserted", resDataMap.get("v1").equals("example"));
    //
    //
    // }
    
}
