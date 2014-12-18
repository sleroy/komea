
package org.komea.connectors.git;


import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public interface IGitRepository
{

    void close();

    /**
     * Convert a git commit to scm commit.
     *
     * @param _commit
     * @param revWalk
     * @return
     * @throws Exception
     */
    IGitCommit convertGitCommit(RevCommit _commit, RevWalk revWalk) throws Exception;

    List<Ref> getAllBranches();

    Map<String, Ref> getAllTags();

    Ref getBranch(final String branch);

    /**
     * Returns the git
     *
     * @return the git object.
     */
    Git getGit();

    List<Ref> getLocalBranches();

    Map<String, Ref> getTagsForBranch(String _branchName);

    void processAllCommits(IGitCommitProcessor _processor);

    void processCommits(final IGitCommitProcessor _processor, final Ref ref);

    void processCommits(final IGitCommitProcessor _processor, final String branch);

}
