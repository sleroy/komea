package org.komea.product.database.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;

/**
 * decribe information on a project
 * <p>
 *
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 */
public class ProjectDto implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ProjectDto.class.getName());
    private static final long serialVersionUID = 1L;
    private String projectKey;
    private String name;
    private String description;
    private String customer;

    /**
     * <name,url>
     */
    private Map<String, String> links;

    private List<String> tags;

    /**
     * <login, firstname lastname>
     */
    private Map<String, String> associatedPersons = new HashMap<String, String>(0);

    /**
     * <Team key, team name>
     */
    private Map<String, String> associatedTeams = new HashMap<String, String>(0);

    public ProjectDto() {

        associatedPersons = Maps.newHashMap();
        associatedTeams = Maps.newHashMap();
        links = Maps.newHashMap();
        tags = Lists.newArrayList();
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

    /**
     * This method associate a Link to this project
     *
     * @param _link th link to associate
     */
    public void addLink(final Link _link) {

        links.put(_link.getName(), _link.getUrl());
    }

    /**
     * This method associate a list a link to this project
     *
     * @param _linkList the link list to associate
     */
    public void addLinkList(final List<Link> _linkList) {

        for (Link link : _linkList) {

            addLink(link);
        }
    }

    public void associatePerson(final Person _person) {

        associatedPersons.put(_person.getLogin(), _person.getFirstName() + " " + _person.getLastName());
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

    public Map<String, String> getAssociatedPersons() {

        return associatedPersons;
    }

    public Map<String, String> getAssociatedTeams() {

        return associatedTeams;
    }

    /**
     * This method
     *
     * @return
     */
    public String getCustomer() {

        return customer;
    }

    /**
     * project desciption
     *
     * @return the project description
     */
    public String getDescription() {

        return description;
    }

    public Map<String, String> getLinks() {

        return links;
    }

    /**
     * This method get the link url with this links name
     *
     * @param _linkName link name
     * @return the link url
     */
    public String getLinksUrl(final String _linkName) {

        return links.get(_linkName);
    }

    /**
     * the project name
     *
     * @return project name
     */
    public String getName() {

        return name;
    }

    /**
     * This method return a person name (firstname lastname)
     *
     * @param _login the person login
     * @return the person name
     */
    public String getPersonName(final String _login) {

        return associatedPersons.get(_login);
    }

    /**
     * This method return the project key
     *
     * @return the project key
     */
    public String getProjectKey() {

        return projectKey;
    }

    /**
     * the tags list
     *
     * @return all tags
     */
    public List<String> getTags() {

        return tags;
    }

    /**
     * This method a team name
     *
     * @param _key the team key
     * @return the team name
     */
    public String getTeamName(final String _key) {

        return associatedTeams.get(_key);
    }

    /**
     * This method return all links name associate to this projects
     *
     * @return the links name list
     */
    public Set<String> linksNameList() {

        return links.keySet();
    }

    /**
     * This method return all the person login associate to this projects
     *
     * @return
     */
    public Set<String> loginList() {

        return associatedPersons.keySet();
    }

    public int nbAssociatedPerson() {

        return associatedPersons.size();
    }

    /**
     * nb links associate to this projects
     *
     * @return
     */
    public int nbLinks() {

        return links.size();
    }

    /**
     * This method return the number of teams associate to this project
     *
     * @return the number of team
     */
    public int nbTeams() {

        return associatedTeams.size();
    }

    /**
     * This method list all person (firstname name) working in this project
     *
     * @return
     */
    public Collection<String> personNameList() {

        return associatedPersons.values();
    }

    public void setAssociatedPersons(final Map<String, String> associatedPersons) {

        this.associatedPersons = associatedPersons;
    }

    public void setAssociatedTeams(final Map<String, String> associatedTeams) {

        this.associatedTeams = associatedTeams;
    }

    public void setCustomer(final String customer) {

        this.customer = customer;
    }

    public void setDescription(final String description) {

        this.description = description;
    }

    public void setLinks(final Map<String, String> links) {

        this.links = links;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public void setProjectKey(final String projectKey) {

        this.projectKey = projectKey;
    }

    public void setTags(final List<String> tags) {

        this.tags = tags;
    }

    /**
     * This method list all team keys associate to this project
     *
     * @return the team key list
     */
    public Set<String> teamKeyList() {

        return associatedTeams.keySet();
    }

    /**
     * This method list all team name associate to this project
     *
     * @return the team name list
     */
    public Collection<String> teamNameList() {

        return associatedTeams.values();
    }

    /**
     * This method list all url links associate to this project
     *
     * @return the url list
     */
    public Collection<String> urlsLinkList() {

        return links.values();
    }

    @Override
    public String toString() {
        return "ProjectDto{" + "projectKey=" + projectKey + ", name=" + name
                + ", description=" + description + ", customer=" + customer
                + ", links=" + links + ", tags=" + tags + ", associatedPersons=" + associatedPersons
                + ", associatedTeams=" + associatedTeams + '}';
    }

}
