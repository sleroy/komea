package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

public class DepartmentDto implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(DepartmentDto.class.getName());
    private static final long serialVersionUID = 1L;
    private String key;
    private String name;
    private String description;
    private Map<String, String> teams;
    private Map<String, String> persons;
    private String type;

    public DepartmentDto() {
    }

    public DepartmentDto(String key, String name, String description, Map<String, String> teams, Map<String, String> persons, String type) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.teams = teams;
        this.persons = persons;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getTeams() {
        return teams;
    }

    public void setTeams(Map<String, String> teams) {
        this.teams = teams;
    }

    public Map<String, String> getPersons() {
        return persons;
    }

    public void setPersons(Map<String, String> persons) {
        this.persons = persons;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
