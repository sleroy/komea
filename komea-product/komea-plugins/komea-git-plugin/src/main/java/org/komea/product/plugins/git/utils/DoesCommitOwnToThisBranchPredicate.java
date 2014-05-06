/**
 * 
 */

package org.komea.product.plugins.git.utils;



import java.io.IOException;

import org.apache.commons.lang.Validate;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
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
    private final RevCommit     branchFirstCommit;
    private final String        branchName;
    
    private final Ref           firstBranchCommit;
    private final Repository    repository;
    private final RevWalk       revWalk;
    
    
    
    public DoesCommitOwnToThisBranchPredicate(
            final String _branchName,
            final Repository _repository,
            final RevWalk _revWalk)
            throws MissingObjectException, IncorrectObjectTypeException, IOException {
    
    
        super();
        Validate.isTrue(!_branchName.isEmpty());
        Validate.notNull(_repository);
        Validate.notNull(_revWalk);
        
        branchName = _branchName;
        repository = _repository;
        revWalk = _revWalk;
        firstBranchCommit = repository.getAllRefs().get(branchName);
        Validate.notNull(firstBranchCommit);
        branchFirstCommit = revWalk.parseCommit(firstBranchCommit.getObjectId());
        
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
    public boolean isCommitRelatedToBranch(final RevCommit _commit) {
    
    
        // if (e.getKey().startsWith(Constants.R_REMOTES)) {
        try {
            final RevCommit targetCommit =
                    revWalk.parseCommit(repository.resolve(_commit.getName()));
            return revWalk.isMergedInto(targetCommit, branchFirstCommit);
        } catch (final Exception e1) {
            LOGGER.error(e1.getMessage(), e1);
        }
        // }
        return false;
    }
    
}
