/**
 * 
 */

package org.komea.product.backend.forms;



import java.util.List;

import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.Project;



/**
 * @author sleroy
 */
public class PersonFormData
{
    
    
    private List<PersonGroup> departments;
    
    
    private List<PersonRole>  personRoles;
    
    
    private List<Project>     projects;
    
    
    private List<PersonGroup> teams;
    
    
    
    /**
     * 
     */
    public PersonFormData() {
    
    
        // TODO Auto-generated constructor stub
    }
    
    
    public PersonFormData(
            final List<PersonGroup> _departments,
            final List<PersonRole> _personRoles,
            final List<Project> _projects,
            final List<PersonGroup> _teams) {
    
    
        super();
        departments = _departments;
        personRoles = _personRoles;
        projects = _projects;
        teams = _teams;
    }
    
    
    /**
     * @return the departments
     */
    public List<PersonGroup> getDepartments() {
    
    
        return departments;
    }
    
    
    /**
     * @return the personRoles
     */
    public List<PersonRole> getPersonRoles() {
    
    
        return personRoles;
    }
    
    
    /**
     * @return the projects
     */
    public List<Project> getProjects() {
    
    
        return projects;
    }
    
    
    /**
     * @return the teams
     */
    public List<PersonGroup> getTeams() {
    
    
        return teams;
    }
    
    
    /**
     * @param _departments
     *            the departments to set
     */
    public void setDepartments(final List<PersonGroup> _departments) {
    
    
        departments = _departments;
    }
    
    
    /**
     * @param _personRoles
     *            the personRoles to set
     */
    public void setPersonRoles(final List<PersonRole> _personRoles) {
    
    
        personRoles = _personRoles;
    }
    
    
    /**
     * @param _projects
     *            the projects to set
     */
    public void setProjects(final List<Project> _projects) {
    
    
        projects = _projects;
    }
    
    
    /**
     * @param _teams
     *            the teams to set
     */
    public void setTeams(final List<PersonGroup> _teams) {
    
    
        teams = _teams;
    }
    
}
