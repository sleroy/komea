/**
 * 
 */

package org.komea.product.plugins.repository.model;



/**
 * A branch definition is required when the user wants to identify customer branches from production branches
 * 
 * @author sleroy
 */
public class BranchDefinition
{
    
    
    private BranchType branchType = BranchType.UNKNOWN;
    
    
    private String     customer;
    
    private String     name       = "";
    
    private String     project    = "";
    
    
    
    /**
     * Branch definition;
     */
    public BranchDefinition() {
    
    
        super();
    }
    
    
    /**
     * Branch definition
     * 
     * @param _branchName
     *            the branch name;
     */
    public BranchDefinition(final String _branchName) {
    
    
        super();
    }
    
    
    public BranchType getBranchType() {
    
    
        return branchType;
    }
    
    
    public String getCustomer() {
    
    
        return customer;
    }
    
    
    public String getName() {
    
    
        return name;
    }
    
    
    public String getProject() {
    
    
        return project;
    }
    
    
    public void setBranchType(final BranchType _branchType) {
    
    
        branchType = _branchType;
    }
    
    
    public void setCustomer(final String _customer) {
    
    
        customer = _customer;
    }
    
    
    public void setName(final String _name) {
    
    
        name = _name;
        
        
    }
    
    
    /**
     * @param _project
     *            the project to set
     */
    public void setProject(final String _project) {
    
    
        project = _project;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "BranchDefinition [branchType="
                + branchType + ", customer=" + customer + ", name=" + name + ", project=" + project
                + "]";
    }
    
    
}
