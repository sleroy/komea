/**
 * 
 */

package org.komea.product.plugins.timemanagement.model;



import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;



/**
 * @author sleroy
 */
public interface ITimeManagementPlugin
{
    
    
    /**
     * Returns the time management table for a project
     * 
     * @param _project
     *            the project
     * @return the time management table .
     */
    ITimeManagementTable getTimeManagement(Person _project);
    
    
    /**
     * Returns the time management table for a project
     * 
     * @param _project
     *            the project
     * @return the time management table .
     */
    ITimeManagementTable getTimeManagement(PersonGroup _project);
    
    
    /**
     * Returns the time management table for a project
     * 
     * @param _project
     *            the project
     * @return the time management table .
     */
    ITimeManagementTable getTimeManagement(Project _project);
    
    
}
