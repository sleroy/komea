package org.komea.product.backend.service.entities;

import java.util.List;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;

/**
 * Komeap projects manager
 * <p>
 *
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 * @version $Revision: 1.0 $
 */
public interface IProjectService extends IGenericService<Project, Integer, ProjectCriteria> {

    /**
     * This method list all stored projects
     *
     * @return List<ProjectDto>
     */
    List<ProjectDto> getAllProjects();

    /**
     * @param _projectKey
     * @return
     */
    Project getOrCreate(String _projectKey);

    /**
     * This method list all stored projects
     *
     * @return List<ProjectDto>
     */
    List<Project> getAllProjectsEntities();

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
     * Method projectsToBaseEntities.
     *
     * @param projects List<Project>
     * @return List<BaseEntityDto>
     */
    List<BaseEntityDto> projectsToBaseEntities(List<Project> projects);

    /**
     * get projects of a personGroup
     *
     * @param _personGroupId id of the personGroup
     * @return list of projects
     */
    List<Project> getProjectsOfPersonGroup(final Integer _personGroupId);

    /**
     * get projects of a person
     *
     * @param _personId id of the person
     * @return list of projects
     */
    List<Project> getProjectsOfPerson(final Integer _personId);

}
