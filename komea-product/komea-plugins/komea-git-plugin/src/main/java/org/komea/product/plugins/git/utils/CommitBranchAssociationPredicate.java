/**
 * 
 */
package org.komea.product.plugins.git.utils;

import java.io.IOException;
import java.util.Map;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class CommitBranchAssociationPredicate
{
    
    
    private final Ref        branch;
    private final Repository repository;
    private final RevWalk    revWalk;
    
    
    
    public CommitBranchAssociationPredicate(
            final Ref _branch,
            final Repository _repository,
            final RevWalk _revWalk) {
    
    
        super();
        branch = _branch;
        repository = _repository;
        revWalk = _revWalk;
        
    }
    
    
    /**
     * Tests if a commit is related to a branch.
     * 
     * @param _branch
     *            the branch
     * @param _repository
     *            the repository
     * @param _revWalk
     *            the walker
     * @param targetCommit
     *            the comit
     * @return true if the commit is related to a branch.
     * @throws MissingObjectException
     * @throws IncorrectObjectTypeException
     * @throws IOException
     */
    public boolean isCommitRelatedToBranch(final RevCommit targetCommit)
            throws MissingObjectException, IncorrectObjectTypeException, IOException {
    
    
        for (final Map.Entry<String, Ref> e : repository.getAllRefs().entrySet()) {
            
            if (e.getKey().startsWith(Constants.R_REMOTES)) {
                if (revWalk.isMergedInto(targetCommit,
                        revWalk.parseCommit(e.getValue().getObjectId()))) {
                    final String foundInBranch = e.getValue().getName();
                    if (branch.getName().equals(foundInBranch)) { return true; }
                }
            }
        }
        return false;
    }
}