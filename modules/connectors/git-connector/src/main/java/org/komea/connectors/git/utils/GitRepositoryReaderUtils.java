/**
 * 
 */

package org.komea.connectors.git.utils;


import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.komea.connectors.git.exceptions.ScmCannotObtainGitProxyException;
import org.komea.connectors.git.exceptions.ScmGitAPIException;

/**
 * @author sleroy
 */
public class GitRepositoryReaderUtils
{
    
    /**
     * Returns the list of remote branches
     * 
     * @param git
     *            the git param
     * @return the list of remote branches.
     * @throws GitAPIException
     */
    public static List<Ref> listRemoteBranches(final Git git) {
    
        try {
            return git.branchList().setListMode(ListMode.ALL).call();
        } catch (final GitAPIException e) {
            throw new ScmGitAPIException(e.getMessage(), e);
        }
    }
    
    /**
     * Switch a branch from a repository.
     * 
     * @param git
     *            the git repository
     * @param _branchName
     *            the branch reference.
     */
    public static void switchBranch(final Git git, final String _branchName) {
    
        try {
            git.checkout().setName(_branchName).call();
        } catch (final Exception e) {
            throw new ScmCannotObtainGitProxyException("Cannot switch to branch " + _branchName, e);
        }
        
    }
}
