
package org.komea.product.database.dto;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DepartmentDto implements Serializable {
    
    private static final Logger LOGGER           = Logger.getLogger(DepartmentDto.class.getName());
    private static final long   serialVersionUID = 1L;
    private String              key;
    private String              name;
    private String              description;
    private Map<String, String> teams = new HashMap<String, String>(0);
    private Map<String, String> persons = new HashMap<String, String>(0);
    
    public DepartmentDto() {
    
    }
    
    public DepartmentDto(final String key, final String name, final String description, final Map<String, String> teams,
            final Map<String, String> persons) {
    
        this.key = key;
        this.name = name;
        this.description = description;
        this.teams = teams;
        this.persons = persons;
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
    
    public Map<String, String> getTeams() {
    
        return teams;
    }
    
    public void setTeams(final Map<String, String> teams) {
    
        this.teams = teams;
    }
    
    public Map<String, String> getPersons() {
    
        return persons;
    }
    
    public void setPersons(final Map<String, String> persons) {
    
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "DepartmentDto{" + "key=" + key + ", name=" + name + ", description=" + description
                + ", teams=" + teams + ", persons=" + persons + ", type=" + type + '}';
    }
    
}
