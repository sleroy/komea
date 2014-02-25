
package org.komea.product.backend.service.entities;



import java.util.List;

import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
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
public interface IProjectService extends IGenericService<Project, Integer, ProjectCriteria>
{
    
    
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
     * This method get all person associate to a project
     * 
     * @param _projectID
     *            the project id
     * @return the person list
     */
    List<Person> getPersonsAssociateToProject(int _projectID);
    
    
    /**
     * This method list all links associate to a project
     * 
     * @param _projectId
     *            the project if
     * @return the link list
     */
    List<Link> getProjectLinks(Integer _projectId);
    
    
    /**
     * Method getProjects.
     * 
     * @param projectKeys
     *            List<String>
     * @return List<Project>
     */
    List<Project> getProjects(final List<String> projectKeys);
    
    
    /**
     * This method list tags associate to a project
     * 
     * @param _projectId
     *            the project id
     * @return the tag list
     */
    List<String> getProjectTags(Integer _projectId);
    
    
    /**
     * This method list all teams associate to a project
     * 
     * @param _projectID
     *            Integer
     * @return the team list
     */
    List<PersonGroup> getTeamsAssociateToProject(Integer _projectID);
    
    
    /**
     * Method projectsToBaseEntities.
     * 
     * @param projects
     *            List<Project>
     * @return List<BaseEntity>
     */
    List<BaseEntity> projectsToBaseEntities(List<Project> projects);
    
}
