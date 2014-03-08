/**
 * 
 */

package org.komea.product.plugins.repository.model;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.google.common.base.Strings;



/**
 * This class defines an scm repository basic attributes. It contains branch / authentication definition.
 * 
 * @author sleroy
 */
public class ScmRepositoryDefinition
{
    
    
    /**
     * Converts a name into a key.
     * 
     * @param _name
     *            the repository name
     * @return the key.
     */
    public static String transformNameInKey(final String _name) {
    
    
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
    
    
    
    private final Map<String, BranchDefinition> branchDefinitions    =
                                                                             new HashMap<String, BranchDefinition>();
    
    
    private Set<String>                         customerRegExps      = new HashSet<String>();
    
    
    private String                              key;
    
    
    private String                              password             = "";
    
    private String                              projectForRepository = "";
    
    private String                              repoName             = "";
    
    
    private String                              url                  = "";
    
    
    private String                              userName             = "";
    
    
    
    /**
     * Returns the branch definition according the branch name.
     * 
     * @param _branchName
     *            the branch name.
     * @return the branh definition.
     */
    public BranchDefinition getBranchDefinition(final String _branchName) {
    
    
        return branchDefinitions.get(_branchName);
    }
    
    
    /**
     * Returns the custom regular expressions.
     * 
     * @return the customer regular expressions.
     */
    public Set<String> getCustomerRegExps() {
    
    
        return customerRegExps;
    }
    
    
    /**
     * @return the key
     */
    public String getKey() {
    
    
        return key;
    }
    
    
    /**
     * Returns the known branches of this repository.
     * 
     * @return the known branches of this repository.
     */
    public Set<String> getKnownBranches() {
    
    
        return branchDefinitions.keySet();
    }
    
    
    /**
     * @return the password
     */
    public String getPassword() {
    
    
        return password;
    }
    
    
    /**
     * Returns the project associated
     * 
     * @param _branchName
     *            the branch name.
     * @return the project associated
     */
    public String getProjectAssociated(final String _branchName) {
    
    
        if (!Strings.isNullOrEmpty(getProjectForRepository())) { return getProjectForRepository(); }
        final BranchDefinition branchDefinition = getBranchDefinition(_branchName);
        if (branchDefinition == null) { return ""; }
        return branchDefinition.getProject();
    }
    
    
    /**
     * @return the projectForRepository
     */
    public String getProjectForRepository() {
    
    
        return projectForRepository;
    }
    
    
    /**
     * @return the repoName
     */
    public String getRepoName() {
    
    
        return repoName;
    }
    
    
    /**
     * @return the url
     */
    public String getUrl() {
    
    
        return url;
    }
    
    
    /**
     * @return the userName
     */
    public String getUserName() {
    
    
        return userName;
    }
    
    
    /**
     * Tests if this branch matches a custom regexp.
     * 
     * @param _branchName
     *            the branch name.
     */
    public boolean isMatchCustomerRegularExpressions(final String _branchName) {
    
    
        for (final String regularExp : customerRegExps) {
            if (Pattern.matches(regularExp, _branchName)) { return true; }
        }
        return false;
    }
    
    
    /**
     * Register the branch if not existing.
     */
    public void registerBranch(final String _branchName) {
    
    
        if (!branchDefinitions.containsKey(_branchName)) {
            final BranchDefinition branchDefinition = new BranchDefinition(_branchName);
            if (isMatchCustomerRegularExpressions(_branchName)) {
                branchDefinition.setBranchType(BranchType.CUSTOMER);
            }
            branchDefinitions.put(_branchName, branchDefinition);
            
        }
    }
    
    
    /**
     * Sets the custom regular expressions to match customer branches.
     * 
     * @param _customerRegExps
     *            the regular expressions.
     */
    public void setCustomerRegExps(final Set<String> _customerRegExps) {
    
    
        customerRegExps = _customerRegExps;
    }
    
    
    /**
     * @param _key
     *            the key to set
     */
    public void setKey(final String _key) {
    
    
        key = _key;
    }
    
    
    /**
     * @param _password
     *            the password to set
     */
    public void setPassword(final String _password) {
    
    
        password = _password;
    }
    
    
    /**
     * @param _projectForRepository
     *            the projectForRepository to set
     */
    public void setProjectForRepository(final String _projectForRepository) {
    
    
        projectForRepository = _projectForRepository;
    }
    
    
    /**
     * @param _repoName
     *            the repoName to set
     */
    public void setRepoName(final String _repoName) {
    
    
        repoName = _repoName;
    }
    
    
    /**
     * @param _url
     *            the url to set
     */
    public void setUrl(final String _url) {
    
    
        url = _url;
    }
    
    
    /**
     * @param _userName
     *            the userName to set
     */
    public void setUserName(final String _userName) {
    
    
        userName = _userName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "ScmRepositoryDefinition [branchDefinitions="
                + branchDefinitions + ", customerRegExps=" + customerRegExps + ", key=" + key
                + ", password=" + password + ", projectForRepository=" + projectForRepository
                + ", repoName=" + repoName + ", url=" + url + ", userName=" + userName + "]";
    }
    
}
