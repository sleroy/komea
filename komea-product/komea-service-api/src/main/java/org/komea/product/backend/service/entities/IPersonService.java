
package org.komea.product.backend.service.entities;



import java.util.List;

import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;



/**
 * Komea service to manage person
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 * @version $Revision: 1.0 $
 */
public interface IPersonService
{
    
    
    /**
     * This method list all person
     * 
     * @return the person list
     */
    List<PersonDto> getPersonList();
    
    
    /**
     * Method getPersons.
     * 
     * @param logins
     *            List<String>
     * @return List<Person>
     */
    List<Person> getPersons(final List<String> logins);
    
    
    /**
     * This method return the list of projects associate to a person
     * 
     * @param _personId
     *            the person if
     * @return the project list
     */
    List<Project> getProjectsAssociateToAPerson(Integer _personId);
    
    
    /**
     * Method personsToBaseEntities.
     * 
     * @param persons
     *            List<Person>
     * @return List<BaseEntity>
     */
    List<BaseEntity> personsToBaseEntities(List<Person> persons);
    
}
