/**
 * 
 */

package org.komea.product.backend.service.entities;



import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;



/**
 * This interface holds the functionalities required to handle person roles (CRUD operations).
 * 
 * @author sleroy
 */
public interface IPersonRoleService extends
        IGenericService<PersonRole, Integer, PersonRoleCriteria>
{
    
    
    /**
     * Returns the admin role
     * 
     * @return the admin role.
     */
    PersonRole getAdminRole();
    
    
    /**
     * Returns the default user role
     * 
     * @return the default user role.
     */
    PersonRole getDefaultUserRole();
    //
}
