package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class TeamDto implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(TeamDto.class.getName());
    private static final long serialVersionUID = 1L;
    private String key;
    private String name;
    private String description;
    private Map<String, String> projects = new HashMap<String, String>(0);
    private Map<String, String> persons = new HashMap<String, String>(0);
    private Pair<String, String> department;
    private String type;

    public TeamDto() {
    }

    public TeamDto(String key, String name, String description, Map<String, String> projects, Map<String, String> persons, Pair<String, String> department, String type) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.projects = projects;
        this.persons = persons;
        this.department = department;
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

    public Map<String, String> getProjects() {
        return projects;
    }

    public void setProjects(Map<String, String> projects) {
        this.projects = projects;
    }

    public Map<String, String> getPersons() {
        return persons;
    }

    public void setPersons(Map<String, String> persons) {
        this.persons = persons;
    }

    public Pair<String, String> getDepartment() {
        return department;
    }

    public void setDepartment(Pair<String, String> department) {
        this.department = department;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TeamDto{" + "key=" + key + ", name=" + name + ", description=" + description
                + ", projects=" + projects + ", persons=" + persons
                + ", department=" + department + ", type=" + type + '}';
    }

}
