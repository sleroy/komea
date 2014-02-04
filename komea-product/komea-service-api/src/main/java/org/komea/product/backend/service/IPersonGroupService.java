
package org.komea.product.backend.service;


import java.util.List;

import org.komea.product.database.model.PersonGroup;

/**
 * Komea service to manage person groups
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 4 févr. 2014
 */
public interface IPersonGroupService
{
    
    /**
     * This method retun the complete department list
     * 
     * @return the department list
     */
    List<PersonGroup> getAllDepartments();
    
}
