
package org.komea.product.plugins.git.cron;



import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.FetchResult;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitCloner;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryReader;
import org.komea.product.plugins.git.utils.CommitBranchAssociationPredicate;
import org.komea.product.plugins.git.utils.GitEventFactory;
import org.komea.product.plugins.git.utils.GitRepositoryReaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;



/**
 * Fetchs the new feeds and produces alerts.
 * 
 * @author sleroy
 */
public class GitRepositoryReader implements IGitRepositoryReader
{
    
    
    private static final String     GIT          = "GIT";
    
    private static final Logger     LOGGER       = LoggerFactory.getLogger("git-repository-reader");
    
    private final IEventPushService esperEngine;
    private final GitEventFactory   eventFactory = new GitEventFactory();
    
    private final GitRepositoryDefinition           fetch;
    
    private final IGitCloner         gitCloner;
    
    private final IPersonService    personService;
    
    
    
    /**
     * Builds the object
     * 
     * @param _fetch
     *            the rss fedd
     * @param _lastDate
     *            the last fetched date.
     * @param _gitCloner
     */
    
    public GitRepositoryReader(
            final GitRepositoryDefinition _fetch,
            final IEventPushService _esperEngine,
            final IGitCloner _gitCloner,
            final IPersonService _personService) {
    
    
        fetch = _fetch;
        esperEngine = _esperEngine;
        gitCloner = _gitCloner;
        personService = _personService;
        
        
    }
    
    
    /**
     * Send a message when a new commit has been detected.
     * 
     * @param revWalk
     * @param parseCommit
     */
    public void alertNewCommit(final RevWalk revWalk, final RevCommit parseCommit) {
    
    
        final PersonIdent authorIdent = parseCommit.getAuthorIdent();
        final PersonIdent committerIdent = parseCommit.getCommitterIdent();
        
        final String revisionName = parseCommit.getId().name();
        if (!Strings.isNullOrEmpty(authorIdent.getName())) {
            notifyEvent(eventFactory.sendNewCommit(fetch, parseCommit.getShortMessage(),
                    personService.findOrCreatePersonByEmail(authorIdent.getEmailAddress()),
                    revisionName));
        }
        
        if (!Strings.isNullOrEmpty(committerIdent.getName())
                && !committerIdent.getName().equals(authorIdent.getName())) {
            notifyEvent(eventFactory.sendNewCommit(fetch, parseCommit.getShortMessage(),
                    personService.findOrCreatePersonByEmail(authorIdent.getEmailAddress()),
                    revisionName));
        }
        
    }
    
    
    // http://stackoverflow.com/questions/12493916/getting-commit-information-from-a-revcommit-object-in-jgit
    // http://stackoverflow.com/questions/10435377/jgit-how-to-get-branch-when-traversing-repos
    
