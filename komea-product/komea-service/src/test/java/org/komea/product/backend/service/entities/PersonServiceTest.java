
package org.komea.product.backend.service.entities;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.HasProjectPersonDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.Project;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {
    
    @InjectMocks
    private final IPersonService service = new PersonService();
    
    @Mock
    private HasProjectPersonDao  projectPersonDaoMock;
    
    @Mock
    private ProjectDao           projectDAOmock;
    
    @Mock
    private PersonDao            personDAOmock;
    
    @Mock
    private PersonRoleDao        roleDAOmock;
    
    @Mock
    private IPersonGroupService  groupService;
    
    //
    
    @Test
    public void testGetPersonList() {
    
        Mockito.when(personDAOmock.selectByCriteria(Matchers.any(PersonCriteria.class))).thenReturn(getPersons());
        
        List<PersonDto> personList = service.getPersonList();
        
        Assert.assertEquals(1, personList.size());
        PersonDto person = personList.get(0);
        Assert.assertEquals(1, person.getId().intValue());
        Assert.assertEquals("lskywalker", person.getLogin());
        
        Mockito.verify(personDAOmock, Mockito.times(1)).selectByCriteria(Matchers.any(PersonCriteria.class));
        
    }
    
    @Test
    public void testGetProjectsAssociateToAPerson() {
    
        List<HasProjectPersonKey> projectPersonList = Lists.newArrayList(new HasProjectPersonKey(1, 1));
        Mockito.when(projectPersonDaoMock.selectByCriteria(Matchers.any(HasProjectPersonCriteria.class))).thenReturn(projectPersonList);
        
        Mockito.when(projectDAOmock.selectByPrimaryKey(1)).thenReturn(getProjects().get(0));
        
        List<Project> projects = service.getProjectsAssociateToAPerson(1);
        
        Assert.assertEquals(1, projects.size());
        Project project = projects.get(0);
        Assert.assertEquals("komea", project.getName());
        Assert.assertEquals("KOMEA", project.getProjectKey());
        Assert.assertEquals("komea kpi", project.getDescription());
        Assert.assertEquals(1, project.getIdCustomer().intValue());
        
        Mockito.verify(projectPersonDaoMock, Mockito.times(1)).selectByCriteria(Matchers.any(HasProjectPersonCriteria.class));
        
        Mockito.verify(projectDAOmock, Mockito.times(1)).selectByPrimaryKey(1);
        
    }
    
    private List<Project> getProjects() {
    
        Project team = new Project();
        team.setId(1);
        team.setName("komea");
        team.setDescription("komea kpi");
        team.setProjectKey("KOMEA");
        team.setIdCustomer(1);
        
        return Lists.newArrayList(team);
    }
    
    private List<Person> getPersons() {
    
        Person person = new Person();
        person.setEmail("luke.skywailer@lighside.com");
        person.setFirstName("luke");
        person.setLastName("Skywalker");
        person.setId(1);
        person.setLogin("lskywalker");
        
        return Lists.newArrayList(person);
    }
}
