
package org.komea.product.backend.service.entities;



import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.esper.IEventConversionAndValidationService;
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

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest
{
    
    
    /**
     * 
     */
    private static final String                  EMAIL_EMAIL_ORG = "email@email.org";
    
    @Mock
    private IEventConversionAndValidationService eventConversionAndValidationService;
    
    @Mock
    private IPersonGroupService                  groupService;
    
    @Mock
    private PersonDao                            personDAOmock;
    
    @Mock
    private IPersonRoleService                   personRoleService;
    
    @Mock
    private HasProjectPersonDao                  projectPersonDaoMock;
    @Mock
    private IProjectPersonService                projectPersonService;
    
    @Mock
    private IProjectService                      projectService;
    @InjectMocks
    private final IPersonService                 service         = new PersonService();
    
    
    
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
    
    
    @Test
    public void testConvertAllPersonsIntoPersonDTO() {
    
    
        Mockito.when(personDAOmock.selectByCriteria(Matchers.any(PersonCriteria.class)))
                .thenReturn(getPersons());
        Mockito.when(groupService.selectByPrimaryKey(2)).thenReturn(getTeam());
        Mockito.when(personRoleService.selectByPrimaryKey(1)).thenReturn(getRole());
        Mockito.when(projectService.getProjectsOfAMember(1)).thenReturn(Collections.EMPTY_LIST);
        
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
    
    
    @Test
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
        Mockito.when(projectService.getProjectsOfAMember(1)).thenReturn(getProjects());
        
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
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.PersonService#existUserByEmail(java.lang.String)}.
     */
    @Test
    public void testExistUserByEmail() throws Exception {
    
    
        // CAnnot test the criteria :(
        // TODO:: DBUNIT
        service.existUserByEmail(EMAIL_EMAIL_ORG);
        
        verify(personDAOmock, times(1)).selectByCriteria(Matchers.any(PersonCriteria.class));
        verify(personDAOmock, never()).insert(Matchers.any(Person.class));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.PersonService#findUserByEmail(java.lang.String)}.
     */
    @Test
    public void testFindUserByEmail_AllFailed() throws Exception {
    
    
        // CAnnot test the criteria :(
        // TODO:: DBUNIT
        final Person findOrCreatePersonByEmail = service.findOrCreatePersonByEmail(EMAIL_EMAIL_ORG);
        assertEquals(EMAIL_EMAIL_ORG, findOrCreatePersonByEmail.getEmail());
        verify(personDAOmock, times(2)).selectByCriteria(Matchers.any(PersonCriteria.class));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.PersonService#findUserByEmail(java.lang.String)}.
     */
    @Test
    public void testFindUserByEmail_emailSuccess() throws Exception {
    
    
        // CAnnot test the criteria :(
        // TODO:: DBUNIT
        programReturnFakePerson();
        final Person findOrCreatePersonByEmail = service.findOrCreatePersonByEmail(EMAIL_EMAIL_ORG);
        assertEquals(EMAIL_EMAIL_ORG, findOrCreatePersonByEmail.getEmail());
        
        verify(personDAOmock, times(1)).selectByCriteria(Matchers.any(PersonCriteria.class));
        
    }


    private void programReturnFakePerson() {
    
    
        final Person returnPersno = new Person();
        returnPersno.setEmail(EMAIL_EMAIL_ORG);
        when(personDAOmock.selectByCriteria(Matchers.any(PersonCriteria.class))).thenReturn(
                Collections.singletonList(returnPersno));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.PersonService#findUserByEmail(java.lang.String)}.
     */
    @Test
    public void testFindUserByEmail_loginFailed_emailSuccess() throws Exception {
    
    
        programReturnFakePerson();
        final Person findOrCreatePersonByEmail = service.findOrCreatePersonByEmail(EMAIL_EMAIL_ORG);
        assertEquals(EMAIL_EMAIL_ORG, findOrCreatePersonByEmail.getEmail());
        
        verify(personDAOmock, times(1)).selectByCriteria(Matchers.any(PersonCriteria.class));
        
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.PersonService#saveOrUpdatePersonAndItsProjects(org.komea.product.database.model.Person, java.util.List)}
     * .
     */
    @Test
    public void testSaveOrUpdatePersonAndItsProjects() throws Exception {
    
    
        final Person person = new Person();
        final List<Project> projects = getProjects();
        service.saveOrUpdatePersonAndItsProjects(person, projects);
        verify(projectPersonService).updateProjectsOfPerson(projects, person);
        verify(personDAOmock).insert(person);
        
    }
    
    
    @Test
    public void testStripLoginFromEmail() throws Exception {
    
    
        final PersonService personService = new PersonService();
        Assert.assertEquals("truc", personService.stripLoginFromEmail("truc@gmail.com"));
    }
    
    
    @Test
    public void testStripLoginFromEmailWrong() throws Exception {
    
    
        final PersonService personService = new PersonService();
        Assert.assertEquals("", personService.stripLoginFromEmail("@gmail.com"));
    }
    
    
    @Test
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
        team.setProjectKey("KOMEA_FOLDER");
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
