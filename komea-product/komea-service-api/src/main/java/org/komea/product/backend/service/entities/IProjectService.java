package org.komea.product.backend.service.entities;

import java.util.List;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;

/**
 * Komeap projects manager
 * <p>
 *
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 */
public interface IProjectService {

    /**
     * This method list all stored projects
     *
     * @return
     */
    List<ProjectDto> getAllProjects();

    /**
     * This method get all person associate to a project
     *
     * @param _projectID the project id
     * @return the person list
     */
    List<Person> getPersonsAssociateToProject(int _projectID);

    /**
     * This method list all links associate to a project
     *
     * @param _projectId the project if
     * @return the link list
     */
    List<Link> getProjectLinks(Integer _projectId);

    /**
     * This method list tags associate to a project
     *
     * @param _projectId the project id
     * @return the tag list
     */
    List<String> getProjectTags(Integer _projectId);

    /**
     * This method list all teams associate to a project
     *
     * @param _projectIDthe project id
     * @return the team list
     */
    List<PersonGroup> getTeamsAssociateToProject(Integer _projectID);

    List<Project> getProjects(final List<String> projectKeys);

    List<BaseEntity> projectsToBaseEntities(List<Project> projects);

}
