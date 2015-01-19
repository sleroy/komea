package org.komea.connectors.git.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
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

/*
 * @author sleroy
 */
public class GitRepository implements IGitRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitRepository.class);

    private Git git;

    private final File storageFolder;
    private final String repositoryUrl;

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
        RevCommit[] parents = _commit.getParents();
        for (int i = 0; i < parents.length; i++) {
            scmCommit.getParents().add(parents[i].getName());
        }
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
        try {
            CommitBranchManager branchManager = new CommitBranchManager(this);
            for (final Ref ref : branches) {

                processCommits(_processor, branchManager, ref);
            }
        } catch (IOException e) {
            throw new GitRuntimeException(e);
        }

    }

    public void processCommits(final IGitCommitProcessor _processor, final CommitBranchManager globalBranchesManager, final Ref ref) {

        RevWalk walk = null;

        CommitBranchManager localManager;
        try {

            if (globalBranchesManager == null) {
                localManager = new CommitBranchManager(this);
            } else {
                localManager = globalBranchesManager;
            }

            /**
             * Browse every commit on branch
             */
            walk = new RevWalk(getGit().getRepository());
            final RevCommit branchCommit = walk.parseCommit(ref.getObjectId());
            walk.markStart(branchCommit);

            final String branch = ref.getName();
            localManager.startBranch(branch);
            final List<RevCommit> allCommitsOnBranch = Lists.newArrayList(walk);

            final int ni = allCommitsOnBranch.size();

            boolean done = false;
            for (int i = 0; i < ni; ++i) {
                final RevCommit commit = allCommitsOnBranch.get(i);
                if (i % 100 == 0) {
                    LOGGER.info("Progress {} {}% {}/{}", branch, i * 100 / ni, i, ni);
                }
                done = processCommit(_processor, ref, walk, localManager, done, commit);

            }

        } catch (final Exception e) {
            throw new GitRuntimeException(e);
        } finally {
            if (globalBranchesManager != null) {
                globalBranchesManager.endBranch(ref.getName());
            }
            if (walk != null) {
                walk.release();
            }
        }
    }

    private boolean processCommit(final IGitCommitProcessor processor, final Ref ref, final RevWalk walk,
            final CommitBranchManager branchManager, final boolean breaked, final RevCommit commit) throws MissingObjectException,
            IncorrectObjectTypeException, AmbiguousObjectException, IOException, Exception {

        if (breaked) {
            return true;
        }
        List<String> branches = branchManager.getBranches(commit.getName());
        if (branchManager.requireFurtherProcessing(commit.getName(), ref.getName(), branches)) {

            final IGitCommit gitCommit = GitRepository.this.convertGitCommit(commit, walk);

            gitCommit.setBranches(branches);
            processor.process(commit, walk, gitCommit);
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void processCommits(final IGitCommitProcessor _processor, final Ref ref) {

        processCommits(_processor, null, ref);

    }

    private static class ForkDetector {

        private Set<String> previousBranches;

        private final Map<String, GitForkCommit> forks;

        public ForkDetector() {

            this.forks = Maps.newHashMap();
            this.previousBranches = Sets.newHashSet();
        }

        public GitForkCommit getFork(final String commmit) {

            return this.forks.get(commmit);
        }

        public void createForkIfNeeded(final String commit, final List<String> branches) {

            GitForkCommit fork = this.forks.get(commit);
            HashSet<String> branchesSet = Sets.newHashSet(branches);
            if (fork == null && !this.previousBranches.equals(branchesSet)) {
                fork = new GitForkCommit(commit, branchesSet);
                this.forks.put(commit, fork);
                this.previousBranches = branchesSet;

            }

        }

        /**
         * Notify that a branch process has been started
         *
         * @param branch
         */
        public void startBranch(final String branch) {

            this.previousBranches.clear();
            this.previousBranches.add(branch);
        }

    }

    private static class CommitBranchManager {

        private final GitRepository repository;
        private final RevWalk walk;
        private final List<RevCommit> branchCommits;
        private final List<Ref> branches;
        private final Set<String> processedBranches;
        private final ForkDetector forkDetector;

        public CommitBranchManager(final GitRepository repository) throws MissingObjectException, IncorrectObjectTypeException, IOException {

            this.processedBranches = Sets.newHashSet();
            this.repository = repository;
            this.walk = new RevWalk(repository.git.getRepository());
            this.branchCommits = Lists.newArrayList();
            this.branches = repository.getLocalBranches();
            this.forkDetector = new ForkDetector();
            for (Ref branch : this.branches) {
                RevCommit branchCommit = this.walk.parseCommit(branch.getObjectId());
                this.branchCommits.add(branchCommit);
            }
        }

        /**
         * Notify that a branch process has been started
         *
         * @param branch
         */
        public void startBranch(final String branch) {

            this.forkDetector.startBranch(branch);
        }

        /**
         * Notify that a branch has been fully processed.
         *
         * @param branch
         */
        public void endBranch(final String branch) {

            this.processedBranches.add(branch);
        }

        /**
         * Test if a commit and all its ancestor needs to be processed.
         *
         * @param commit commit name
         * @param currentBranch current walked branch
         * @param _branches branches of the commit
         * @return
         * @throws RevisionSyntaxException
         * @throws MissingObjectException
         * @throws IncorrectObjectTypeException
         * @throws AmbiguousObjectException
         * @throws IOException
         */
        public boolean requireFurtherProcessing(final String commit, final String currentBranch, final List<String> _branches)
                throws RevisionSyntaxException, MissingObjectException, IncorrectObjectTypeException, AmbiguousObjectException, IOException {

            GitForkCommit fork = this.forkDetector.getFork(commit);
            if (fork != null) {
                return !fork.isAncestorOfBranch(currentBranch);
            } else {
                this.forkDetector.createForkIfNeeded(commit, _branches);
            }

            return true;
        }

        private List<String> getBranches(final String name) throws RevisionSyntaxException, MissingObjectException,
                IncorrectObjectTypeException, AmbiguousObjectException, IOException {

            List<String> names = Lists.newArrayList();
            RevCommit commit = this.walk.parseCommit(this.repository.getGit().getRepository().resolve(name));
            for (int i = 0; i < this.branches.size(); i++) {
                Ref branch = this.branches.get(i);
                if (this.walk.isMergedInto(commit, this.branchCommits.get(i))) {
                    names.add(branch.getName());
                }
            }
            return names;
        }
    }

    private static class GitForkCommit {

        private final String commitName;
        private final Set<String> branches;

        public GitForkCommit(final String commitName, final Set<String> branches) {

            super();
            this.commitName = commitName;
            this.branches = branches;
        }

        public boolean isAncestorOfBranch(final String name) {

            return this.branches.contains(name);
        }

        public String getCommitName() {

            return this.commitName;
        }

        public Set<String> getBranches() {

            return this.branches;
        }

        @Override
        public String toString() {

            return this.commitName + " => " + this.branches;
        }

    }

    @Override
    public void processCommits(final IGitCommitProcessor _processor, final String branch) {

        processCommits(_processor, getBranch(branch));
    }

}
