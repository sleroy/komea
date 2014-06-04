/**
 * 
 */

package org.komea.product.plugins.projectmanagement.model;



import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;



/**
 * @author sleroy
 */
public interface ITaskPlugin
{
    
    
    /**
     * Returns the list of tasks
     * 
     * @param _person
     *            the person
     * @return the list of tasks
     */
    ITaskManagement getTasks(Person _person);
    
    
    /**
     * Returns the list of tasks for a person group
     * 
     * @param _personGroup
     *            the projet
     * @return the list of tasks
     */
    ITaskManagement getTasks(PersonGroup _personGroup);
    
    
    /**
     * Returns the list of tasks for a project.
     * 
     * @param _project
     *            the project
     * @return the list of tasks
     */
    ITaskManagement getTasks(Project _project);
}
