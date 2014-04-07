
package org.komea.product.plugins.scm;



import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.plugin.IScmEventFactory;



public class ScmEventFactory implements IScmEventFactory
{
    
    
    private final ScmRepositoryDefinition scmRepositoryDefinition;
    
    
    
    /**
 * 
 */
    public ScmEventFactory(final ScmRepositoryDefinition _scmRepositoryDefinition) {
    
    
        super();
        scmRepositoryDefinition = _scmRepositoryDefinition;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.plugins.scm.IScmEventFactory#sendBranchAssociated(java.lang.String)
     */
    @Override
    public EventSimpleDto sendBranchAssociated(final String _branchName) {
    
    
        final EventSimpleDto event = initializeScmEvent();
        event.setEventType("scm-fetch-failed");
        event.setProject(scmRepositoryDefinition.getProjectAssociated(_branchName));
        event.setMessage("Git Repository "
                + scmRepositoryDefinition.getRepoName() + " could not be fetch.");
        return event;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.plugins.scm.IScmEventFactory#sendBranchNumbers(int)
     */
    @Override
    public EventSimpleDto sendBranchNumbers(final int _number) {
    
    
        final EventSimpleDto event = initializeScmEvent();
        event.setEventType("scm-branch-numbers");
        event.setMessage("Git Repository "
                + scmRepositoryDefinition.getRepoName() + " has " + _number + " branches");
        event.setValue(_number);
        event.setUrl(scmRepositoryDefinition.toString());
        return event;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.plugins.scm.IScmEventFactory#sendCustomerBranches(int)
     */
    @Override
    public EventSimpleDto sendCustomerBranches(
    
    final int _number) {
    
    
        final EventSimpleDto event = initializeScmEvent();
        event.setEventType("scm-customer-branch-numbers");
        event.setMessage("Git Repository "
                + scmRepositoryDefinition.getRepoName() + " has " + _number + " customer branches");
        event.setValue(_number);
        return event;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.plugins.scm.IScmEventFactory#sendCustomerTags(int)
     */
    @Override
    public EventSimpleDto sendCustomerTags(final int _number) {
    
    
        final EventSimpleDto event = initializeScmEvent();
        event.setEventType("scm-customer-tag-numbers");
        event.setMessage("Git Repository "
                + scmRepositoryDefinition.getRepoName() + " has " + _number + " customer tags");
        event.setValue(_number);
        return event;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.plugins.scm.IScmEventFactory#sendFetchRepository()
     */
    @Override
    public EventSimpleDto sendFetchRepository() {
    
    
        final EventSimpleDto event = initializeScmEvent();
        event.setEventType("scm-fetch-repository");
        event.setMessage("Git Repository "
                + scmRepositoryDefinition.getRepoName() + " is controlling...");
        return event;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.plugins.scm.IScmEventFactory#sendGitFetchFailed()
     */
    @Override
    public EventSimpleDto sendFetchFailed() {
    
    
        final EventSimpleDto event = initializeScmEvent();
        event.setEventType("scm-fetch-failed");
        event.setMessage("Git Repository "
                + scmRepositoryDefinition.getRepoName() + " could not be fetch.");
        return event;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.plugins.scm.IScmEventFactory#sendNewCommit(java.lang.String, org.komea.product.database.model.Person, java.lang.String)
     */
    @Override
    public EventSimpleDto sendNewCommit(
            final String _message,
            final Person _person,
            final String _revision) {
    
    
        final EventSimpleDto event = initializeScmEvent();
        event.setEventType("scm-new-commit");
        event.setMessage(scmRepositoryDefinition.getRepoName()
                + " : new commit : " + _message + " from " + _person.getFullName());
        event.setPerson(_person.getLogin());
        event.getProperties().put("rev", _revision);
        return event;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.plugins.scm.IScmEventFactory#sendNumberTagPerBranch(int, java.lang.String)
     */
    @Override
    public EventSimpleDto sendNumberTagPerBranch(final int _number, final String _branchName) {
    
    
        final EventSimpleDto event = initializeScmEvent();
        event.setEventType("scm-tag-perbranch-numbers");
        event.setMessage("Git Repository "
                + scmRepositoryDefinition.getRepoName() + " has " + _number
                + " tags for the branch " + _branchName);
        event.setValue(_number);
        return event;
    }
    
    
    private EventSimpleDto initializeScmEvent() {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setProvider(scmRepositoryDefinition.getType());
        event.setProject(scmRepositoryDefinition.getProjectForRepository());
        return event;
    }
}
