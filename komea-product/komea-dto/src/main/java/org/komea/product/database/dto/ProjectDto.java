
package org.komea.product.database.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;

public class ProjectDto implements Serializable {
    
    private static final Logger LOGGER           = Logger.getLogger(ProjectDto.class.getName());
    private static final long   serialVersionUID = 1L;
    private String              projectKey;
    private String              name;
    private String              description;
    private String              customer;
    private Map<String, String> links;
    private List<String>        tags;
    /**
     * <login, firstname lastname>
     */
    private Map<String, String> associatedPersons;
    
    /**
     * <Team key, team name>
     */
    private Map<String, String> associatedTeams;
    
    public ProjectDto() {
    
    }
    
    public ProjectDto(final String projectKey, final String name, final String description, final String customer,
            final Map<String, String> links, final List<String> tags, final Map<String, String> associatedPersons,
            final Map<String, String> associatedTeams) {
    
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
    
    public void setProjectKey(final String projectKey) {
    
        this.projectKey = projectKey;
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
    
    public String getCustomer() {
    
        return customer;
    }
    
    public void setCustomer(final String customer) {
    
        this.customer = customer;
    }
    
    public Map<String, String> getLinks() {
    
        return links;
    }
    
    public void setLinks(final Map<String, String> links) {
    
        this.links = links;
    }
    
    public List<String> getTags() {
    
        return tags;
    }
    
    public void setTags(final List<String> tags) {
    
        this.tags = tags;
    }
    
    public Map<String, String> getAssociatedPersons() {
    
        return associatedPersons;
    }
    
    public void setAssociatedPersons(final Map<String, String> associatedPersons) {
    
        this.associatedPersons = associatedPersons;
    }
    
    public Map<String, String> getAssociatedTeams() {
    
        return associatedTeams;
    }
    
    public void setAssociatedTeams(final Map<String, String> associatedTeams) {
    
        this.associatedTeams = associatedTeams;
    }
    
    public void associatePerson(final Person _person) {
    
        associatedPersons.put(_person.getLogin(), _person.getFirstName() + _person.getLastName());
    }
    
    public void associatePersonList(final List<Person> _personlist) {
    
        for (Person person : _personlist) {
            associatePerson(person);
        }
    }
    
    public void associateTeam(final PersonGroup _group) {
    
        associatedTeams.put(_group.getPersonGroupKey(), _group.getName());
    }
    
    public void associateTeamList(final List<PersonGroup> _teamlist) {
    
        for (PersonGroup team : _teamlist) {
            associateTeam(team);
        }
    }
    
}
