
package org.komea.product.plugins.git.utils;



import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.git.model.GitRepo;



public class GitEventFactory
{
    
    
    /**
     * 
     */
    private static final String GIT_PROVIDER = "/git-provider";
    
    
    
    public EventSimpleDto sendBranchNumbers(final GitRepo _repo, final int _number) {
    
    
        final EventSimpleDto event = initializeGitEvent(_repo);
        event.setEventType("scm-branch-numbers");
        event.setMessage("Git Repository " + _repo.getRepoName() + " has " + _number + " branches");
        event.setValue(_number);
        event.setUrl(_repo.toString());
        return event;
    }
    
    
    public EventSimpleDto sendCustomerBranches(final GitRepo _repo, final int _number) {
    
    
        final EventSimpleDto event = initializeGitEvent(_repo);
        event.setEventType("scm-customer-branch-numbers");
        event.setMessage("Git Repository "
                + _repo.getRepoName() + " has " + _number + " customer branches");
        event.setValue(_number);
        return event;
    }
    
    
    public EventSimpleDto sendCustomerTags(final GitRepo _repo, final int _number) {
    
    
        final EventSimpleDto event = initializeGitEvent(_repo);
        event.setEventType("scm-customer-tag-numbers");
        event.setMessage("Git Repository "
                + _repo.getRepoName() + " has " + _number + " customer tags");
        event.setValue(_number);
        return event;
    }
    
    
    public EventSimpleDto sendFetchRepository(final GitRepo _repo) {
    
    
        final EventSimpleDto event = initializeGitEvent(_repo);
        event.setEventType("scm-fetch-repository");
        event.setMessage("Git Repository " + _repo.getRepoName() + " is controlling...");
        return event;
    }
    
    
    public EventSimpleDto sendGitFetchFailed(final GitRepo _repo) {
    
    
        final EventSimpleDto event = initializeGitEvent(_repo);
        event.setEventType("scm-fetch-failed");
        event.setMessage("Git Repository " + _repo.getRepoName() + " could not be fetch.");
        return event;
    }
    
    
    public EventSimpleDto sendNewCommit(
            final GitRepo _gitRepo,
            final String _message,
            final Person _person,
            final String _revision) {
    
    
        final EventSimpleDto event = initializeGitEvent(_gitRepo);
        event.setEventType("scm-new-commit");
        event.setMessage(_gitRepo.getRepoName()
                + " : new commit : " + _message + " from " + _person.getFullName());
        event.setPerson(_person.getLogin());
        event.getProperties().put("rev", _revision);
        return event;
    }
    
    
    public EventSimpleDto sendNumberTagPerBranch(
            final GitRepo _repo,
            final int _number,
            final String _branchName) {
    
    
        final EventSimpleDto event = initializeGitEvent(_repo);
        event.setEventType("scm-tag-perbranch-numbers");
        event.setMessage("Git Repository "
                + _repo.getRepoName() + " has " + _number + " tags for the branch " + _branchName);
        event.setValue(_number);
        return event;
    }
    
    
    private EventSimpleDto initializeGitEvent(final GitRepo _repo) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(GIT_PROVIDER);
        event.setProject(_repo.getProjectAssociated());
        return event;
    }
}
