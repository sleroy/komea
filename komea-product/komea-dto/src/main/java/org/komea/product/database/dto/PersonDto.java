
package org.komea.product.database.dto;



import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;



public class PersonDto implements Serializable
{
    
    
    private static final Logger  LOGGER           = Logger.getLogger(PersonDto.class.getName());
    private static final long    serialVersionUID = 1L;
    private String               login;
    private String               email;
    private String               firstName;
    private String               lastName;
    private String               role;
    private Pair<String, String> team;
    private Pair<String, String> department;
    private Map<String, String>  projects;
    private Integer              id;
    
    
    
    public PersonDto() {
    
    
    }
    
    
    public PersonDto(
            final String login,
            final String email,
            final String firstName,
            final String lastName,
            final String role,
            final Pair<String, String> team,
            final Pair<String, String> department,
            final Map<String, String> projects) {
    
    
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
    
}
