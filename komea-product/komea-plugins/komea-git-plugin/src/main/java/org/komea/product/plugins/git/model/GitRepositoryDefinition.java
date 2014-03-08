/**
 * 
 */

package org.komea.product.plugins.git.model;



import java.util.HashMap;
import java.util.Map;



/**
 * @author sleroy
 */
public class GitRepositoryDefinition
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
        gitRepo.setKey(gitRepo.transformNameInKey(_name));
        gitRepo.setUrl(_url);
        return gitRepo;
    }
    
    
    
    private final Map<String, BranchDefinition> branchDefinitionsMap =
                                                                             new HashMap<String, BranchDefinition>();
    
    private String                              key                  = "";
    
    /**
     * last references.
     */
    private final Map<String, String>           lastRefs             =
                                                                             new HashMap<String, String>();
    
    private String                              password             = "";
    
    private String                              projectForRepository = "";
    
    private String                              repoName             = "";
    
    private String                              url                  = "";
    
    
    
    /**
     * Git repository definition.
     */
    private GitRepositoryDefinition() {
    
    
        super();
        
    }
    
    
    public String getKey() {
    
    
        return key;
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
    
    
    public Map<String, String> getLastRefs() {
    
    
        return lastRefs;
    }
    
    
    public String getPassword() {
    
    
        return password;
    }
    
    
    public String getProjectForRepository() {
    
    
        return projectForRepository;
    }
    
    
    public String getRepoName() {
    
    
        return repoName;
    }
    
    
    public String getUrl() {
    
    
        return url;
    }
    
    
    public void setKey(final String _key) {
    
    
        key = _key;
    }
    
    
    public void setPassword(final String _password) {
    
    
        password = _password;
    }
    
    
    public void setProjectForRepository(final String _projectForRepository) {
    
    
        projectForRepository = _projectForRepository;
    }
    
    
    public void setRepoName(final String _repoName) {
    
    
        repoName = _repoName;
    }
    
    
    public void setUrl(final String _url) {
    
    
        url = _url;
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
    
    
    /**
     * Converts a name into a key.
     * 
     * @param _name
     *            the repository name
     * @return the key.
     */
    private String transformNameInKey(final String _name) {
    
    
        final StringBuilder sBuilder = new StringBuilder();
        for (int i = 0, ni = _name.length(); i < ni; ++i) {
            final char charAt = _name.charAt(i);
            if (Character.isAlphabetic(charAt) || Character.isDigit(charAt)) {
                sBuilder.append(charAt);
            } else if (' ' == charAt) {
                sBuilder.append('_');
            }
        }
        return sBuilder.toString();
    }
}
