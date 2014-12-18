
package org.komea.connectors.git.impl;


import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.IGitRepository;
import org.komea.connectors.git.exceptions.GitRuntimeException;
import org.komea.connectors.git.exceptions.ScmCannotObtainGitProxyException;
import org.komea.connectors.git.exceptions.ScmGitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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

        final DiffComputation gitDiffComputation = new DiffComputation(this.git, scmCommit, _commit);
        gitDiffComputation.update();

        return scmCommit;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.connectors.git.impl.IGitRepository#getBranches()
     */
    @Override
    public List<Ref> getAllBranches() {

        try {
            return getGit().branchList().setListMode(ListMode.ALL).call();
        } catch (final GitAPIException e) {
            throw new ScmGitAPIException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Ref> getAllTags() {

        return getGit().getRepository().getTags();
    }
    @Override
    public Ref getBranch(final String branch) {

        for (final Ref ref : getAllBranches()) {
            final String name = ref.getName();
            if (name.equals(branch)) {
                return ref;
            }
        }
        throw new IllegalArgumentException("Unable to find branch " + branch);
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

    @Override
    public List<Ref> getLocalBranches() {

        try {
            return getGit().branchList().call();
        } catch (final GitAPIException e) {
            throw new ScmGitAPIException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.komea.connectors.git.impl.IGitRepository#getAllTagsFromABranch(java.lang.String)
     */
    @Override
    public Map<String, Ref> getTagsForBranch(final String _branchName) {

        try {
            final Map<String, Ref> result = Maps.newHashMap();

            final Ref branch = getBranch(_branchName);
            final Repository repository = getGit().getRepository();
            final RevWalk walk = new RevWalk(repository);
            final RevCommit branchCommit = walk.parseCommit(branch.getObjectId());

            final Collection<Ref> tags = getGit().tagList().call();
            for (final Ref tag : tags) {

                final RevObject object = walk.parseAny(tag.getObjectId());
                String tagName;
                RevCommit referencedCommit;
                if (object instanceof RevTag) {
                    final RevTag jtag = (RevTag) object;
                    tagName = jtag.getName();
                    final RevObject tagged = jtag.getObject();
                    referencedCommit = walk.parseCommit(repository.resolve(tagged.getName()));
                } else {
                    tagName = Repository.shortenRefName(tag.getName());
                    referencedCommit = (RevCommit) object;
                }

                if (walk.isMergedInto(referencedCommit, branchCommit)) {

                    result.put(tagName, tag);
                }

            }

            return result;
        } catch (final Exception e) {
            throw new GitRuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.komea.connectors.git.impl.IGitRepository#processAllCommits(org.komea.connectors.git.IGitCommitProcessor)
     */
    @Override
    public void processAllCommits(final IGitCommitProcessor _processor) {

        /**
         * Browse every local branch
         */
        final List<Ref> branches = getLocalBranches();
        for (final Ref ref : branches) {

            processCommits(_processor, ref);
        }

    }
    @Override
    public void processCommits(final IGitCommitProcessor _processor, final Ref ref) {

        RevWalk walk = null;
        try {
            /**
             * Browse every commit on branch
             */
            walk = new RevWalk(getGit().getRepository());
            final RevCommit branchCommit = walk.parseCommit(ref.getObjectId());
            walk.markStart(branchCommit);

            final String branch = ref.getName();
            final List<RevCommit> allCommitsOnBranch = Lists.newArrayList(walk);
            int i = 0;
            final int ni = allCommitsOnBranch.size();
            for (final RevCommit commit : allCommitsOnBranch) {
                if (i % 100 == 0) {
                    LOGGER.info("Progress {} {}% {}/{}", branch, i * 100 / ni, i, ni);
                }
                final IGitCommit gitCommit = GitRepository.this.convertGitCommit(commit, walk);
                gitCommit.setBranch(branch);
                _processor.process(commit, walk, gitCommit);
                i++;
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (walk != null) {
                walk.release();
            }
        }
    }

    @Override
    public void processCommits(final IGitCommitProcessor _processor, final String branch) {

        processCommits(_processor, getBranch(branch));
    }

}
