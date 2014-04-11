/**
 * 
 */

package org.komea.product.plugins.git.operations;



import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.joda.time.DateTime;
import org.junit.Test;
import org.komea.product.plugins.git.cron.GitRepoFactory;
import org.komea.product.plugins.git.utils.GitRepositoryProxy;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;



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
    
    
    @Test @Ignore
    public void testCreationRepositoryAndScan() throws Exception {
    
    
        final ScmRepositoryDefinition gitRepository = GitRepoFactory.createDummyGitRepository();
        
        GitRepoFactory.initEsperEngine();
        final GitRepositoryProxy gitRepositoryProxy =
                GitRepoFactory.newGitRepositoryProxy(gitRepository);
        gitRepositoryProxy.getScmCloner().cloneRepository();
        final Git git = gitRepositoryProxy.getGit();
        System.out.println(gitRepository.getCloneDirectory());
        
        final DateTime previousTime = new DateTime();
        previousTime.withYear(1900);
        new File(gitRepository.getCloneDirectory(), "truc.txt").createNewFile();
        git.add().addFilepattern("truc.txt").call();
        git.commit().setAll(true).setAuthor("myself", "a@a.com").setMessage("Example of message")
                .call();
        git.branchCreate().setName("new_branch").setForce(true).call();
        git.push().call();
        System.out.println(gitRepositoryProxy.getBranches());
        
        final List<IScmCommit> receivedEvents =
                gitRepositoryProxy.getAllCommitsFromABranch("new_branch", previousTime);
        
        System.out.println(receivedEvents);
        // assertEquals("Number of events sent the first time", 5, receivedEvents.size());
        // assertEquals("Second event is branch number", "scm-branch-numbers", receivedEvents.get(1)
        // .getEventType());
        // assertEquals("Third event is tag per branch", "scm-tag-perbranch-numbers", receivedEvents
        // .get(2).getEventType());
        // assertEquals("One branch is found", 1.0d, receivedEvents.get(1).getValue(), 0);
        // assertEquals("Zero tag for the branch is found", 0.0d, receivedEvents.get(2).getValue(), 0);
        // assertEquals("scm-new-commit", receivedEvents.get(3).getEventType());
        // assertEquals("scm-new-commit", receivedEvents.get(4).getEventType());
        
    }
}
