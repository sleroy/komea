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
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.FetchResult;
import org.komea.product.plugins.git.api.errors.ScmCannotObtainGitProxyException;
import org.komea.product.plugins.git.api.errors.ScmGitAPIException;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;



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
     * Returns a git object.
     * 
     * @param _repositoryDefinition
     * @return
     */
    public static Git getGit(final ScmRepositoryDefinition _repositoryDefinition) {
    
    
        if (!_repositoryDefinition.getCloneDirectory().exists()) { throw new IllegalArgumentException(
                "GIT Repository should be cloned before"); }
        FileRepository fileRepository;
        try {
            fileRepository =
                    new FileRepository(_repositoryDefinition.getCloneDirectory() + "/.git");
            return new Git(fileRepository);
        } catch (final IOException e) {
            throw new ScmCannotObtainGitProxyException("Could not create Git proxy on repository "
                    + _repositoryDefinition.getRepoName(), e);
        }
        
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
    public static List<Ref> listRemoteBranches(final Git git) {
    
    
        try {
            return git.branchList().setListMode(ListMode.REMOTE).call();
        } catch (final GitAPIException e) {
            throw new ScmGitAPIException(e.getMessage(), e);
        }
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
                new HashMap<String, Ref>(repository.getRefDatabase().getRefs("refs/heads"));
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
