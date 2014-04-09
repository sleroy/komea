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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Predicate to identifiy if a commit owns to a branch.
 * 
 * @author sleroy
 */
public class DoesCommitOwnToThisBranchPredicate
{
    
    
    private static final Logger LOGGER = LoggerFactory
                                               .getLogger(DoesCommitOwnToThisBranchPredicate.class);
    private final String        branchName;
    private final Repository    repository;
    
    private final RevWalk       revWalk;
    
    
    
    public DoesCommitOwnToThisBranchPredicate(
            final String _branchName,
            final Repository _repository,
            final RevWalk _revWalk) {
    
    
        super();
        branchName = _branchName;
        repository = _repository;
        revWalk = _revWalk;
        
    }
    
    
    /**
     * Tests if a commit is related to a branchName.
     * 
     * @param _branch
     *            the branchName
     * @param _repository
     *            the repository
     * @param _revWalk
     *            the walker
     * @param targetCommit
     *            the comit
     * @return true if the commit is related to a branchName.
     * @throws MissingObjectException
     * @throws IncorrectObjectTypeException
     * @throws IOException
     */
    public boolean isCommitRelatedToBranch(final RevCommit targetCommit) {
    
    
        for (final Map.Entry<String, Ref> e : repository.getAllRefs().entrySet()) {
            
            if (e.getKey().startsWith(Constants.R_REMOTES)) {
                try {
                    if (revWalk.isMergedInto(targetCommit,
                            revWalk.parseCommit(e.getValue().getObjectId()))) {
                        final String foundInBranch = e.getValue().getName();
                        if (branchName.equals(foundInBranch)) { return true; }
                    }
                } catch (final Exception e1) {
                    LOGGER.error(e1.getMessage(), e1);
                }
            }
        }
        return false;
    }
    
    
}
