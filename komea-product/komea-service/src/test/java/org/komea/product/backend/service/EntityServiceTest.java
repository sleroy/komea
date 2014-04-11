
package org.komea.product.backend.service;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.EntityService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.service.dto.EntityKey;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;



/**
 */
@RunWith(MockitoJUnitRunner.class)
public class EntityServiceTest
{
    
    
    @InjectMocks
    private final IEntityService entityService = new EntityService();
    
    @Mock
    private PersonDao            personDAO;
    
    @Mock
    private PersonGroupDao       personGroupDao;
    @Mock
    private ProjectDao           projectDao;
    
    
    
    @Test @Ignore
    public void testGetGroup() {
    
    
        final PersonGroup group = new PersonGroup();
        group.setId(12);
        group.setName("Killer Group");
        group.setDescription("The killer group");
        group.setType(PersonGroupType.TEAM);
        
        Mockito.when(personGroupDao.selectByPrimaryKey(12)).thenReturn(group);
        
        final IEntity entity = entityService.getEntity(EntityKey.of(EntityType.TEAM, 12));
        
        Assert.assertTrue(entity instanceof PersonGroup);
        final PersonGroup result = (PersonGroup) entity;
        Assert.assertEquals(12, result.getId().intValue());
        Assert.assertEquals("Killer Group", result.getName());
        Assert.assertEquals("The killer group", result.getDescription());
        
        Mockito.verify(personGroupDao, Mockito.times(1)).selectByPrimaryKey(12);
        
    }
    
    
    //
    @Test @Ignore
    public void testGetPerson() {
    
    
        final Person person = new Person();
        person.setId(12);
        person.setFirstName("John");
        person.setLastName("Dalton");
        
        Mockito.when(personDAO.selectByPrimaryKey(12)).thenReturn(person);
        
        final IEntity entity = entityService.getEntity(EntityKey.of(EntityType.PERSON, 12));
        
        Assert.assertTrue(entity instanceof Person);
        final Person result = (Person) entity;
        Assert.assertEquals(12, result.getId().intValue());
        Assert.assertEquals("John", result.getFirstName());
        Assert.assertEquals("Dalton", result.getLastName());
        
        Mockito.verify(personDAO, Mockito.times(1)).selectByPrimaryKey(12);
        
    }
    
    
    @Test @Ignore
    public void testGetProject() {
    
    
        final Project project = new Project();
        project.setId(12);
        project.setName("Komea");
        project.setDescription("kpi measure tool");
        project.setProjectKey("KPI");
        
        Mockito.when(projectDao.selectByPrimaryKey(12)).thenReturn(project);
        
        final IEntity entity = entityService.getEntity(EntityKey.of(EntityType.PROJECT, 12));
        
        Assert.assertTrue(entity instanceof Project);
        final Project result = (Project) entity;
        Assert.assertEquals(12, result.getId().intValue());
        Assert.assertEquals("Komea", result.getName());
        Assert.assertEquals("kpi measure tool", result.getDescription());
        Assert.assertEquals("KPI", result.getProjectKey());
        
        Mockito.verify(projectDao, Mockito.times(1)).selectByPrimaryKey(12);
    }
    
    
    @Test @Ignore
    public void testGetProjectNoExistingID() {
    
    
        Mockito.when(projectDao.selectByPrimaryKey(Matchers.anyInt())).thenReturn(null);
        
        final IEntity entity = entityService.getEntity(EntityKey.of(EntityType.PROJECT, 12));
        
        Assert.assertEquals(null, entity);
        Mockito.verify(projectDao, Mockito.times(1)).selectByPrimaryKey(Matchers.anyInt());
    }
    
    
    @Test @Ignore
    public void testLoadEntities() {
    
    
        final Person person = new Person();
        person.setId(12);
        person.setFirstName("John");
        person.setLastName("Dalton");
        final Person person2 = new Person();
        person2.setId(13);
        person2.setFirstName("william");
        person2.setLastName("Dalton");
        
        Mockito.when(personDAO.selectByPrimaryKey(Matchers.anyInt())).thenReturn(null);
        Mockito.when(personDAO.selectByPrimaryKey(12)).thenReturn(person);
        Mockito.when(personDAO.selectByPrimaryKey(13)).thenReturn(person2);
        
        final List<IEntity> entities =
                entityService.loadEntities(EntityType.PERSON, Lists.newArrayList(12, 13, 14));
        
        Assert.assertEquals(2, entities.size());
        
        Assert.assertTrue(entities.get(0) instanceof Person);
        Person result = (Person) entities.get(0);
        Assert.assertEquals(12, result.getId().intValue());
        Assert.assertEquals("John", result.getFirstName());
        Assert.assertEquals("Dalton", result.getLastName());
        
        Assert.assertTrue(entities.get(1) instanceof Person);
        result = (Person) entities.get(1);
        Assert.assertEquals(13, result.getId().intValue());
        Assert.assertEquals("william", result.getFirstName());
        Assert.assertEquals("Dalton", result.getLastName());
        
        Mockito.verify(personDAO, Mockito.times(3)).selectByPrimaryKey(Matchers.anyInt());
        
    }
}
