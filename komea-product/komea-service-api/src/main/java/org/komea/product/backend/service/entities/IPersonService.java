package org.komea.product.backend.service.entities;

import java.util.List;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;

/**
 * Komea service to manage person
 * <p>
 *
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 * @version $Revision: 1.0 $
 */
public interface IPersonService extends IGenericService<Person, Integer, PersonCriteria> {

    /**
     * get persons of a group
     *
     * @param groupId id of the group
     * @return persons
     */
    public List<Person> getPersonsOfPersonGroup(Integer groupId);

    /**
     * Method personsToBaseEntities.
     *
     * @param persons List<Person>
     * @return List<BaseEntityDto>
     */
    List<BaseEntityDto> convertPersonsToBaseEntities(List<Person> persons);

    /**
     * Find or create an user based on an email.
     *
     * @param _email the email.
     */
    Person findOrCreatePersonByEmail(String _email);

    /**
     * This method list all person
     *
     * @return the person list
     */
    List<PersonDto> convertAllPersonsIntoPersonDTO();

    /**
     * Save or update information of a person.
     *
     * @param _person
     * @param _selectedProject
     * @param _selectedRole
     * @param _personGroup
     */
    void saveOrUpdate(
            Person _person,
            Project _selectedProject,
            PersonRole _selectedRole,
            PersonGroup _personGroup);

    /**
     * get persons of a project
     *
     * @param _projectId id of the project
     * @return list of persons
     */
    List<Person> getPersonsOfProject(final Integer _projectId);

}
