/**
 * 
 */

package org.komea.product.plugins.git.model;



import org.komea.product.database.model.Customer;
import org.komea.product.database.model.Project;



/**
 * This class defines a branch definition.
 * 
 * @author sleroy
 */
public class BranchDefinition
{
    
    
    private Customer customer;
    
    private Project  project;
    
    
    
    public Customer getCustomer() {
    
    
        return customer;
    }
    
    
    public Project getProject() {
    
    
        return project;
    }
    
    
    public void setCustomer(final Customer _customer) {
    
    
        customer = _customer;
    }
    
    
    public void setProject(final Project _project) {
    
    
        project = _project;
    }
}
