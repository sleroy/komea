/**
 * 
 */

package org.komea.product.plugins.git.operations;



import java.util.List;

import org.apache.wicket.util.file.File;
import org.eclipse.jgit.api.Git;
import org.junit.Test;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.plugins.git.cron.GitRepoFactory;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitCloner;
import org.komea.product.plugins.git.repositories.api.IGitClonerService;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class CreateBranchTest
{
    
    
    /**
     * 
     */
    public CreateBranchTest() {
    
    
        super();
    }
    
    
    @Test
    public void testCreationRepositoryAndScan() throws Exception {
    
    
        final GitRepositoryDefinition gitRepository = GitRepoFactory.createDummyGitRepository();
        
        final IEventPushService eventPushEngine = GitRepoFactory.initEsperEngine();
        final IGitClonerService gitClonerService =
                GitRepoFactory.newGitClonerService(gitRepository);
        final IGitCloner gitCloner = gitClonerService.getOrCreate(gitRepository);
        final Git git = gitCloner.getGit();
        new File(gitRepository.getCloneDirectory(), "truc.txt").createNewFile();
        git.add().addFilepattern("truc.txt").call();
        git.commit().setAll(true).setAuthor("myself", "a@a.com").setMessage("Example of message")
                .call();
        git.branchCreate().setName("new_branch").setForce(true).call();
        
        
        final List<EventSimpleDto> firstEvents =
                GitRepoFactory.launchGitCron(eventPushEngine, gitRepository, gitClonerService);
        assertEquals("Number of events sent the first time", 2, firstEvents.size());
        assertEquals("Second event is branch number", "scm-branch-numbers", firstEvents.get(1)
                .getEventType());
        assertEquals("One branch is found", 1.0d, firstEvents.get(1).getValue(), 0);
        
        System.out.println(firstEvents.size());
        System.out.println(firstEvents);
        
        
    }
    
}
