
package org.komea.product.backend.service.entities;



import java.util.List;

import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.komea.product.database.model.Tag;



/**
 * Komeap projects manager
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 * @version $Revision: 1.0 $
 */
public interface IProjectService extends IGenericService<Project, Integer, ProjectCriteria>
{
    
    
    /**
     * delete project
     * 
     * @param _project
     *            project
     */
    void deleteProject(final Project _project);
    
    
    /**
     * This method list all stored projects
     * 
     * @return List<ProjectDto>
     */
    List<ProjectDto> getAllProjectsAsDtos();
    
    
    /**
     * This method list all stored projects
     * 
     * @return List<ProjectDto>
     */
    List<Project> getAllProjectsEntities();
    
    
    /**
     * @param _projectKey
     * @return
     */
    Project getOrCreate(String _projectKey);
    
    
    /**
     * This method list all links associate to a project
     * 
     * @param _projectId
     *            the project if
     * @return the link list
     */
    List<Link> getProjectLinks(Integer _projectId);
    
    
    /**
     * get projects of a person
     * 
     * @param _personId
     *            id of the person
     * @return list of projects
     */
    List<Project> getProjectsOfAMember(final Integer _personId);
    
    
    /**
     * get projects of a personGroup
     * 
     * @param _personGroupId
     *            id of the personGroup
     * @return list of projects
     */
    List<Project> getProjectsOfPersonGroup(Integer _personGroupId);
    
    
    /**
     * Returns the list of projects from a person group by processing recursively all the groups.
     * 
     * @param _personGroupId
     *            the person group id to start the collection.
     * @return the list of projects.
     */
    List<Project> getProjectsOfPersonGroupRecursively(Integer _personGroupId);
    
    
    /**
     * This method list tags associate to a project
     * 
     * @param _projectId
     *            the project id
     * @return the tag list
     */
    List<String> getProjectTags(Integer _projectId);
    
    
    /**
     * @param _project
     * @param _tags
     * @param _persons
     * @param _links
     * @param _teams
     */
    void saveOrUpdateProject(
            Project _project,
            List<Tag> _tags,
            List<Person> _persons,
            List<Link> _links,
            List<PersonGroup> _teams);
    
}
