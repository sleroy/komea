
package org.komea.connectors.git.impl;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.IGitRepository;
import org.komea.connectors.git.IGitRevisionProcessor;
import org.komea.connectors.git.exceptions.ScmCannotObtainGitProxyException;
import org.komea.connectors.git.exceptions.ScmGitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/*
 * @author sleroy
 */
public class GitRepository implements IGitRepository
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GitRepository.class);
    
    private Git                 git;
    
    private final File          storageFolder;
    private final String        repositoryUrl;
    
    /**
     * Builds the GIT repositor proxy, an exception may be thrown when creating
     * the GIT Proxy.
     */
    public GitRepository(final File _storageFolder, final String repositoryUrl) {
    
        super();
        this.storageFolder = _storageFolder;
        this.repositoryUrl = repositoryUrl;
    }
    
    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    
    /*
     * (non-Javadoc)
     * @see org.komea.connectors.git.impl.IGitRepository#close()
     */
    @Override
    public void close() {
    
        if (this.git != null) {
            this.git.gc();
            
            this.git.close();
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.connectors.git.impl.IGitRepository#convertGitCommit(org.eclipse.jgit.revwalk.RevCommit,
     * org.eclipse.jgit.revwalk.RevWalk)
     */
    @Override
    public IGitCommit convertGitCommit(final RevCommit _commit, final RevWalk revWalk) throws Exception {
    
        final PersonIdent author = _commit.getAuthorIdent();
        final PersonIdent committer = _commit.getCommitterIdent();
        if (author != null && committer != null) {
            if (Strings.isNullOrEmpty(author.getEmailAddress())) {
                LOGGER.warn("Could not find email from the author of this commit {}", _commit);
            }
            if (Strings.isNullOrEmpty(committer.getEmailAddress())) {
                LOGGER.warn("Could not find email from the committer of this commit {}", _commit);
            }
            
        } else {
            LOGGER.warn("Could not find information about author or committer of this commit {}", _commit);
        }
        
        final GitCommit scmCommit = new GitCommit(_commit.getId().name(), author, committer, _commit.getFullMessage());
        scmCommit.setRepositoryUrl(this.repositoryUrl);
        scmCommit.setCommitMessageSize(_commit.getFullMessage().length());
        final DiffComputation gitDiffComputation = new DiffComputation(this.git, scmCommit, _commit, revWalk);
        gitDiffComputation.updateCommitWithDiffInformations();
        
        return scmCommit;
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy#
     * getAllCommitsFromABranch(java.lang.String, org.joda.time.DateTime)
     */
    
    /*
     * (non-Javadoc)
     * @see org.komea.connectors.git.impl.IGitRepository#getAllTagsFromABranch(java.lang.String)
     */
    @Override
    public Set<String> getAllTagsFromABranch(final String _branchName) {
    
        try {
            getGit().checkout().setName(_branchName).call();
            return this.getGit().getRepository().getTags().keySet();
        } catch (final Exception e) {
            throw new ScmCannotObtainGitProxyException("Cannot switch to branch " + _branchName, e);
        }
        
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.connectors.git.impl.IGitRepository#getBranches()
     */
    @Override
    public List<Ref> getBranches() {
    
        try {
            return getGit().branchList().setListMode(ListMode.ALL).call();
        } catch (final GitAPIException e) {
            throw new ScmGitAPIException(e.getMessage(), e);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.connectors.git.impl.IGitRepository#getGit()
     */
    @Override
    public Git getGit() {
    
        if (this.git == null) {
            
            try {
                
                this.git = Git.open(this.storageFolder);
            } catch (final IOException e) {
                throw new ScmCannotObtainGitProxyException("Could not create Git proxy on repository ", e);
            }
            
        }
        
        return this.git;
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.connectors.git.impl.IGitRepository#processAllCommits(org.komea.connectors.git.IGitCommitProcessor)
     */
    @Override
    public void processAllCommits(final IGitCommitProcessor _processor) {
    
        this.processAllRevisions(new IGitRevisionProcessor()
        {
            
            @Override
            public void process(final RevCommit commit, final RevWalk revWalk) throws Exception {
            
                final IGitCommit gitCommit = GitRepository.this.convertGitCommit(commit, revWalk);
                _processor.process(commit, revWalk, gitCommit);
                
            }
        });
        
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.connectors.git.impl.IGitRepository#processAllRevisions(org.komea.connectors.git.IGitRevisionProcessor)
     */
    @Override
    public void processAllRevisions(final IGitRevisionProcessor _processor) {
    
        RevWalk revWalk = null;
        try {
            
            /**
             * Produce the log configuration
             */
            final org.eclipse.jgit.api.LogCommand logcmd = this.getGit().log();
            revWalk = new RevWalk(this.getGit().getRepository());
            
            /**
             * Browse every commit
             */
            logcmd.all();
            final List<RevCommit> call = Lists.newArrayList(logcmd.call());
            int i = 0;
            final int ni = call.size();
            for (final RevCommit commit : call) {
                if (i % 100 == 0) {
                    LOGGER.info("Progress {}% {}/{}", i * 100 / ni, i, ni);
                }
                _processor.process(commit, revWalk);
                i++;
                
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (revWalk != null) {
                revWalk.release();
            }
        }
        
    }
    
    // http://stackoverflow.com/questions/12493916/getting-commit-information-from-a-revcommit-object-in-jgit
    // http://stackoverflow.com/questions/10435377/jgit-how-to-get-branch-when-traversing-repos
    
}
