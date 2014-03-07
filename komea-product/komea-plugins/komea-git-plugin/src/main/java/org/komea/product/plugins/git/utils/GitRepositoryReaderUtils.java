/**
 * 
 */

package org.komea.product.plugins.git.utils;



import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.FetchResult;



/**
 * @author sleroy
 */
public class GitRepositoryReaderUtils
{
    
    
    /**
     * Fetch a repository
     * 
     * @return the repôsitory to fetch.
     * @throws GitAPIException
     * @throws InvalidRemoteException
     * @throws TransportException
     */
    public static FetchResult fetchRepository(final Git _git)
            throws GitAPIException, InvalidRemoteException, TransportException {
    
    
        return _git.fetch().call();
    }
    
    
    /**
     * Initialize log walk with branch names; The git component requires the branch to browse.
     * 
     * @param logcmd
     * @param mapRefs
     * @throws MissingObjectException
     * @throws IncorrectObjectTypeException
     */
    public static void initializeLogWalkWithBranchNames(
            final LogCommand logcmd,
            final Map<String, Ref> mapRefs)
            throws MissingObjectException, IncorrectObjectTypeException {
    
    
        final Set<String> branchRefNames = mapRefs.keySet();
        for (final String branchRef : branchRefNames) {
            final Ref ref = mapRefs.get(branchRef);
            logcmd.add(ref.getObjectId());
        }
    }
    
    
    /**
     * Returns the list of remote branches
     * 
     * @param git
     *            the git param
     * @return the list of remote branches.
     * @throws GitAPIException
     */
    public static List<Ref> listRemoteBranches(final Git git) throws GitAPIException {
    
    
        return git.branchList().setListMode(ListMode.REMOTE).call();
    }
    
    
    /**
     * Obtain the list of branches from a git repository.
     * 
     * @param repository
     *            the repository
     * @return the list of references
     * @throws IOException
     */
    public static Map<String, Ref> obtainBranchRefsFromARepository(final Repository repository)
            throws IOException {
    
    
        final Map<String, Ref> mapRefs =
                new HashMap(repository.getRefDatabase().getRefs("refs/heads"));
        final Map<String, Ref> refs = repository.getRefDatabase().getRefs("refs/remotes");
        if (refs != null) {
            mapRefs.putAll(refs);
        }
        return mapRefs;
    }
    
    
    /**
     * Switch a branch from a repository.
     * 
     * @param git
     *            the git repository
     * @param branch
     *            the branch reference.
     * @throws GitAPIException
     * @throws RefAlreadyExistsException
     * @throws RefNotFoundException
     * @throws InvalidRefNameException
     * @throws CheckoutConflictException
     */
    public static void switchBranch(final Git git, final Ref branch)
            throws GitAPIException, RefAlreadyExistsException, RefNotFoundException,
            InvalidRefNameException, CheckoutConflictException {
    
    
        git.checkout().setName(branch.getName()).call();
        
    }
    
}
