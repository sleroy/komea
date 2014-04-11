
package org.komea.product.backend.service.entities;



import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.HasProjectPersonDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
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
public class PersonServiceTest
{
    
    
    @Mock
    private IPersonGroupService  groupService;
    
    @Mock
    private PersonDao            personDAOmock;
    
    @Mock
    private IPersonRoleService   personRoleService;
    
    @Mock
    private HasProjectPersonDao  projectPersonDaoMock;
    @Mock
    private IProjectService      projectService;
    @InjectMocks
    private final IPersonService service = new PersonService();
    
    
    
    //
    /**
     * Method getDepartment.
     * 
     * @return PersonGroup
     */
    public PersonGroup getDepartment() {
    
    
        final PersonGroup group = new PersonGroup();
        
        group.setDescription("research department");
        group.setId(1);
        group.setName("research department");
        group.setPersonGroupKey("RESEARCH_DEPARTMENT");
        group.setType(PersonGroupType.DEPARTMENT);
        return group;
        
    }
    
    
    @Test @Ignore
    public void testConvertAllPersonsIntoPersonDTO() {
    
    
        Mockito.when(personDAOmock.selectByCriteria(Matchers.any(PersonCriteria.class)))
                .thenReturn(getPersons());
        Mockito.when(groupService.selectByPrimaryKey(2)).thenReturn(getTeam());
        Mockito.when(personRoleService.selectByPrimaryKey(1)).thenReturn(getRole());
        Mockito.when(projectService.getProjectsOfPerson(1)).thenReturn(Collections.EMPTY_LIST);
        
        final List<PersonDto> personList = service.convertAllPersonsIntoPersonDTO();
        
        Assert.assertEquals(1, personList.size());
        final PersonDto person = personList.get(0);
        Assert.assertEquals(1, person.getId().intValue());
        Assert.assertEquals("lskywalker", person.getLogin());
        Assert.assertEquals(0, person.nbProject());
        Assert.assertFalse(person.hasDepartment());
        Assert.assertTrue(person.hasTeam());
        Assert.assertEquals("KOMEA_TEAM", person.teamKey());
        Assert.assertEquals("dev", person.getRole());
        
        Mockito.verify(personDAOmock, Mockito.times(1)).selectByCriteria(
                Matchers.any(PersonCriteria.class));
        
        Mockito.verify(projectService, Mockito.times(0)).selectByPrimaryKey(1);
        
    }
    
    
    @Test @Ignore
    public void testConvertAllPersonsIntoPersonDTOComplete() {
    
    
        Mockito.when(personDAOmock.selectByCriteria(Matchers.any(PersonCriteria.class)))
                .thenReturn(getPersons());
        
        final List<HasProjectPersonKey> projectPersonList =
                Lists.newArrayList(new HasProjectPersonKey(1, 1));
        Mockito.when(
                projectPersonDaoMock.selectByCriteria(Matchers.any(HasProjectPersonCriteria.class)))
                .thenReturn(projectPersonList);
        
        Mockito.when(projectService.selectByPrimaryKey(1)).thenReturn(getProjects().get(0));
        
        Mockito.when(groupService.selectByPrimaryKey(2)).thenReturn(getDepartment());
        
        Mockito.when(personRoleService.selectByPrimaryKey(1)).thenReturn(getRole());
        Mockito.when(projectService.getProjectsOfPerson(1)).thenReturn(getProjects());
        
        final List<PersonDto> personList = service.convertAllPersonsIntoPersonDTO();
        
        Assert.assertEquals(1, personList.size());
        final PersonDto person = personList.get(0);
        Assert.assertEquals(1, person.getId().intValue());
        Assert.assertEquals("lskywalker", person.getLogin());
        Assert.assertEquals(1, person.nbProject());
        Assert.assertEquals("RESEARCH_DEPARTMENT", person.departmentKey());
        Assert.assertEquals("research department", person.departmentName());
        Assert.assertEquals("dev", person.getRole());
        
        Mockito.verify(personDAOmock, Mockito.times(1)).selectByCriteria(
                Matchers.any(PersonCriteria.class));
        
        Mockito.verify(personRoleService, Mockito.times(1)).selectByPrimaryKey(1);
        
    }
    
    
    @Test @Ignore
    public void testStripLoginFromEmail() throws Exception {
    
    
        final PersonService personService = new PersonService();
        Assert.assertEquals("truc", personService.stripLoginFromEmail("truc@gmail.com"));
    }
    
    
    @Test @Ignore
    public void testStripLoginFromEmailWrong() throws Exception {
    
    
        final PersonService personService = new PersonService();
        Assert.assertEquals("", personService.stripLoginFromEmail("@gmail.com"));
    }
    
    
    @Test @Ignore
    public void testStripLoginFromEmailWrong2() throws Exception {
    
    
        final PersonService personService = new PersonService();
        Assert.assertEquals("trucgmail.com", personService.stripLoginFromEmail("trucgmail.com"));
    }
    
    
    /**
     * Method getPersons.
     * 
     * @return List<Person>
     */
    private List<Person> getPersons() {
    
    
        final Person person = new Person();
        person.setEmail("luke.skywailer@lighside.com");
        person.setFirstName("luke");
        person.setLastName("Skywalker");
        person.setId(1);
        person.setLogin("lskywalker");
        person.setIdPersonGroup(2);
        person.setIdPersonRole(1);
        
        return Lists.newArrayList(person);
    }
    
    
    /**
     * Method getProjects.
     * 
     * @return List<Project>
     */
    private List<Project> getProjects() {
    
    
        final Project team = new Project();
        team.setId(1);
        team.setName("komea");
        team.setDescription("komea kpi");
        team.setProjectKey("KOMEA");
        team.setIdCustomer(1);
        
        return Lists.newArrayList(team);
    }
    
    
    /**
     * Method getRole.
     * 
     * @return PersonRole
     */
    private PersonRole getRole() {
    
    
        final PersonRole role = new PersonRole(1, "dev", "dev");
        return role;
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
        return team;
    }
    
}
