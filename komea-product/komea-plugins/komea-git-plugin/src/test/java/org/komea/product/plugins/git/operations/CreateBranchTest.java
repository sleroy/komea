/**
 * 
 */

package org.komea.product.plugins.git.operations;






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
    //
    //
    // @Test
    // public void testCreationRepositoryAndScan() throws Exception {
    //
    //
    // final GitRepositoryDefinition gitRepository = GitRepoFactory.createDummyGitRepository();
    //
    // final IEventPushService eventPushEngine = GitRepoFactory.initEsperEngine();
    // final IGitClonerService gitClonerService =
    // GitRepoFactory.newGitClonerService(gitRepository);
    // final IGitCloner gitCloner =
    // gitClonerService.cloneOrRetrieveClonedRepository(gitRepository);
    // final Git git = gitCloner.getGit();
    // new File(gitRepository.getCloneDirectory(), "truc.txt").createNewFile();
    // git.add().addFilepattern("truc.txt").call();
    // git.commit().setAll(true).setAuthor("myself", "a@a.com").setMessage("Example of message")
    // .call();
    // git.branchCreate().setName("new_branch").setForce(true).call();
    // git.push().call();
    //
    //
    // final List<EventSimpleDto> receivedEvents =
    // GitRepoFactory.launchGitCron(eventPushEngine, gitRepository, gitClonerService);
    //
    // System.out.println(receivedEvents);
    // assertEquals("Number of events sent the first time", 5, receivedEvents.size());
    // assertEquals("Second event is branch number", "scm-branch-numbers", receivedEvents.get(1)
    // .getEventType());
    // assertEquals("Third event is tag per branch", "scm-tag-perbranch-numbers", receivedEvents
    // .get(2).getEventType());
    // assertEquals("One branch is found", 1.0d, receivedEvents.get(1).getValue(), 0);
    // assertEquals("Zero tag for the branch is found", 0.0d, receivedEvents.get(2).getValue(), 0);
    // assertEquals("scm-new-commit", receivedEvents.get(3).getEventType());
    // assertEquals("scm-new-commit", receivedEvents.get(4).getEventType());
    //
    // }
    
}
