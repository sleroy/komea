/**
 * 
 */

package org.komea.product.backend.service.entities;



import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 *
 * @version $Revision: 1.0 $
 */
public interface IProjectPersonService
{
    
    
    /**
     * Method updateProjectPersonLink.
     * @param _project Project
     * @param _person Person
     */
    public abstract void updateProjectPersonLink(Project _project, Person _person);
    
}
