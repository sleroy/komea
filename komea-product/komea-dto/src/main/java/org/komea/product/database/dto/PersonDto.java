package org.komea.product.database.dto;

import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.komea.product.database.model.Project;

/**
 * Komea to get persin information
 * <p>
 *
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 */
public class PersonDto implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(PersonDto.class.getName());
    private static final long serialVersionUID = 1L;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    /**
     * <team Key, team name>
     */
    private Pair<String, String> team;

    /**
     * <department key, department name>
     */
    private Pair<String, String> department;

    /**
     * <project key, project name>
     */
    private Map<String, String> projects = new HashMap<String, String>(0);
    private Integer id;

    public PersonDto() {

        projects = Maps.newHashMap();
    }

    /**
     * This method return the number of project the person is associate
     *
     * @return the number of project
     */
    public int nbProject() {

        return projects.size();
    }

    /**
     * This method associate a project to this person
     *
     * @param _project the project to associate
     */
    public void associateToProject(final Project _project) {

        projects.put(_project.getProjectKey(), _project.getName());
    }

    /**
     * This method associate a project list to this person
     *
     * @param _projectList the tptoject list to associate
     */
    public void associateToProjectList(final Collection<Project> _projectList) {

        for (Project project : _projectList) {
            associateToProject(project);
        }
    }

    /**
     * This method list all project keys associate to this person
     *
     * @return the project key list
     */
    public Set<String> projectKeyList() {

        return projects.keySet();
    }

    /**
     * This method list all project name associate to this person
     *
     * @return the project name list
     */
    public Collection<String> projectNameList() {

        return projects.values();
    }

    public PersonDto(final String login, final String email, final String firstName, final String lastName, final String role,
            final Pair<String, String> team, final Pair<String, String> department, final Map<String, String> projects) {

        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.team = team;
        this.department = department;
        this.projects = projects;
    }

    public Pair<String, String> getDepartment() {

        return department;
    }

    public String getEmail() {

        return email;
    }

    public String getFirstName() {

        return firstName;
    }

    public Integer getId() {

        return id;
    }

    public String getLastName() {

        return lastName;
    }

    public String getLogin() {

        return login;
    }

    public Map<String, String> getProjects() {

        return projects;
    }

    public String getRole() {

        return role;
    }

    public Pair<String, String> getTeam() {

        return team;
    }

    /**
     * This method check if person has a team
     *
     * @return true or false
     */
    public boolean hasTeam() {

        return team != null;
    }

    /**
     * This method check if person has a department
     *
     * @return true or false
     */
    public boolean hasDepartment() {

        return department != null;
    }

    /**
     * This method return the team key or null
     *
     * @return the team key
     */
    public String teamKey() {

        return hasTeam() ? team.getKey() : null;
    }

    /**
     * This method return the department key or null
     *
     * @return the department key
     */
    public String departmentKey() {

        return hasDepartment() ? department.getKey() : null;
    }

    /**
     * This method return the department name or null
     *
     * @return the department name
     */
    public String departmentName() {

        return hasDepartment() ? department.getValue() : null;
    }

    /**
     * This method return the team name or null
     *
     * @return the team name
     */
    public String teamName() {

        return hasTeam() ? team.getValue() : null;
    }

    public void setDepartment(final Pair<String, String> department) {

        this.department = department;
    }

    public void setEmail(final String email) {

        this.email = email;
    }

    public void setFirstName(final String firstName) {

        this.firstName = firstName;
    }

    public void setId(final Integer _id) {

        id = _id;
    }

    public void setLastName(final String lastName) {

        this.lastName = lastName;
    }

    public void setLogin(final String login) {

        this.login = login;
    }

    public void setProjects(final Map<String, String> projects) {

        this.projects = projects;
    }

    public void setRole(final String role) {

        this.role = role;
    }

    public void setTeam(final Pair<String, String> team) {

        this.team = team;
    }

    @Override
    public String toString() {
        return "PersonDto{" + "login=" + login + ", email=" + email + ", firstName=" + firstName
                + ", lastName=" + lastName + ", role=" + role + ", team=" + team
                + ", department=" + department + ", projects=" + projects + ", id=" + id + '}';
    }

}
