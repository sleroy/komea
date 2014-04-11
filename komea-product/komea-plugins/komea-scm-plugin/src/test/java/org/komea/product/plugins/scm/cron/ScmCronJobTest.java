/**
 * 
 */

package org.komea.product.plugins.scm.cron;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryAnalysisService;
import org.komea.product.plugins.scm.api.IScmRepositoryProxyFactories;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class ScmCronJobTest
{
    
    
    @Mock
    private IScmRepositoryAnalysisService analysisService;
    
    
    @Mock
    private IEventPushService             esperEngine;
    
    
    @Mock
    private ScmRepositoryDefinition       fetch;
    
    
    @Mock
    private IPersonService                personService;
    
    
    @Mock
    private IScmRepositoryService         repository;
    
    
    @Mock
    private IScmRepositoryProxyFactories  repositoryFactory;
    @InjectMocks
    private ScmCronJob                    scmCronJob;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.scm.cron.ScmCronJob#executeScmCron()}.
     */
    @Test @Ignore
    public final void testExecuteScmCron() throws Exception {
    
    
        // TODO NOt yet implemented
        
    }
    
}
