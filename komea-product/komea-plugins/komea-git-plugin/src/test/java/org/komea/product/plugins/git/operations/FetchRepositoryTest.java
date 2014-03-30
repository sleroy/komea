/**
 * 
 */

package org.komea.product.plugins.git.operations;



import java.util.List;

import org.junit.Test;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.plugins.git.cron.GitRepoFactory;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitClonerService;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class FetchRepositoryTest
{
    
    
    /**
     * 
     */
    public FetchRepositoryTest() {
    
    
        super();
    }
    
    
    @Test
    public void testCreationRepositoryAndScan() throws Exception {
    
    
        final GitRepositoryDefinition gitRepository = GitRepoFactory.createDummyGitRepository();
        
        final IEventPushService eventPushEngine = GitRepoFactory.initEsperEngine();
        final IGitClonerService gitClonerService =
                GitRepoFactory.newGitClonerService(gitRepository);
        
        
        // First time
        final List<EventSimpleDto> firstEvents =
                GitRepoFactory.launchGitCron(eventPushEngine, gitRepository, gitClonerService);
        assertEquals("Number of events sent the first time", 2, firstEvents.size());
        assertEquals("First event is fetch event", "scm-fetch-repository", firstEvents.get(0)
                .getEventType());
        assertEquals("Second event is branch number", "scm-branch-numbers", firstEvents.get(1)
                .getEventType());
        assertEquals("Zero branch are found", 0.0d, firstEvents.get(1).getValue(), 0);
        
        System.out.println(firstEvents.size());
        System.out.println(firstEvents);
        
        
    }
    
}
