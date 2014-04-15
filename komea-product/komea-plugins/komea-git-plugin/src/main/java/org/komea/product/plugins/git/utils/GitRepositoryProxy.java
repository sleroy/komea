
package org.komea.product.plugins.git.utils;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.git.api.errors.ScmCannotObtainCommitListException;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.ScmEventFactory;
import org.komea.product.plugins.scm.api.plugin.IScmCloner;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.komea.product.plugins.scm.api.plugin.ScmCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * Fetchs the new feeds and produces alerts.
 * 
 * @author sleroy
 */

public class GitRepositoryProxy implements IScmRepositoryProxy
{
    
    
    private static final Logger           LOGGER = LoggerFactory.getLogger("git-repository-reader");
    
    
    private Git                           git;
    
    
    @Autowired
    private IPersonService                personService;
    
    
    private final ScmRepositoryDefinition repositoryDefinition;
    
    
    private final ScmEventFactory         scmEventFactory;
    
    
    private final File                    storageFolder;
    
    
    
    /**
     * Builds the GIT repositor proxy, an exception may be thrown when creating the GIT Proxy.
     */
    
    public GitRepositoryProxy(
            final ScmRepositoryDefinition _repositoryDefinition,
            final File _storageFolder) {
    
    
        super();
        repositoryDefinition = _repositoryDefinition;
        storageFolder = _storageFolder;
        scmEventFactory = new ScmEventFactory(repositoryDefinition);
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
    
    
        if (git != null) {
            git.gc();
            
            git.close();
        }
    }
    
    
    /**
     * Convert a git commit to scm commit.
     * 
     * @param _commit
     * @return
     */
    public IScmCommit convertGitCommit(final RevCommit _commit) {
    
    
        final PersonIdent authorIdent = _commit.getAuthorIdent();
        final Person personByEmail =
                personService.findOrCreatePersonByEmail(authorIdent.getEmailAddress());
        final String revisionName = _commit.getId().name();
        
        return new ScmCommit(personByEmail, new DateTime(_commit.getAuthorIdent().getWhen()),
                revisionName, _commit.getFullMessage());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getAllCommitsFromABranch(java.lang.String, org.joda.time.DateTime)
     */
    @Override
    public List<IScmCommit> getAllCommitsFromABranch(
            final String _branchName,
            final DateTime _previousTime) {
    
    
        Validate.notEmpty(_branchName);
        DateTime previousTime = _previousTime;
        if (_previousTime == null) {
            previousTime = new DateTime();
            previousTime.withYear(1900);
            previousTime.withMonthOfYear(1);
            previousTime.withDayOfMonth(1);
        }
        GitRepositoryReaderUtils.switchBranch(getGit(), _branchName);
        final List<IScmCommit> commits = new ArrayList<IScmCommit>(100);
        RevWalk revWalk = null;
        try {
            
            /**
             * Produce the log configuration
             */
            final org.eclipse.jgit.api.LogCommand logcmd = getGit().log();
            final Map<String, Ref> mapRefs =
                    GitRepositoryReaderUtils.obtainBranchRefsFromARepository(getGit()
                            .getRepository());
            
            GitRepositoryReaderUtils.initializeLogWalkWithBranchNames(logcmd, mapRefs);
            revWalk = new RevWalk(getGit().getRepository());
            
            final DoesCommitOwnToThisBranchPredicate predicate =
                    newBranchPredicate(_branchName, revWalk);
            
            /**
             * Browse every commit
             */
            for (final RevCommit commit : logcmd.call()) {
                final DateTime dateTime = new DateTime(commit.getAuthorIdent().getWhen());
                if (!previousTime.isBefore(dateTime)) {
                    continue;
                }
                
                
                if (predicate.isCommitRelatedToBranch(commit)) {
                    commits.add(convertGitCommit(commit));
                }
                
            }
        } catch (final Exception e) {
            throw new ScmCannotObtainCommitListException(
                    "Could not obtain the list of new commits for the branch " + _branchName, e);
            
        } finally {
            if (revWalk != null) {
                revWalk.release();
            }
        }
        
        
        return commits;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getAllTagsFromABranch(java.lang.String)
     */
    @Override
    public Set<String> getAllTagsFromABranch(final String _branchName) {
    
    
        GitRepositoryReaderUtils.switchBranch(getGit(), _branchName);
        return getGit().getRepository().getTags().keySet();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getBranches()
     */
    @Override
    public List<String> getBranches() {
    
    
        final List<Ref> branches = GitRepositoryReaderUtils.listRemoteBranches(getGit());
        return CollectionUtil.convertAll(branches, new GitRefToBranchNameConverter());
    }
    
    
    // http://stackoverflow.com/questions/12493916/getting-commit-information-from-a-revcommit-object-in-jgit
    // http://stackoverflow.com/questions/10435377/jgit-how-to-get-branch-when-traversing-repos
    
    /**
     * @return the eventFactory
     */
    @Override
    public ScmEventFactory getEventFactory() {
    
    
        return scmEventFactory;
    }
    
    
    /**
     * Returns the git
     * 
     * @return the git object.
     */
    public Git getGit() {
    
    
        if (git == null) {
            git = GitRepositoryReaderUtils.getGit(repositoryDefinition);
            
        }
        
        return git;
    }
    
    
    /**
     * @return the personService
     */
    public IPersonService getPersonService() {
    
    
        return personService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getRepositoryDefinition()
     */
    @Override
    public ScmRepositoryDefinition getRepositoryDefinition() {
    
    
        return repositoryDefinition;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#getScmCloner()
     */
    @Override
    public IScmCloner getScmCloner() {
    
    
        return new GitCloner(storageFolder, repositoryDefinition);
    }
    
    
    /**
     * @param _personService
     *            the personService to set
     */
    public void setPersonService(final IPersonService _personService) {
    
    
        personService = _personService;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#testConnection()
     */
    @Override
    public void testConnection() {
    
    
        //
        
    }
    
    
    private DoesCommitOwnToThisBranchPredicate newBranchPredicate(
            final String _branchName,
            final RevWalk revWalk) {
    
    
        final DoesCommitOwnToThisBranchPredicate doesCommitOwnToThisBranchPredicate =
                new DoesCommitOwnToThisBranchPredicate(_branchName, getGit().getRepository(),
                        revWalk);
        return doesCommitOwnToThisBranchPredicate;
    }
    
    
    // http://stackoverflow.com/questions/12493916/getting-commit-information-from-a-revcommit-object-in-jgit
    // http://stackoverflow.com/questions/10435377/jgit-how-to-get-branch-when-traversing-repos
    
    
}
