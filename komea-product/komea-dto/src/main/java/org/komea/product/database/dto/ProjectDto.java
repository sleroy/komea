package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ProjectDto implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ProjectDto.class.getName());
    private static final long serialVersionUID = 1L;
    private String projectKey;
    private String name;
    private String description;
    private String customer;
    private Map<String, String> links;
    private List<String> tags;
    private Map<String, String> associatedPersons;
    private Map<String, String> associatedTeams;

    public ProjectDto() {
    }

    public ProjectDto(String projectKey, String name, String description, String customer,
            Map<String, String> links, List<String> tags, Map<String, String> associatedPersons, Map<String, String> associatedTeams) {
        this.projectKey = projectKey;
        this.name = name;
        this.description = description;
        this.customer = customer;
        this.links = links;
        this.tags = tags;
        this.associatedPersons = associatedPersons;
        this.associatedTeams = associatedTeams;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getAssociatedPersons() {
        return associatedPersons;
    }

    public void setAssociatedPersons(Map<String, String> associatedPersons) {
        this.associatedPersons = associatedPersons;
    }

    public Map<String, String> getAssociatedTeams() {
        return associatedTeams;
    }

    public void setAssociatedTeams(Map<String, String> associatedTeams) {
        this.associatedTeams = associatedTeams;
    }

}
