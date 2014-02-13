
package org.komea.product.database.dto;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class TeamDto implements Serializable {
    
    private static final Logger  LOGGER           = Logger.getLogger(TeamDto.class.getName());
    private static final long    serialVersionUID = 1L;
    private String               key;
    private String               name;
    private String               description;
    private Map<String, String> projects = new HashMap<String, String>(0);
    private Map<String, String> persons = new HashMap<String, String>(0);
    private Pair<String, String> department;
    
    public TeamDto() {
    
    }
    
    public TeamDto(final String key, final String name, final String description, final Map<String, String> projects,
            final Map<String, String> persons, final Pair<String, String> department) {
    
        this.key = key;
        this.name = name;
        this.description = description;
        this.projects = projects;
        this.persons = persons;
        this.department = department;
    }
    
    public String getKey() {
    
        return key;
    }
    
    public void setKey(final String key) {
    
        this.key = key;
    }
    
    public String getName() {
    
        return name;
    }
    
    public void setName(final String name) {
    
        this.name = name;
    }
    
    public String getDescription() {
    
        return description;
    }
    
    public void setDescription(final String description) {
    
        this.description = description;
    }
    
    public Map<String, String> getProjects() {
    
        return projects;
    }
    
    public void setProjects(final Map<String, String> projects) {
    
        this.projects = projects;
    }
    
    public Map<String, String> getPersons() {
    
        return persons;
    }
    
    public void setPersons(final Map<String, String> persons) {
    
        this.persons = persons;
    }
    
    public Pair<String, String> getDepartment() {
    
        return department;
    }
    
    public void setDepartment(final Pair<String, String> department) {
    
        this.department = department;
    }

    @Override
    public String toString() {
        return "TeamDto{" + "key=" + key + ", name=" + name + ", description=" + description
                + ", projects=" + projects + ", persons=" + persons
                + ", department=" + department + ", type=" + type + '}';
    }
    
}
