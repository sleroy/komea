/**
 * 
 */

package org.komea.product.plugins.git.model;



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
        return gitRepo;
    }
    
    
    
    /**
     * last references.
     */
    private final Map<String, String> lastRefs = new HashMap<String, String>();
    
    
    
    /**
     * Git repository definition.
     */
    private GitRepositoryDefinition() {
    
    
        super();
        
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
