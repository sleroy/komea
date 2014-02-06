package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

public class PersonDto implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(PersonDto.class.getName());
    private static final long serialVersionUID = 1L;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Pair<String, String> team;
    private Pair<String, String> department;
    private Map<String, String> projects;

    public PersonDto() {
    }

    public PersonDto(String login, String email, String firstName, String lastName,
            String role, Pair<String, String> team, Pair<String, String> department, Map<String, String> projects) {
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.team = team;
        this.department = department;
        this.projects = projects;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Pair<String, String> getTeam() {
        return team;
    }

    public void setTeam(Pair<String, String> team) {
        this.team = team;
    }

    public Pair<String, String> getDepartment() {
        return department;
    }

    public void setDepartment(Pair<String, String> department) {
        this.department = department;
    }

    public Map<String, String> getProjects() {
        return projects;
    }

    public void setProjects(Map<String, String> projects) {
        this.projects = projects;
    }

}
