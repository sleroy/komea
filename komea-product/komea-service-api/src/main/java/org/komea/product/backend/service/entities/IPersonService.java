package org.komea.product.backend.service.entities;

import java.util.List;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
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
     * Tests if the user is existing (from his email)
     *
     * @param _email the email
     * @return true if this user is present in the database
     */
    public boolean existUserByEmail(String _email);

    /**
     * Find a user by its email.
     *
     * @param _email the email
     * @return null if not found.
     */
    public Person findUserByEmail(String _email);

    /**
     * get persons of a group
     *
     * @param groupId id of the group
     * @return persons
     */
    public List<Person> getPersonsOfPersonGroup(Integer groupId);

    /**
     * This method list all person
     *
     * @return the person list
     */
    List<PersonDto> convertAllPersonsIntoPersonDTO();

    /**
     * Method personsToBaseEntities.
     *
     * @param persons List<Person>
     * @return List<BaseEntityDto>
     */
    List<BaseEntityDto> convertPersonsToBaseEntities(List<Person> persons);

    /**
     * delete person
     *
     * @param _person person
     */
    void deletePerson(final Person _person);

    /**
     * Find or create an user based on an email.
     *
     * @param _email the email.
     */
    Person findOrCreatePersonByEmail(String _email);

    /**
     * get persons of a project
     *
     * @param _projectId id of the project
     * @return list of persons
     */
    List<Person> getPersonsOfProject(final Integer _projectId);

    /**
     * Save or update information of a person.
     *
     * @param _person
     * @param _projects
     */
    void saveOrUpdatePerson(Person _person, List<Project> _projects);

}
