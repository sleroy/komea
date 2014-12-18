
package org.komea.connectors.git;


import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public interface IGitCommitProcessor
{
    
    void process(RevCommit commit, RevWalk revWalk, IGitCommit convertGitCommit);
    
}
