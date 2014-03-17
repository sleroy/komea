/**
 *
 */
package org.komea.product.backend.service.entities;

import java.util.List;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;

/**
 * This interface defines a service to manage links between a project and
 * persons.
 *
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface IProjectPersonService {

    /**
     * Method updateProjectsOfPerson.
     *
     * @param _projects Projects
     * @param _person Person
     */
    public void updateProjectsOfPerson(List<Project> _projects, Person _person);

    /**
     * Method updateProjectsOfPerson.
     *
     * @param _persons persons
     * @param _project project
     */
    public void updatePersonsOfProject(List<Person> _persons, Project _project);

    /**
     * get ids of persons associated of a project
     *
     * @param _projectId id of project
     * @return list of person ids
     */
    List<HasProjectPersonKey> getPersonIdsOfProject(Integer _projectId);

    /**
     * get ids of projects associated of a person
     *
     * @param _personId id of person
     * @return list of project ids
     */
    List<HasProjectPersonKey> getProjectIdsOfPerson(Integer _personId);

}
