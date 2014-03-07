/**
 * 
 */

package org.komea.product.plugins.git.model;



import java.util.HashMap;
import java.util.Map;



/**
 * @author sleroy
 */
public class GitRepo
{
    
    
    private Long                      id;
    
    /**
     * last references.
     */
    private final Map<String, String> lastRefs          = new HashMap<String, String>();
    
    private String                    password          = "";
    
    private String                    projectAssociated = "";
    
    private String                    repoName          = "";
    
    
    private String                    url               = "";
    
    
    
    /**
	 * 
	 */
    public GitRepo() {
    
    
        super();
        
    }
    
    
    /**
     * @param _feedName
     * @param _url
     */
    public GitRepo(final String _feedName, final String _url) {
    
    
        super();
        repoName = _feedName;
        url = _url;
    }
    
    
    /**
     * @param _repoName
     * @param _url
     * @param _projectAssociated
     */
    public GitRepo(final String _repoName, final String _url, final String _projectAssociated) {
    
    
        super();
        repoName = _repoName;
        url = _url;
        projectAssociated = _projectAssociated;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
    
    
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (!(obj instanceof GitRepo)) { return false; }
        final GitRepo other = (GitRepo) obj;
        if (projectAssociated == null) {
            if (other.projectAssociated != null) { return false; }
        } else if (!projectAssociated.equals(other.projectAssociated)) { return false; }
        if (repoName == null) {
            if (other.repoName != null) { return false; }
        } else if (!repoName.equals(other.repoName)) { return false; }
        if (url == null) {
            if (other.url != null) { return false; }
        } else if (!url.equals(other.url)) { return false; }
        return true;
    }
    
    
    public Long getId() {
    
    
        return id;
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
    
    
    public String getProjectAssociated() {
    
    
        return projectAssociated;
    }
    
    
    public String getRepoName() {
    
    
        return repoName;
    }
    
    
    public String getUrl() {
    
    
        return url;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    
    
        final int prime = 31;
        int result = 1;
        result = prime * result + (projectAssociated == null ? 0 : projectAssociated.hashCode());
        result = prime * result + (repoName == null ? 0 : repoName.hashCode());
        result = prime * result + (url == null ? 0 : url.hashCode());
        return result;
    }
    
    
    public void setId(final Long _id) {
    
    
        id = _id;
    }
    
    
    public void setPassword(final String _password) {
    
    
        password = _password;
    }
    
    
    public void setProjectAssociated(final String _projectAssociated) {
    
    
        projectAssociated = _projectAssociated;
    }
    
    
    public void setRepoName(final String _repoName) {
    
    
        repoName = _repoName;
    }
    
    
    public void setUrl(final String _url) {
    
    
        url = _url;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "GitRepo [id="
                + id + ", lastRefs=" + lastRefs + ", password=" + password + ", projectAssociated="
                + projectAssociated + ", repoName=" + repoName + ", url=" + url + "]";
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
