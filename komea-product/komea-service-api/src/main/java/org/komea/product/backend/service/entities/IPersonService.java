
package org.komea.product.backend.service.entities;


import java.util.List;

import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.Project;

/**
 * Komea service to manage person
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 */
public interface IPersonService {
    
    /**
     * This method list all person
     * 
     * @return the person list
     */
    List<PersonDto> getPersonList();
    
    /**
     * This method return the list of projects associate to a person
     * 
     * @param _personId
     *            the person if
     * @return the project list
     */
    List<Project> getProjectsAssociateToAPerson(Integer _personId);
    
}
