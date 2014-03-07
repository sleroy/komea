package org.komea.product.backend.service.entities;

import java.util.List;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.BaseEntity;
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
     * This method list all person
     *
     * @return the person list
     */
    List<PersonDto> getPersonList();

    /**
     * Method getPersons.
     *
     * @param logins List<String>
     * @return List<Person>
     */
    List<Person> getPersons(final List<String> logins);

    /**
     * Method personsToBaseEntities.
     *
     * @param persons List<Person>
     * @return List<BaseEntity>
     */
    List<BaseEntity> personsToBaseEntities(List<Person> persons);

    /**
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
     * get persons of a group
     *
     * @param groupId id of the group
     * @return persons
     */
    List<Person> getPersonsOfPersonGroup(Integer groupId);

    /**
     * get persons of a project
     *
     * @param _projectId id of the project
     * @returnlist of persons
     */
    List<Person> getPersonsOfProject(Integer _projectId);

}