    /**
     * Performs the event analysis on a specific git branch
     * 
     * @param _revWalk
     * @param git
     *            git
     * @param branch
     *            the branch
     * @throws GitAPIException
     * @throws RefAlreadyExistsException
     * @throws RefNotFoundException
     * @throws InvalidRefNameException
     * @throws CheckoutConflictException
     * @throws IOException
     * @throws NoHeadException
     */
    public void analysisGitBranch(final RevWalk _revWalk, final Git git, final Ref branch)
            throws GitAPIException, RefAlreadyExistsException, RefNotFoundException,
            InvalidRefNameException, CheckoutConflictException, IOException, NoHeadException {
    
    
        GitRepositoryReaderUtils.switchBranch(git, branch);
        
        final Repository repository = gitCloner.getRepository();
        checkNumberofTagsPerBranch(branch.getName(), repository);
        
        /**
         * Detect new commits in a branch.
         */
        detectNewCommitsInBranch(git, branch, repository, _revWalk);
    }
    
    
    /**
     * Performs the analysis of a git repository according requested scm event specification
     * 
     * @param _revWalk
     * @throws Exception
     */
    public void analysisGitRepository(final RevWalk _revWalk) throws Exception {
    
    
        final Git git = gitCloner.getGit();
        final List<Ref> branches = GitRepositoryReaderUtils.listRemoteBranches(git);
        checkBranchNumber(branches);
        for (final Ref branch : branches) {
            analysisGitBranch(_revWalk, git, branch);
            
        }
        
    }
    
    
    /**
     * Check the branch number of a repository
     * 
     * @param _gitRepositoryReader
     *            TODO
     * @param branches
     *            the list of branches in git format.
     */
    public void checkBranchNumber(final List<Ref> branches) {
    
    
        final int branchSize = branches.size();
        notifyEvent(eventFactory.sendBranchNumbers(fetch, branchSize));
    }
    
    
    /**
     * Check number of tags per branch.
     * 
     * @param currentBranchName
     * @param repository
     */
    public void checkNumberofTagsPerBranch(
            final String currentBranchName,
            final Repository repository) {
    
    
        final int numberOfTagsPerBranch = repository.getTags().size();
        
        notifyEvent(eventFactory.sendNumberTagPerBranch(fetch, numberOfTagsPerBranch,
                currentBranchName));
    }
    
    
    /**
     * Detects new commit in a given branch.
     * 
     * @param _branch
     *            the branch
     * @param _repository
     *            the repository.
     * @param _revWalk
     * @throws IOException
     * @throws GitAPIException
     * @throws NoHeadException
     */
    public void detectNewCommitsInBranch(
            final Git _git,
            final Ref _branch,
            final Repository _repository,
            final RevWalk _revWalk) throws IOException, NoHeadException, GitAPIException {
    
    
        /**
         * Produce the log configuration
         */
        final org.eclipse.jgit.api.LogCommand logcmd = _git.log();
        final Map<String, Ref> mapRefs =
                GitRepositoryReaderUtils.obtainBranchRefsFromARepository(_repository);
        
        GitRepositoryReaderUtils.initializeLogWalkWithBranchNames(logcmd, mapRefs);
        
        
        final Iterable<RevCommit> commit_log = logcmd.call();
        
        String objID = "";
        
        final CommitBranchAssociationPredicate commitBranchAssociationPredicate =
                new CommitBranchAssociationPredicate(_branch, _repository, _revWalk);
        
        /**
         * Browse every commit
         */
        for (final RevCommit commit : commit_log) {
            final RevCommit targetCommit =
                    _revWalk.parseCommit(_repository.resolve(commit.getName()));
            
            final boolean foundInThisBranch =
                    commitBranchAssociationPredicate.isCommitRelatedToBranch(targetCommit);
            if (foundInThisBranch && isLastCommitCheckedPreviousTime(_branch, targetCommit)) {
                break;
            } else if (foundInThisBranch) {
                // Its a new commit
                if (objID.isEmpty()) { // Initialize for the first time the commit that will be selected as last commit.
                    objID = targetCommit.getId().name();
                }
                alertNewCommit(_revWalk, targetCommit);
                
            }
        }
        fetch.updateLastRef(_branch.getName(), objID); // Store last object scanned
        
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.plugins.git.utils.IGitRepositoryReader#feed()
     */
    @Override
    public void feed() {
    
    
        LOGGER.info("Checking updates from {}", fetch.getRepoName());
        RevWalk revWalk = null;
        
        try {
            revWalk = new RevWalk(gitCloner.getRepository());
            final FetchResult fetchRepository =
                    GitRepositoryReaderUtils.fetchRepository(gitCloner.getGit());
            
            LOGGER.debug("Ref updates : {}", fetchRepository.getTrackingRefUpdates());
            LOGGER.debug("Advertised Ref : {}", fetchRepository.getAdvertisedRefs());
            LOGGER.info("Fetch ended with message {}", fetchRepository.getMessages());
            
            // Begin fetch
            esperEngine.sendEventDto(eventFactory.sendFetchRepository(fetch));
            analysisGitRepository(revWalk);
            
        } catch (final Throwable e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            revWalk.release();
            gitCloner.getGit().gc();
            
        }
    }
    
    
    /**
     * This method checks if the branch is the last commit previously checked
     * 
     * @param _branch
     *            the branch
     * @param targetCommit
     *            the target commit
     * @return the last
     */
    public boolean isLastCommitCheckedPreviousTime(final Ref _branch, final RevCommit targetCommit) {
    
    
        return targetCommit.getId().name().equals(fetch.getLastCommit(_branch.getName()));
    }
    
    
    // http://stackoverflow.com/questions/12493916/getting-commit-information-from-a-revcommit-object-in-jgit
    // http://stackoverflow.com/questions/10435377/jgit-how-to-get-branch-when-traversing-repos
    
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
