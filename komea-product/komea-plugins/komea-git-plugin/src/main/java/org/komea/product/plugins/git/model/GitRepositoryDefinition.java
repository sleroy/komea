/**
 * 
 */

package org.komea.product.plugins.git.model;



import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;



/**
 * @author sleroy
 */
public class GitRepositoryDefinition extends ScmRepositoryDefinition
{
    
    
    /**
     * Creates a new git repository.
     * 
     * @param _name
     *            the repository name
     * @param _url
     *            the url
     * @return the new git repository.
     */
    public static GitRepositoryDefinition newGitRepository(final String _name, final String _url) {
    
    
        final GitRepositoryDefinition gitRepo = new GitRepositoryDefinition();
        gitRepo.setRepoName(_name);
        gitRepo.setKey(transformNameInKey(_name));
        gitRepo.setUrl(_url);
        gitRepo.setLastDateCheckout(new Date());
        return gitRepo;
    }
    
    
    
    private File                      cloneDirectory;
    
    
    /**
     * last references.
     */
    private final Map<String, String> lastRefs = new HashMap<String, String>();
    
    
    
    /**
     * Git repository definition.
     */
    public GitRepositoryDefinition() {
    
    
        super();
        
    }
    
    
    public File getCloneDirectory() {
    
    
        return cloneDirectory;
    }
    
    
    /**
     * Returns the last commit
     * 
     * @param _name
     *            the branch name
     * @return the last commit or null.
     */
    public String getLastCommit(final String _name) {
    
    
        return lastRefs.get(_name);
    }
    
    
    /**
     * Returns the last references
     * 
     * @return the last references.
     */
    public Map<String, String> getLastRefs() {
    
    
        return lastRefs;
    }
    
    
    public void setCloneDirectory(final File _cloneDirectory) {
    
    
        cloneDirectory = _cloneDirectory;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "GitRepositoryDefinition [lastRefs="
                + lastRefs + ", cloneDirectory=" + cloneDirectory + ", getLastRefs()="
                + getLastRefs() + ", getCloneDirectory()=" + getCloneDirectory()
                + ", getCustomerRegExps()=" + getCustomerRegExps() + ", getKey()=" + getKey()
                + ", getKnownBranches()=" + getKnownBranches() + ", getLastDateCheckout()="
                + getLastDateCheckout() + ", getPassword()=" + getPassword()
                + ", getProjectForRepository()=" + getProjectForRepository() + ", getRepoName()="
                + getRepoName() + ", getUrl()=" + getUrl() + ", getUserName()=" + getUserName()
                + ", toString()=" + super.toString() + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + "]";
    }
    
    
    /**
     * Updates the last reference
     * 
     * @param _branchName
     *            the branch name
     * @param _objID
     *            the ref ID.
     */
    public void updateLastRef(final String _branchName, final String _objID) {
    
    
        lastRefs.put(_branchName, _objID);
    }
}
