package org.komea.product.backend.service.entities;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.HasProjectPersonGroupCriteria;
import org.komea.product.database.model.HasProjectPersonGroupKey;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.database.model.Project;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonGroupServiceTest {

    @InjectMocks
    private final IPersonGroupService groupService = new PersonGroupService();

    @Mock
    private PersonGroupDao personGroupDaoMock;

    @Mock
    private HasProjectPersonGroupDao projectPersonGroupDaoMock;

    @Mock
    private IPersonService personService;

    @Mock
    private IProjectService projectService;

    @Test
    public void testGetDepartment() {

        Mockito.when(personGroupDaoMock.selectByPrimaryKey(1)).thenReturn(getDepartment());

        PersonGroup department = groupService.getDepartment(1);
        Assert.assertEquals(1, department.getId().intValue());
        Assert.assertEquals("research department", department.getName());
        Assert.assertEquals("research department", department.getDescription());
        Assert.assertEquals("RESEARCH_DEPARTMENT", department.getPersonGroupKey());
        Assert.assertEquals(PersonGroupType.DEPARTMENT, department.getType());

        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(1);

    }

    @Test
    public void testGetDepartmentNull() {

        PersonGroup department = groupService.getDepartment(1);
        Assert.assertNull(department);
        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(Matchers.anyInt());

    }

    @Test
    public void testGetDepartmentByTeam() {

        Mockito.when(personGroupDaoMock.selectByPrimaryKey(1)).thenReturn(getDepartment());
        Mockito.when(personGroupDaoMock.selectByPrimaryKey(2)).thenReturn(getTeam());

        PersonGroup department = groupService.getDepartment(2);
        Assert.assertEquals(1, department.getId().intValue());
        Assert.assertEquals("research department", department.getName());
        Assert.assertEquals("research department", department.getDescription());
        Assert.assertEquals("RESEARCH_DEPARTMENT", department.getPersonGroupKey());
        Assert.assertEquals(PersonGroupType.DEPARTMENT, department.getType());

        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(1);
        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(2);

    }

    @Test
    public void testGetAllTeams() {
        PersonGroup team = getTeam();
        int teamId = team.getId();
        Mockito.when(groupService.selectByCriteria(any(PersonGroupCriteria.class))).thenReturn(
                Arrays.asList(getTeam()));
        Mockito.when(projectService.getProjectsOfPersonGroup(teamId)).thenReturn(
                Arrays.asList(getProject()));
        Mockito.when(personService.getPersonsOfPersonGroup(teamId)).thenReturn(
                getPersons(teamId));

        List<TeamDto> allTeams = groupService.getAllTeams();
        Assert.assertEquals(1, allTeams.size());
        TeamDto teamDto = allTeams.get(0);
        Assert.assertEquals(2, teamDto.getPersons().size());
        Assert.assertEquals(1, teamDto.getProjects().size());
        Assert.assertTrue(teamDto.getPersons().containsKey("5"));
        Assert.assertTrue(teamDto.getPersons().containsKey("6"));
        Assert.assertTrue(teamDto.getProjects().containsKey("PROJECT_9"));
        Assert.assertEquals(team.getPersonGroupKey(), teamDto.getKey());
    }

    @Test
    public void testGetAllDepartments() {
        PersonGroup department = getDepartment();
        int departmentId = department.getId();
        Mockito.when(groupService.selectByCriteria(any(PersonGroupCriteria.class))).thenReturn(
                Arrays.asList(getDepartment()));
        Mockito.when(personService.getPersonsOfPersonGroup(departmentId)).thenReturn(
                getPersons(departmentId));

        List<DepartmentDto> allDepartments = groupService.getAllDepartments();
        Assert.assertEquals(1, allDepartments.size());
        DepartmentDto departmentDto = allDepartments.get(0);
        Assert.assertEquals(2, departmentDto.getPersons().size());
        Assert.assertTrue(departmentDto.getPersons().containsKey("5"));
        Assert.assertTrue(departmentDto.getPersons().containsKey("6"));
        Assert.assertEquals(department.getPersonGroupKey(), departmentDto.getKey());

    }

    @Test
    public void testGetTeam() {

        Mockito.when(personGroupDaoMock.selectByPrimaryKey(2)).thenReturn(getTeam());

        PersonGroup team = groupService.getTeam(2);
        Assert.assertEquals(2, team.getId().intValue());
        Assert.assertEquals("komea team", team.getName());
        Assert.assertEquals("komea team", team.getDescription());
        Assert.assertEquals("KOMEA_TEAM", team.getPersonGroupKey());
        Assert.assertEquals(PersonGroupType.TEAM, team.getType());

        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(2);

    }

    @Test
    public void testGetTeamNull() {

        PersonGroup team = groupService.getTeam(2);
        Assert.assertNull(team);

        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(Matchers.anyInt());

    }

    @Test
    public void testGetTeamButIsDepartment() {

        Mockito.when(personGroupDaoMock.selectByPrimaryKey(2)).thenReturn(getDepartment());

        PersonGroup team = groupService.getTeam(1);
        Assert.assertNull(team);

        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(Matchers.anyInt());

    }

    /**
     * Method getTeam.
     *
     * @return PersonGroup
     */
    private PersonGroup getTeam() {

        PersonGroup team = new PersonGroup();
        team.setId(2);
        team.setName("komea team");
        team.setDescription("komea team");
        team.setPersonGroupKey("KOMEA_TEAM");
        team.setType(PersonGroupType.TEAM);
        team.setIdPersonGroupParent(1);
        return team;
    }

    /**
     * Method getDepartment.
     *
     * @return PersonGroup
     */
    private PersonGroup getDepartment() {

        PersonGroup group = new PersonGroup();

        group.setDescription("research department");
        group.setId(1);
        group.setName("research department");
        group.setPersonGroupKey("RESEARCH_DEPARTMENT");
        group.setType(PersonGroupType.DEPARTMENT);
        return group;

    }

    private Project getProject() {

        Project project = new Project();
        project.setDescription("Project 9");
        project.setId(9);
        project.setName("Project 9");
        project.setProjectKey("PROJECT_9");
        return project;

    }

    private Person getPerson(Integer personGroupId, int id) {
        Person person = new Person();
        person.setEmail(id + "@tocea.com");
        person.setFirstName("aa");
        person.setId(id);
        person.setIdPersonGroup(personGroupId);
        person.setLastName("bb");
        person.setLogin("" + id);
        person.setPassword("**");
        person.setUserBdd(UserBdd.KOMEA);
        return person;
    }

    private List<Person> getPersons(Integer personGroupId) {
        return Arrays.asList(getPerson(personGroupId, 5), getPerson(personGroupId, 6));

    }

    private List<PersonGroup> getTeams() {

        PersonGroup team = new PersonGroup();
        team.setId(1);
        team.setName("komea team");
        team.setDescription("komea team");
        team.setPersonGroupKey("KOMEA_TEAM");
        team.setType(PersonGroupType.TEAM);

        return Lists.newArrayList(team);
    }

    @Test
    public void testGetprojectTeams() {

        List<HasProjectPersonGroupKey> projectGroups = Lists.newArrayList(new HasProjectPersonGroupKey(1, 1));
        Mockito.when(projectPersonGroupDaoMock.selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class))).thenReturn(
                projectGroups);

        Mockito.when(personGroupDaoMock.selectByCriteria(Matchers.any(PersonGroupCriteria.class))).thenReturn(getTeams());

        List<PersonGroup> teams = groupService.getTeamsOfProject(1);

        Assert.assertEquals(1, teams.size());
        PersonGroup team = teams.get(0);
        Assert.assertEquals(1, team.getId().intValue());
        Assert.assertEquals("KOMEA_TEAM", team.getPersonGroupKey());
        Assert.assertEquals(PersonGroupType.TEAM, team.getType());

        Mockito.verify(projectPersonGroupDaoMock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonGroupCriteria.class));

        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByCriteria(Matchers.any(PersonGroupCriteria.class));

    }

}
