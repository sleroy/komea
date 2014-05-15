/**
 * 
 */

package org.komea.product.plugins.scm.cron;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobDataMap;

import com.google.common.collect.Lists;

/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class ScmScheduleCronJobTest {

	@Mock
	private ICronRegistryService	cronRegistryService;
	@InjectMocks
	private ScmScheduleCronJob	 scmScheduleCronJob;

	/**
	 * Test method for
	 * {@link org.komea.product.plugins.scm.cron.ScmScheduleCronJob#checkIfScmRepositoriesAreAssociatedToAJob(org.komea.product.plugins.scm.api.IScmRepositoryService, org.quartz.JobDataMap)}
	 * .
	 */
	@Test
	public final void testCheckIfScmRepositoriesAreAssociatedToAJob() throws Exception {

		final IScmRepositoryService scmRepositoryService = Mockito.mock(IScmRepositoryService.class);
		final ScmRepositoryDefinition scmRepo = new ScmRepositoryDefinition();
		when(scmRepositoryService.getRepositoriesNotAssociated()).thenReturn(Lists.newArrayList(scmRepo));
		scmScheduleCronJob.checkIfScmRepositoriesAreAssociatedToAJob(scmRepositoryService, new JobDataMap());
		verify(cronRegistryService, times(1)).registerCronTask(Matchers.anyString(),
		        Matchers.eq(ScmScheduleCronJob.GIT_CRON_VALUE), Matchers.any(Class.class),
		        Matchers.any(JobDataMap.class));
		verify(scmRepositoryService, times(1)).registerCronJobOfScm(scmRepo);
	}

	/**
	 * Test method for
	 * {@link org.komea.product.plugins.scm.cron.ScmScheduleCronJob#prepareJobMapForCron(org.quartz.JobDataMap, org.komea.product.plugins.repository.model.ScmRepositoryDefinition)}
	 * .
	 */
	@Test
	public final void testPrepareJobMapForCron() throws Exception {

		final ScmRepositoryDefinition gitRepo = new ScmRepositoryDefinition();
		final JobDataMap parentDataMap = new JobDataMap();
		parentDataMap.put("V1", true);
		final JobDataMap prepareJobMapForCron = scmScheduleCronJob.prepareJobMapForCron(parentDataMap, gitRepo);
		assertEquals(gitRepo, prepareJobMapForCron.get(ScmScheduleCronJob.KEY_REPO));
		assertEquals(true, prepareJobMapForCron.get("V1"));
	}

}
