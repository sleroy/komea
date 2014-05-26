
package org.komea.product.plugins.scm;



import java.util.List;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.plugins.scm.api.IScmRepositoryAnalysisService;
import org.komea.product.plugins.scm.api.error.ScmCronJobException;
import org.komea.product.plugins.scm.api.error.ScmRepositoryAnalysisException;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;



/**
 * Fetchs the new feeds and produces alerts.
 * 
 * @author sleroy
 */
@Service
public class ScmRepositoryAnalysisService implements IScmRepositoryAnalysisService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("scm-repository-analysis");
    
    @Autowired
    private IEventPushService   esperEngine;
    
    @Autowired
    private IPersonService      personService;
    
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.scm.api.IScmRepositoryAnalysisService#analysis(org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy
     * )
     */
    @Override
    public void analysis(final IScmRepositoryProxy _newProxy) {
    
    
        Validate.notNull(_newProxy);
        
        LOGGER.info("Differential analysis of the scm repository {}", _newProxy
                .getRepositoryDefinition().getRepoName());
        
        final List<Throwable> errorsFound = Lists.newArrayList();
        try {
            // Begin gitRepositoryDefinition
            esperEngine.sendEventDto(_newProxy.getEventFactory().sendFetchRepository());
            LOGGER.info("Checking number of branches");
            checkNumberOfBranches(_newProxy);
            
            for (final String branchName : _newProxy.getBranches()) {
                try {
                    LOGGER.info("Checking number of tags for the branch {}", branchName);
                    checkNumberOfTagsPerBranch(_newProxy, branchName);
                    LOGGER.info("Checking new commits {}", branchName);
                    checkNewCommits(_newProxy, branchName);
                } catch (final Exception e) {
                    errorsFound.add(e);
                }
            }
            
            if (!errorsFound.isEmpty()) {
                throw new ScmRepositoryAnalysisException(errorsFound);
            }
        } catch (final Throwable e) {
            LOGGER.error(e.getMessage(), e);
            esperEngine.sendEventDto(_newProxy.getEventFactory().sendFetchFailed());
            throw new ScmCronJobException(e.getMessage(), e);
        }
        
    }
    
    
    /**
     * Checks new commit from a branch.
     * 
     * @param _newProxy
     *            the proxy
     * @param _branchName
     *            the branch name
     */
    public void checkNewCommits(final IScmRepositoryProxy _newProxy, final String _branchName) {
    
    
        final DateTime lastDate = _newProxy.getRepositoryDefinition().getLastDateCheckoutOrNull();
        LOGGER.debug("Check for new commits on branch {} and last date {}", _branchName, lastDate);
        final List<IScmCommit> allCommitsFromABranch =
                _newProxy.getAllCommitsFromABranch(_branchName, lastDate);
        LOGGER.debug(">>>>>>> Received {} commits", allCommitsFromABranch.size());
        for (final IScmCommit commit : allCommitsFromABranch) {
            LOGGER.trace("Detected new commit {} since {} lastDate {}", commit, lastDate);
            
            notifyEvent(_newProxy.getEventFactory().sendNewCommit(commit.getMessage(),
                    commit.getAuthor(), commit.getId()));
            notifyCommit(commit);
        }
        
    }
    
    
    /**
     * Check the number of branches.
     * 
     * @param _newProxy
     */
    public void checkNumberOfBranches(final IScmRepositoryProxy _newProxy) {
    
    
        notifyEvent(_newProxy.getEventFactory().sendBranchNumbers(_newProxy.getBranches().size()));
    }
    
    
    /**
     * Check the number of tags per branch
     * 
     * @param _newProxy
     * @param branchName
     */
    public void checkNumberOfTagsPerBranch(
            final IScmRepositoryProxy _newProxy,
            final String branchName) {
    
    
        final int size = _newProxy.getAllTagsFromABranch(branchName).size();
        notifyEvent(_newProxy.getEventFactory().sendNumberTagPerBranch(size, branchName));
    }
    
    
    /**
     * @return the esperEngine
     */
    public IEventPushService getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    /**
     * @return the personService
     */
    public IPersonService getPersonService() {
    
    
        return personService;
    }
    
    
    // http://stackoverflow.com/questions/12493916/getting-commit-information-from-a-revcommit-object-in-jgit
    // http://stackoverflow.com/questions/10435377/jgit-how-to-get-branch-when-traversing-repos
    
    
    /**
     * @param _esperEngine
     *            the esperEngine to set
     */
    public void setEsperEngine(final IEventPushService _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    /**
     * @param _personService
     *            the personService to set
     */
    public void setPersonService(final IPersonService _personService) {
    
    
        personService = _personService;
    }
    
    
    // http://stackoverflow.com/questions/12493916/getting-commit-information-from-a-revcommit-object-in-jgit
    // http://stackoverflow.com/questions/10435377/jgit-how-to-get-branch-when-traversing-repos
    
    /**
     * Notify a commit to the CEP Engine.
     * 
     * @param _commit
     *            the commit
     */
    private void notifyCommit(final IScmCommit _commit) {
    
    
        Validate.notNull(_commit);
        esperEngine.sendCustomEvent(_commit);
        
    }
    
    
    /**
     * Notify that an event has been sent.
     * 
     * @param _event
     *            the event
     */
    void notifyEvent(final EventSimpleDto _event) {
    
    
        esperEngine.sendEventDto(_event);
        
    }
}
