/**
 * 
 */

package org.komea.product.backend.service.entities;



import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;



/**
 * This interface defines a service to manage links between a project and persons.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface IProjectPersonService
{
    
    
    /**
     * Method updateProjectPersonLink.
     * 
     * @param _project
     *            Project
     * @param _person
     *            Person
     */
    public void updatePersonToProjectLink(Project _project, Person _person);
    
}
