
package org.komea.product.backend.service.entities;



import static org.mockito.Matchers.any;

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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;



/**
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonGroupServiceTest
{
    
    
    @InjectMocks
    private final IPersonGroupService groupService = new PersonGroupService();
    
    @Mock
    private PersonGroupDao            personGroupDaoMock;
    
    @Mock
    private IPersonService            personService;
    
    @Mock
    private HasProjectPersonGroupDao  projectPersonGroupDaoMock;
    
    @Mock
    private IProjectService           projectService;
    
    
    
    @Test
    public void testGetAllDepartments() {
    
    
        final PersonGroup department = getDepartment();
        final int departmentId = department.getId();
        Mockito.when(groupService.selectByCriteria(any(PersonGroupCriteria.class))).thenReturn(
                Arrays.asList(getDepartment()));
        Mockito.when(personService.getPersonsOfPersonGroup(departmentId)).thenReturn(
                getPersons(departmentId));
        
        final List<DepartmentDto> allDepartments = groupService.getAllDepartments();
        Assert.assertEquals(1, allDepartments.size());
        final DepartmentDto departmentDto = allDepartments.get(0);
        Assert.assertEquals(2, departmentDto.getPersons().size());
        Assert.assertTrue(departmentDto.getPersons().containsKey("5"));
        Assert.assertTrue(departmentDto.getPersons().containsKey("6"));
        Assert.assertEquals(department.getPersonGroupKey(), departmentDto.getKey());
        
    }
    
    
    @Test
    public void testGetAllTeams() {
    
    
        final PersonGroup team = getTeam();
        final int teamId = team.getId();
        Mockito.when(groupService.selectByCriteria(any(PersonGroupCriteria.class))).thenReturn(
                Arrays.asList(getTeam()));
        Mockito.when(projectService.getProjectsOfPersonGroup(teamId)).thenReturn(
                Arrays.asList(getProject()));
        Mockito.when(personService.getPersonsOfPersonGroup(teamId)).thenReturn(getPersons(teamId));
        
        final List<TeamDto> allTeams = groupService.getAllTeams();
        Assert.assertEquals(1, allTeams.size());
        final TeamDto teamDto = allTeams.get(0);
        Assert.assertEquals(2, teamDto.getPersons().size());
        Assert.assertEquals(1, teamDto.getProjects().size());
        Assert.assertTrue(teamDto.getPersons().containsKey("5"));
        Assert.assertTrue(teamDto.getPersons().containsKey("6"));
        Assert.assertTrue(teamDto.getProjects().containsKey("PROJECT_9"));
        Assert.assertEquals(team.getPersonGroupKey(), teamDto.getKey());
    }
    
    
    @Test
    public void testGetDepartment() {
    
    
        Mockito.when(personGroupDaoMock.selectByPrimaryKey(1)).thenReturn(getDepartment());
        
        final PersonGroup department = groupService.getDepartment(1);
        Assert.assertEquals(1, department.getId().intValue());
        Assert.assertEquals("research department", department.getName());
        Assert.assertEquals("research department", department.getDescription());
        Assert.assertEquals("RESEARCH_DEPARTMENT", department.getPersonGroupKey());
        Assert.assertEquals(PersonGroupType.DEPARTMENT, department.getType());
        
        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(1);
        
    }
    
    
    @Test
    public void testGetDepartmentByTeam() {
    
    
        Mockito.when(personGroupDaoMock.selectByPrimaryKey(1)).thenReturn(getDepartment());
        Mockito.when(personGroupDaoMock.selectByPrimaryKey(2)).thenReturn(getTeam());
        
        final PersonGroup department = groupService.getDepartment(2);
        Assert.assertEquals(1, department.getId().intValue());
        Assert.assertEquals("research department", department.getName());
        Assert.assertEquals("research department", department.getDescription());
        Assert.assertEquals("RESEARCH_DEPARTMENT", department.getPersonGroupKey());
        Assert.assertEquals(PersonGroupType.DEPARTMENT, department.getType());
        
        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(1);
        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(2);
        
    }
    
    
    @Test
    public void testGetDepartmentNull() {
    
    
        final PersonGroup department = groupService.getDepartment(1);
        Assert.assertNull(department);
        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(Matchers.anyInt());
        
    }
    
    
    @Test
    public void testGetprojectTeams() {
    
    
        final List<HasProjectPersonGroupKey> projectGroups =
                Lists.newArrayList(new HasProjectPersonGroupKey(1, 1));
        Mockito.when(
                projectPersonGroupDaoMock.selectByCriteria(Matchers
                        .any(HasProjectPersonGroupCriteria.class))).thenReturn(projectGroups);
        
        Mockito.when(personGroupDaoMock.selectByCriteria(Matchers.any(PersonGroupCriteria.class)))
                .thenReturn(getTeams());
        
        final List<PersonGroup> teams = groupService.getTeamsOfProject(1);
        
        Assert.assertEquals(1, teams.size());
        final PersonGroup team = teams.get(0);
        Assert.assertEquals(1, team.getId().intValue());
        Assert.assertEquals("KOMEA_TEAM", team.getPersonGroupKey());
        Assert.assertEquals(PersonGroupType.TEAM, team.getType());
        
        Mockito.verify(projectPersonGroupDaoMock, Mockito.times(1)).selectByCriteria(
                Matchers.any(HasProjectPersonGroupCriteria.class));
        
        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByCriteria(
                Matchers.any(PersonGroupCriteria.class));
        
    }
    
    
    @Test
    public void testGetTeam() {
    
    
        Mockito.when(personGroupDaoMock.selectByPrimaryKey(2)).thenReturn(getTeam());
        
        final PersonGroup team = groupService.getTeam(2);
        Assert.assertEquals(2, team.getId().intValue());
        Assert.assertEquals("komea team", team.getName());
        Assert.assertEquals("komea team", team.getDescription());
        Assert.assertEquals("KOMEA_TEAM", team.getPersonGroupKey());
        Assert.assertEquals(PersonGroupType.TEAM, team.getType());
        
        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(2);
        
    }
    
    
    @Test
    public void testGetTeamButIsDepartment() {
    
    
        Mockito.when(personGroupDaoMock.selectByPrimaryKey(2)).thenReturn(getDepartment());
        
        final PersonGroup team = groupService.getTeam(1);
        Assert.assertNull(team);
        
        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(Matchers.anyInt());
        
    }
    
    
    @Test
    public void testGetTeamNull() {
    
    
        final PersonGroup team = groupService.getTeam(2);
        Assert.assertNull(team);
        
        Mockito.verify(personGroupDaoMock, Mockito.times(1)).selectByPrimaryKey(Matchers.anyInt());
        
    }
    
    
    /**
     * Method getDepartment.
     * 
     * @return PersonGroup
     */
    private PersonGroup getDepartment() {
    
    
        final PersonGroup group = new PersonGroup();
        
        group.setDescription("research department");
        group.setId(1);
        group.setName("research department");
        group.setPersonGroupKey("RESEARCH_DEPARTMENT");
        group.setType(PersonGroupType.DEPARTMENT);
        return group;
        
    }
    
    
    private Person getPerson(final Integer personGroupId, final int id) {
    
    
        final Person person = new Person();
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
    
    
    private List<Person> getPersons(final Integer personGroupId) {
    
    
        return Arrays.asList(getPerson(personGroupId, 5), getPerson(personGroupId, 6));
        
    }
    
    
    private Project getProject() {
    
    
        final Project project = new Project();
        project.setDescription("Project 9");
        project.setId(9);
        project.setName("Project 9");
        project.setProjectKey("PROJECT_9");
        return project;
        
    }
    
    
    /**
     * Method getTeam.
     * 
     * @return PersonGroup
     */
    private PersonGroup getTeam() {
    
    
        final PersonGroup team = new PersonGroup();
        team.setId(2);
        team.setName("komea team");
        team.setDescription("komea team");
        team.setPersonGroupKey("KOMEA_TEAM");
        team.setType(PersonGroupType.TEAM);
        team.setIdPersonGroupParent(1);
        return team;
    }
    
    
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
