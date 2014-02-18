
package org.komea.product.backend.service.entities;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.dao.HasProjectPersonDao;
import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.dao.HasProjectTagDao;
import org.komea.product.database.dao.LinkDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dao.TagDao;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonGroupCriteria;
import org.komea.product.database.model.HasProjectPersonGroupKey;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.HasProjectTagCriteria;
import org.komea.product.database.model.HasProjectTagKey;
import org.komea.product.database.model.Link;
import org.komea.product.database.model.LinkCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.komea.product.database.model.Tag;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {
    
    @InjectMocks
    private final IProjectService    projectService = new ProjectService();
    
    @Mock
    private ProjectDao               projectDAOmock;
    
    @Mock
    private HasProjectPersonDao      projectPersonDAOmock;
    
    @Mock
    private PersonDao                personDAOmock;
    
    @Mock
    private LinkDao                  linkDAOmock;
    
    @Mock
    private HasProjectTagDao         projectTagsDAOmock;
    
    @Mock
    private TagDao                   tagDAOmock;
    
    @Mock
    private HasProjectPersonGroupDao projectPersonGroupDAOmock;
    
    @Mock
    private PersonGroupDao           personGroupDAOmock;
    
    @Mock
    private CustomerDao              customerDAOmock;
    
    @Test
    public void testGetPersonsAssociateToProject() {
    
        HasProjectPersonKey hasProjectPersonKey = new HasProjectPersonKey(1, 1);
        List<HasProjectPersonKey> projectPersonList = Lists.newArrayList(hasProjectPersonKey);
        Mockito.when(projectPersonDAOmock.selectByCriteria(Matchers.any(HasProjectPersonCriteria.class))).thenReturn(projectPersonList);
        
        Mockito.when(personDAOmock.selectByPrimaryKey(hasProjectPersonKey.getIdPerson())).thenReturn(getPerson());
        
        List<Person> personList = projectService.getPersonsAssociateToProject(1);
        
        Assert.assertEquals(1, personList.size());
        Person person = personList.get(0);
        Assert.assertEquals(1, person.getId().intValue());
        Assert.assertEquals("lskywalker", person.getLogin());
        
        Mockito.verify(projectPersonDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonCriteria.class));
        
        Mockito.verify(personDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
    }
    
    @Test
    public void testGetPersonsAssociateToProjectWithNoPersons() {
    
        List<HasProjectPersonKey> projectPersonList = Lists.newArrayList();
        Mockito.when(projectPersonDAOmock.selectByCriteria(Matchers.any(HasProjectPersonCriteria.class))).thenReturn(projectPersonList);
        
        List<Person> personList = projectService.getPersonsAssociateToProject(1);
        
        Assert.assertEquals(0, personList.size());
        
        Mockito.verify(projectPersonDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonCriteria.class));
        
        Mockito.verify(personDAOmock, Mockito.times(0)).selectByPrimaryKey(Matchers.any(Integer.class));
    }
    
    @Test
    public void testGetPersonsAssociateToProjectWithNullPersons() {
    
        HasProjectPersonKey hasProjectPersonKey = new HasProjectPersonKey(1, 1);
        List<HasProjectPersonKey> projectPersonList = Lists.newArrayList(hasProjectPersonKey);
        Mockito.when(projectPersonDAOmock.selectByCriteria(Matchers.any(HasProjectPersonCriteria.class))).thenReturn(projectPersonList);
        
        Mockito.when(personDAOmock.selectByPrimaryKey(hasProjectPersonKey.getIdPerson())).thenReturn(null);
        
        List<Person> personList = projectService.getPersonsAssociateToProject(1);
        
        Assert.assertEquals(0, personList.size());
        
        Mockito.verify(projectPersonDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonCriteria.class));
        
        Mockito.verify(personDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
    }
    
    @Test
    public void testGetProjectLinks() {
    
        Mockito.when(linkDAOmock.selectByCriteria(Matchers.any(LinkCriteria.class))).thenReturn(getLinks());
        
        List<Link> links = projectService.getProjectLinks(1);
        
        Assert.assertEquals(1, links.size());
        Link link = links.get(0);
        Assert.assertEquals(1, link.getId().intValue());
        Assert.assertEquals(1, link.getIdProject().intValue());
        Assert.assertEquals("komea github site", link.getName());
        Assert.assertEquals("https://github.com/sleroy/komea", link.getUrl());
        
        Mockito.verify(linkDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(LinkCriteria.class));
        
    }
    
    @Test
    public void testGetProjectLinksNoLinks() {
    
        Mockito.when(linkDAOmock.selectByCriteria(Matchers.any(LinkCriteria.class))).thenReturn(new ArrayList<Link>(0));
        
        List<Link> links = projectService.getProjectLinks(1);
        
        Assert.assertEquals(0, links.size());
        
        Mockito.verify(linkDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(LinkCriteria.class));
        
    }
    
    @Test
    public void testGetProjectTags() {
    
        List<HasProjectTagKey> projectTags = Lists.newArrayList(new HasProjectTagKey(1, 1));
        Mockito.when(projectTagsDAOmock.selectByCriteria(Matchers.any(HasProjectTagCriteria.class))).thenReturn(projectTags);
        
        Mockito.when(tagDAOmock.selectByPrimaryKey(1)).thenReturn(getTag());
        
        List<String> tags = projectService.getProjectTags(1);
        
        Assert.assertEquals(1, tags.size());
        String tag = tags.get(0);
        Assert.assertEquals("kpi", tag);
        
        Mockito.verify(projectTagsDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectTagCriteria.class));
        
        Mockito.verify(tagDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
    }
    
    @Test
    public void testGetProjectTagsNoTags() {
    
        List<HasProjectTagKey> projectTags = Lists.newArrayList();
        Mockito.when(projectTagsDAOmock.selectByCriteria(Matchers.any(HasProjectTagCriteria.class))).thenReturn(projectTags);
        
        List<String> tags = projectService.getProjectTags(1);
        
        Assert.assertEquals(0, tags.size());
        
        Mockito.verify(projectTagsDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectTagCriteria.class));
        
        Mockito.verify(tagDAOmock, Mockito.times(0)).selectByPrimaryKey(1);
    }
    
    @Test
    public void testGetProjectTagsNullTags() {
    
        List<HasProjectTagKey> projectTags = Lists.newArrayList(new HasProjectTagKey(1, 1));
        Mockito.when(projectTagsDAOmock.selectByCriteria(Matchers.any(HasProjectTagCriteria.class))).thenReturn(projectTags);
        
        Mockito.when(tagDAOmock.selectByPrimaryKey(1)).thenReturn(null);
        
        List<String> tags = projectService.getProjectTags(1);
        
        Assert.assertEquals(0, tags.size());
        
        Mockito.verify(projectTagsDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectTagCriteria.class));
        
        Mockito.verify(tagDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
    }
    
    @Test
    public void testGetprojectTeams() {
    
        List<HasProjectPersonGroupKey> projectGroups = Lists.newArrayList(new HasProjectPersonGroupKey(1, 1));
        Mockito.when(projectPersonGroupDAOmock.selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class))).thenReturn(
                projectGroups);
        
        Mockito.when(personGroupDAOmock.selectByCriteria(Matchers.any(PersonGroupCriteria.class))).thenReturn(getTeams());
        
        List<PersonGroup> teams = projectService.getTeamsAssociateToProject(1);
        
        Assert.assertEquals(1, teams.size());
        PersonGroup team = teams.get(0);
        Assert.assertEquals(1, team.getId().intValue());
        Assert.assertEquals("KOMEA_TEAM", team.getPersonGroupKey());
        Assert.assertEquals(PersonGroupType.TEAM, team.getType());
        
        Mockito.verify(projectPersonGroupDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class));
        
        Mockito.verify(personGroupDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(PersonGroupCriteria.class));
        
    }
    
    @Test
    public void testGetprojectNoTeams() {
    
        List<HasProjectPersonGroupKey> projectGroups = Lists.newArrayList();
        Mockito.when(projectPersonGroupDAOmock.selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class))).thenReturn(
                projectGroups);
        
        List<PersonGroup> teams = projectService.getTeamsAssociateToProject(1);
        
        Assert.assertEquals(0, teams.size());
        Mockito.verify(projectPersonGroupDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class));
        
        Mockito.verify(personGroupDAOmock, Mockito.times(0)).selectByCriteria(Matchers.any(PersonGroupCriteria.class));
        
    }
    
    @Test
    public void testGetprojectTeamsNullTeams() {
    
        List<HasProjectPersonGroupKey> projectGroups = Lists.newArrayList(new HasProjectPersonGroupKey(1, 1));
        Mockito.when(projectPersonGroupDAOmock.selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class))).thenReturn(
                projectGroups);
        
        Mockito.when(personGroupDAOmock.selectByCriteria(Matchers.any(PersonGroupCriteria.class))).thenReturn(new ArrayList<PersonGroup>());
        
        List<PersonGroup> teams = projectService.getTeamsAssociateToProject(1);
        
        Assert.assertEquals(0, teams.size());
        
        Mockito.verify(projectPersonGroupDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class));
        
        Mockito.verify(personGroupDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(PersonGroupCriteria.class));
        
    }
    
    @Test
    public void testGetAllProjects() {
    
        Mockito.when(projectDAOmock.selectByCriteria(Matchers.any(ProjectCriteria.class))).thenReturn(getProject());
        
        List<ProjectDto> projects = projectService.getAllProjects();
        
        Assert.assertEquals(1, projects.size());
        ProjectDto project = projects.get(0);
        Assert.assertEquals("komea", project.getName());
        Assert.assertEquals("KOMEA", project.getProjectKey());
        Assert.assertEquals(0, project.nbAssociatedPerson());
        Assert.assertEquals(0, project.nbTeams());
        Assert.assertEquals(0, project.getTags().size());
        Assert.assertEquals(0, project.nbLinks());
        
        Mockito.verify(projectDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(ProjectCriteria.class));
        
        Mockito.verify(customerDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
        
        Mockito.verify(projectPersonDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonCriteria.class));
        
        Mockito.verify(personDAOmock, Mockito.times(0)).selectByPrimaryKey(1);
        
        Mockito.verify(projectPersonGroupDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class));
        
        Mockito.verify(personGroupDAOmock, Mockito.times(0)).selectByCriteria(Matchers.any(PersonGroupCriteria.class));
        
        Mockito.verify(projectTagsDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectTagCriteria.class));
        
        Mockito.verify(tagDAOmock, Mockito.times(0)).selectByPrimaryKey(1);
        
        Mockito.verify(linkDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(LinkCriteria.class));
        
    }
    
    @Test
    public void testGetAllProjectsComplete() {
    
        Mockito.when(projectDAOmock.selectByCriteria(Matchers.any(ProjectCriteria.class))).thenReturn(getProject());
        
        Mockito.when(customerDAOmock.selectByPrimaryKey(1)).thenReturn(getCustomer());
        
        // persons
        HasProjectPersonKey hasProjectPersonKey = new HasProjectPersonKey(1, 1);
        List<HasProjectPersonKey> projectPersonList = Lists.newArrayList(hasProjectPersonKey);
        Mockito.when(projectPersonDAOmock.selectByCriteria(Matchers.any(HasProjectPersonCriteria.class))).thenReturn(projectPersonList);
        
        Mockito.when(personDAOmock.selectByPrimaryKey(hasProjectPersonKey.getIdPerson())).thenReturn(getPerson());
        
        // teams
        List<HasProjectPersonGroupKey> projectGroups = Lists.newArrayList(new HasProjectPersonGroupKey(1, 1));
        Mockito.when(projectPersonGroupDAOmock.selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class))).thenReturn(
                projectGroups);
        
        Mockito.when(personGroupDAOmock.selectByCriteria(Matchers.any(PersonGroupCriteria.class))).thenReturn(getTeams());
        
        // tags
        List<HasProjectTagKey> projectTags = Lists.newArrayList(new HasProjectTagKey(1, 1));
        Mockito.when(projectTagsDAOmock.selectByCriteria(Matchers.any(HasProjectTagCriteria.class))).thenReturn(projectTags);
        
        Mockito.when(tagDAOmock.selectByPrimaryKey(1)).thenReturn(getTag());
        
        // links
        Mockito.when(linkDAOmock.selectByCriteria(Matchers.any(LinkCriteria.class))).thenReturn(getLinks());
        
        List<ProjectDto> projects = projectService.getAllProjects();
        
        Assert.assertEquals(1, projects.size());
        ProjectDto project = projects.get(0);
        Assert.assertEquals("komea", project.getName());
        Assert.assertEquals("KOMEA", project.getProjectKey());
        
        Assert.assertEquals(1, project.nbAssociatedPerson());
        Assert.assertEquals("lskywalker", project.loginList().toArray()[0]);
        Assert.assertEquals("luke Skywalker", project.getPersonName("lskywalker"));
        
        Assert.assertEquals(1, project.nbTeams());
        Assert.assertEquals("KOMEA_TEAM", project.teamKeyList().toArray()[0]);
        Assert.assertEquals("komea team", project.getTeamName("KOMEA_TEAM"));
        
        Assert.assertEquals(1, project.getTags().size());
        Assert.assertEquals("kpi", project.getTags().get(0));
        
        Assert.assertEquals(1, project.nbLinks());
        Assert.assertEquals("komea github site", project.linksNameList().toArray()[0]);
        Assert.assertEquals("https://github.com/sleroy/komea", project.getLinksUrl("komea github site"));
        
        Mockito.verify(projectDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(ProjectCriteria.class));
        
        Mockito.verify(customerDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
        
        Mockito.verify(projectPersonDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonCriteria.class));
        
        Mockito.verify(personDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
        
        Mockito.verify(projectPersonGroupDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class));
        
        Mockito.verify(personGroupDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(PersonGroupCriteria.class));
        
        Mockito.verify(projectTagsDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectTagCriteria.class));
        
        Mockito.verify(tagDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
        
        Mockito.verify(linkDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(LinkCriteria.class));
        
    }
    
    /**
     * Method getPerson.
     * @return Person
     */
    private Person getPerson() {
    
        Person person = new Person();
        person.setEmail("luke.skywailer@lighside.com");
        person.setFirstName("luke");
        person.setLastName("Skywalker");
        person.setId(1);
        person.setLogin("lskywalker");
        
        return person;
    }
    
    /**
     * Method getLinks.
     * @return List<Link>
     */
    private List<Link> getLinks() {
    
        Link link = new Link();
        link.setId(1);
        link.setIdProject(1);
        link.setName("komea github site");
        link.setUrl("https://github.com/sleroy/komea");
        
        return Lists.newArrayList(link);
    }
    
    /**
     * Method getTag.
     * @return Tag
     */
    private Tag getTag() {
    
        Tag link = new Tag();
        link.setId(1);
        link.setId(1);
        link.setName("kpi");
        return link;
    }
    
    /**
     * Method getTeams.
     * @return List<PersonGroup>
     */
    private List<PersonGroup> getTeams() {
    
        PersonGroup team = new PersonGroup();
        team.setId(1);
        team.setName("komea team");
        team.setDescription("komea team");
        team.setPersonGroupKey("KOMEA_TEAM");
        team.setType(PersonGroupType.TEAM);
        
        return Lists.newArrayList(team);
    }
    
    /**
     * Method getProject.
     * @return List<Project>
     */
    private List<Project> getProject() {
    
        Project team = new Project();
        team.setId(1);
        team.setName("komea");
        team.setDescription("komea kpi");
        team.setProjectKey("KOMEA");
        team.setIdCustomer(1);
        
        return Lists.newArrayList(team);
    }
    
    /**
     * Method getCustomer.
     * @return Customer
     */
    private Customer getCustomer() {
    
        return new Customer(1, "Soft@home");
    }
}
