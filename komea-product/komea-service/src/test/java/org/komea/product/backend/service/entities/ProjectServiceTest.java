package org.komea.product.backend.service.entities;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Ignore;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @Mock
    private CustomerDao customerDAOmock;

    @Mock
    private LinkDao linkDAOmock;

    @Mock
    private PersonDao personDAOmock;

    @Mock
    private PersonGroupDao personGroupDAOmock;

    @Mock
    private IPersonGroupService personGroupService;

    @Mock
    private IPersonService personService;

    @Mock
    private ProjectDao projectDAOmock;

    @Mock
    private HasProjectPersonDao projectPersonDAOmock;

    @Mock
    private HasProjectPersonGroupDao projectPersonGroupDAOmock;

    @Mock
    private IProjectPersonService projectPersonService;

    @InjectMocks
    private final IProjectService projectService = new ProjectService();

    @Mock
    private HasProjectTagDao projectTagsDAOmock;
    @Mock
    private TagDao tagDAOmock;

    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.ProjectService#deleteProject(org.komea.product.database.model.Project)}.
     */
    @Test
    public void testDeleteProject() throws Exception {

        final Project project = new Project();
        project.setId(1);

        projectService.delete(project);
        verify(projectDAOmock, times(1)).deleteByPrimaryKey(project.getId());

    }

    @Test
    public void testFindProjectsAssociatedToAPerson() {

        final List<HasProjectPersonKey> projectPersonList
                = Lists.newArrayList(new HasProjectPersonKey(1, 1));
        Mockito.when(
                projectPersonDAOmock.selectByCriteria(Matchers.any(HasProjectPersonCriteria.class)))
                .thenReturn(projectPersonList);
        Mockito.when(projectPersonService.getProjectIdsOfPerson(1)).thenReturn(projectPersonList);
        Mockito.when(projectDAOmock.selectByPrimaryKey(1)).thenReturn(getProjects().get(0));

        final List<Project> projects = projectService.getProjectsOfAMember(1);

        Assert.assertEquals(1, projects.size());
        final Project project = projects.get(0);
        Assert.assertEquals("komea", project.getName());
        Assert.assertEquals("KOMEA_FOLDER", project.getProjectKey());
        Assert.assertEquals("komea kpi", project.getDescription());
        Assert.assertEquals(1, project.getIdCustomer().intValue());

        Mockito.verify(projectDAOmock, Mockito.times(1)).selectByPrimaryKey(1);

    }

    @Test
    public void testGetAllProjectsAsDtos() {

        Mockito.when(projectDAOmock.selectByCriteria(Matchers.any(ProjectCriteria.class)))
                .thenReturn(getProjects());

        final List<ProjectDto> projects = projectService.getAllProjectsAsDtos();

        Assert.assertEquals(3, projects.size());
        final ProjectDto project = projects.get(0);
        Assert.assertEquals("komea", project.getName());
        Assert.assertEquals("KOMEA_FOLDER", project.getProjectKey());
        Assert.assertEquals(0, project.nbAssociatedPerson());
        Assert.assertEquals(0, project.nbTeams());
        Assert.assertEquals(0, project.getTags().size());
        Assert.assertEquals(0, project.nbLinks());

        Mockito.verify(projectDAOmock, Mockito.times(1)).selectByCriteria(
                Matchers.any(ProjectCriteria.class));

        Mockito.verify(customerDAOmock, Mockito.times(1)).selectByPrimaryKey(1);

        Mockito.verify(personGroupDAOmock, Mockito.times(0)).selectByCriteria(
                Matchers.any(PersonGroupCriteria.class));

        Mockito.verify(projectTagsDAOmock, Mockito.times(3)).selectByCriteria(
                Matchers.any(HasProjectTagCriteria.class));

        Mockito.verify(tagDAOmock, Mockito.times(0)).selectByPrimaryKey(1);

        Mockito.verify(linkDAOmock, Mockito.times(3)).selectByCriteria(
                Matchers.any(LinkCriteria.class));

    }

    @Test
    public void testGetAllProjectsAsDtosComplete() {

        Mockito.when(projectDAOmock.selectByCriteria(Matchers.any(ProjectCriteria.class)))
                .thenReturn(getProjects());

        Mockito.when(customerDAOmock.selectByPrimaryKey(1)).thenReturn(getCustomer());

        // persons
        final HasProjectPersonKey hasProjectPersonKey = new HasProjectPersonKey(1, 1);
        final List<HasProjectPersonKey> projectPersonList = Lists.newArrayList(hasProjectPersonKey);
        Mockito.when(
                projectPersonDAOmock.selectByCriteria(Matchers.any(HasProjectPersonCriteria.class)))
                .thenReturn(projectPersonList);

        Mockito.when(personDAOmock.selectByPrimaryKey(hasProjectPersonKey.getIdPerson()))
                .thenReturn(getPerson());

        // teams
        final List<HasProjectPersonGroupKey> projectGroups
                = Lists.newArrayList(new HasProjectPersonGroupKey(1, 1));
        Mockito.when(
                projectPersonGroupDAOmock.selectByCriteria(Matchers
                        .any(HasProjectPersonGroupCriteria.class))).thenReturn(projectGroups);

        Mockito.when(personGroupDAOmock.selectByCriteria(Matchers.any(PersonGroupCriteria.class)))
                .thenReturn(getTeams());
        Mockito.when(personService.getPersonsOfPersonGroup(1)).thenReturn(
                Arrays.asList(getPerson()));
        Mockito.when(personGroupService.getTeamsOfProject(1)).thenReturn(getTeams());

        // tags
        final List<HasProjectTagKey> projectTags = Lists.newArrayList(new HasProjectTagKey(1, 1));
        Mockito.when(projectTagsDAOmock.selectByCriteria(Matchers.any(HasProjectTagCriteria.class)))
                .thenReturn(projectTags);

        Mockito.when(tagDAOmock.selectByPrimaryKey(1)).thenReturn(getTag());

        // links
        Mockito.when(linkDAOmock.selectByCriteria(Matchers.any(LinkCriteria.class))).thenReturn(
                getLinks());

        final List<ProjectDto> projects = projectService.getAllProjectsAsDtos();

        Assert.assertEquals(3, projects.size());
        final ProjectDto project = projects.get(0);
        Assert.assertEquals("komea", project.getName());
        Assert.assertEquals("KOMEA_FOLDER", project.getProjectKey());

        Assert.assertEquals(0, project.nbAssociatedPerson());
        // Assert.assertEquals("lskywalker", project.loginList().toArray()[0]);
        // Assert.assertEquals("luke Skywalker", project.getPersonName("lskywalker"));

        Assert.assertEquals(1, project.nbTeams());
        Assert.assertEquals("KOMEA_TEAM", project.teamKeyList().toArray()[0]);
        Assert.assertEquals("komea team", project.getTeamName("KOMEA_TEAM"));

        Assert.assertEquals(1, project.getTags().size());
        Assert.assertEquals("kpi", project.getTags().get(0));

        Assert.assertEquals(1, project.nbLinks());
        Assert.assertEquals("komea github site", project.linksNameList().toArray()[0]);
        Assert.assertEquals("https://github.com/sleroy/komea",
                project.getLinksUrl("komea github site"));

        Mockito.verify(projectDAOmock, Mockito.times(1)).selectByCriteria(
                Matchers.any(ProjectCriteria.class));

        Mockito.verify(customerDAOmock, Mockito.times(1)).selectByPrimaryKey(1);

        Mockito.verify(projectTagsDAOmock, Mockito.times(3)).selectByCriteria(
                Matchers.any(HasProjectTagCriteria.class));

        Mockito.verify(tagDAOmock, Mockito.times(3)).selectByPrimaryKey(1);

        Mockito.verify(linkDAOmock, Mockito.times(3)).selectByCriteria(
                Matchers.any(LinkCriteria.class));

    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.ProjectService#getAllProjectsEntities()}.
     */
    @Test
    public void testGetAllProjectsAsDtosEntities() throws Exception {

        // assertFalse("Not yet implemented", true);
    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.ProjectService#getOrCreate(java.lang.String)}.
     */
    @Test
    public void testGetOrCreate_existingProject() throws Exception {

        Project project = new Project();
        when(projectDAOmock.selectByCriteria(Matchers.any(ProjectCriteria.class))).thenReturn(
                Collections.singletonList(project));
        project = projectService.getOrCreate("PROJECT_KEY");
        verify(projectDAOmock, times(1)).selectByCriteria(Matchers.any(ProjectCriteria.class));
        verify(projectDAOmock, never()).insert(project);

    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.ProjectService#getOrCreate(java.lang.String)}.
     */
    @Test
    public void testGetOrCreate_notexistingProject() throws Exception {

        final Project orCreate = projectService.getOrCreate("PROJECT_KEY");
        verify(projectDAOmock, times(1)).insert(orCreate);

    }

    @Test
    public void testGetProjectLinks() {

        Mockito.when(linkDAOmock.selectByCriteria(Matchers.any(LinkCriteria.class))).thenReturn(
                getLinks());

        final List<Link> links = projectService.getProjectLinks(1);

        Assert.assertEquals(1, links.size());
        final Link link = links.get(0);
        Assert.assertEquals(1, link.getId().intValue());
        Assert.assertEquals(1, link.getIdProject().intValue());
        Assert.assertEquals("komea github site", link.getName());
        Assert.assertEquals("https://github.com/sleroy/komea", link.getUrl());

        Mockito.verify(linkDAOmock, Mockito.times(1)).selectByCriteria(
                Matchers.any(LinkCriteria.class));

    }

    @Test
    public void testGetProjectLinksNoLinks() {

        Mockito.when(linkDAOmock.selectByCriteria(Matchers.any(LinkCriteria.class))).thenReturn(
                new ArrayList<Link>(0));

        final List<Link> links = projectService.getProjectLinks(1);

        Assert.assertEquals(0, links.size());

        Mockito.verify(linkDAOmock, Mockito.times(1)).selectByCriteria(
                Matchers.any(LinkCriteria.class));

    }

    @Test
    public void testGetprojectNoTeams() {

        final List<HasProjectPersonGroupKey> projectGroups = Lists.newArrayList();
        Mockito.when(
                projectPersonGroupDAOmock.selectByCriteria(Matchers
                        .any(HasProjectPersonGroupCriteria.class))).thenReturn(projectGroups);

        final List<PersonGroup> teams = personGroupService.getTeamsOfProject(1);

        Assert.assertEquals(0, teams.size());
    }

    @Test
    public void testGetProjectsAssociateToAPerson() {

        final List<HasProjectPersonKey> projectPersonList
                = Lists.newArrayList(new HasProjectPersonKey(1, 1));
        Mockito.when(
                projectPersonDAOmock.selectByCriteria(Matchers.any(HasProjectPersonCriteria.class)))
                .thenReturn(projectPersonList);

        Mockito.when(projectDAOmock.selectByPrimaryKey(1)).thenReturn(getProjects().get(0));
        Mockito.when(projectPersonService.getProjectIdsOfPerson(1)).thenReturn(projectPersonList);

        final List<Project> projects = projectService.getProjectsOfAMember(1);

        Assert.assertEquals(1, projects.size());
        final Project project = projects.get(0);
        Assert.assertEquals("komea", project.getName());
        Assert.assertEquals("KOMEA_FOLDER", project.getProjectKey());
        Assert.assertEquals("komea kpi", project.getDescription());
        Assert.assertEquals(1, project.getIdCustomer().intValue());

        Mockito.verify(projectDAOmock, Mockito.times(1)).selectByPrimaryKey(1);

    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.ProjectService#getProjectsOfAMember(java.lang.Integer)}.
     */
    @Test
    public void testGetProjectsOfAMember() throws Exception {

        // assertFalse("Not yet implemented", true);
    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.ProjectService#getProjectsOfPersonGroup(java.lang.Integer)}.
     */
    @Test
    public void testGetProjectsOfPersonGroup() throws Exception {

        // assertFalse("Not yet implemented", true);
    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.ProjectService#getProjectsOfPersonGroupRecursively(java.lang.Integer)}.
     */
    @Test
    public void testGetProjectsOfPersonGroupRecursively() throws Exception {

        // assertFalse("Not yet implemented", true);
    }

    @Test
    public void testGetProjectTags() {

        final List<HasProjectTagKey> projectTags = Lists.newArrayList(new HasProjectTagKey(1, 1));
        Mockito.when(projectTagsDAOmock.selectByCriteria(Matchers.any(HasProjectTagCriteria.class)))
                .thenReturn(projectTags);

        Mockito.when(tagDAOmock.selectByPrimaryKey(1)).thenReturn(getTag());

        final List<String> tags = projectService.getProjectTags(1);

        Assert.assertEquals(1, tags.size());
        final String tag = tags.get(0);
        Assert.assertEquals("kpi", tag);

        Mockito.verify(projectTagsDAOmock, Mockito.times(1)).selectByCriteria(
                Matchers.any(HasProjectTagCriteria.class));

        Mockito.verify(tagDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
    }

    @Test
    public void testGetProjectTagsNoTags() {

        final List<HasProjectTagKey> projectTags = Lists.newArrayList();
        Mockito.when(projectTagsDAOmock.selectByCriteria(Matchers.any(HasProjectTagCriteria.class)))
                .thenReturn(projectTags);

        final List<String> tags = projectService.getProjectTags(1);

        Assert.assertEquals(0, tags.size());

        Mockito.verify(projectTagsDAOmock, Mockito.times(1)).selectByCriteria(
                Matchers.any(HasProjectTagCriteria.class));

        Mockito.verify(tagDAOmock, Mockito.times(0)).selectByPrimaryKey(1);
    }

    @Test
    public void testGetProjectTagsNullTags() {

        final List<HasProjectTagKey> projectTags = Lists.newArrayList(new HasProjectTagKey(1, 1));
        Mockito.when(projectTagsDAOmock.selectByCriteria(Matchers.any(HasProjectTagCriteria.class)))
                .thenReturn(projectTags);

        Mockito.when(tagDAOmock.selectByPrimaryKey(1)).thenReturn(null);

        final List<String> tags = projectService.getProjectTags(1);

        Assert.assertEquals(0, tags.size());

        Mockito.verify(projectTagsDAOmock, Mockito.times(1)).selectByCriteria(
                Matchers.any(HasProjectTagCriteria.class));

        Mockito.verify(tagDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
    }

    @Test
    @Ignore
    public void testSelectByAlias() {
        final List<Project> projects = getProjects();
        Mockito.when(projectService.selectAll()).thenReturn(projects);
        for (final Project project : projects) {
            Mockito.when(projectService.selectByKey(project.getKey())).thenReturn(project);
        }

        Project project = projectService.selectByAlias("project-alias");
        Assert.assertNotNull(project);
        Assert.assertNotEquals("project-alias", project.getKey());
        Assert.assertTrue(project.hasAlias("project-alias"));
        Assert.assertEquals(projects.get(2), project);

        project = projectService.selectByAlias("project-key");
        Assert.assertNotNull(project);
        Assert.assertEquals("project-key", project.getKey());
        Assert.assertEquals(projects.get(1), project);

        project = projectService.selectByAlias("project-unknown");
        Assert.assertNull(project);
    }

    @Test
    public void testGetprojectTeamsNullTeams() {

        final List<HasProjectPersonGroupKey> projectGroups
                = Lists.newArrayList(new HasProjectPersonGroupKey(1, 1));
        Mockito.when(
                projectPersonGroupDAOmock.selectByCriteria(Matchers
                        .any(HasProjectPersonGroupCriteria.class))).thenReturn(projectGroups);

        Mockito.when(personGroupDAOmock.selectByCriteria(Matchers.any(PersonGroupCriteria.class)))
                .thenReturn(new ArrayList<PersonGroup>());

        final List<PersonGroup> teams = personGroupService.getTeamsOfProject(1);

        Assert.assertEquals(0, teams.size());

    }

    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.ProjectService#saveOrUpdateProject(org.komea.product.database.model.Project, java.util.List, java.util.List, java.util.List, java.util.List)}
     * .
     */
    @Test
    public void testSaveOrUpdateProject() throws Exception {

        // assertFalse("Not yet implemented", true);
    }

    /**
     * Method getCustomer.
     *
     * @return Customer
     */
    private Customer getCustomer() {

        return new Customer(1, "Soft@home");
    }

    /**
     * Method getLinks.
     *
     * @return List<Link>
     */
    private List<Link> getLinks() {

        final Link link = new Link();
        link.setId(1);
        link.setIdProject(1);
        link.setName("komea github site");
        link.setUrl("https://github.com/sleroy/komea");

        return Lists.newArrayList(link);
    }

    /**
     * Method getPerson.
     *
     * @return Person
     */
    private Person getPerson() {

        final Person person = new Person();
        person.setEmail("luke.skywailer@lighside.com");
        person.setFirstName("luke");
        person.setLastName("Skywalker");
        person.setId(1);
        person.setLogin("lskywalker");

        return person;
    }

    /**
     * Method getProject.
     *
     * @return List<Project>
     */
    private List<Project> getProjects() {

        final Project proj1 = new Project();
        proj1.setId(1);
        proj1.setName("komea");
        proj1.setDescription("komea kpi");
        proj1.setProjectKey("KOMEA_FOLDER");
        proj1.setIdCustomer(1);

        final Project proj2 = new Project();
        proj2.setId(2);
        proj2.setProjectKey("project-key");

        final Project proj3 = new Project();
        proj3.setId(3);
        proj3.setProjectKey("project3");
        proj3.setAlias(Arrays.asList("gradle", "project-alias", "maven"));

        return Lists.newArrayList(proj1, proj2, proj3);
    }

    /**
     * Method getTag.
     *
     * @return Tag
     */
    private Tag getTag() {

        final Tag link = new Tag();
        link.setId(1);
        link.setId(1);
        link.setName("kpi");
        return link;
    }

    /**
     * Method getTeams.
     *
     * @return List<PersonGroup>
     */
    private List<PersonGroup> getTeams() {

        final PersonGroup team = new PersonGroup();
        team.setId(1);
        team.setName("komea team");
        team.setDescription("komea team");
        team.setPersonGroupKey("KOMEA_TEAM");
        team.setType(PersonGroupType.TEAM);

        return Lists.newArrayList(team);
    }
}
