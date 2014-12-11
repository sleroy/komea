
package org.komea.connectors.git;


import java.util.List;
import java.util.Set;

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
    
     
     Set<String> getAllTagsFromABranch(String _branchName);
    
     List<Ref> getBranches();
    
     /**
     * Returns the git
     *
     * @return the git object.
     */
     Git getGit();
    
     void processAllCommits(IGitCommitProcessor _processor);
    
     void processAllRevisions(IGitRevisionProcessor _processor);
    
}
