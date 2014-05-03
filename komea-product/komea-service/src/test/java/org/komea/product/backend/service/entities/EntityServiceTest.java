
package org.komea.product.backend.service.entities;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.api.IEntity;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



/**
 */
@RunWith(MockitoJUnitRunner.class)
public class EntityServiceTest
{
    
    
    @InjectMocks
    private final IEntityService entityService = new EntityService();
    
    @Mock
    private IPersonGroupService  personGroupDao;
    
    @Mock
    private IPersonService       personService;
    @Mock
    private IProjectService      projectDao;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.EntityService#getEntitiesByKey(org.komea.product.database.enums.EntityType, java.util.List)}
     * .
     */
    @Test
    public void testFindEntitiesByTypeAndKeysTeam_getAll() throws Exception {
    
    
        entityService.getBaseEntityDTOS(EntityType.TEAM, Collections.EMPTY_LIST);
        verify(personGroupDao, times(1)).getAllTeamsPG();
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.EntityService#getEntitiesByKey(org.komea.product.database.enums.EntityType, java.util.List)}
     * .
     */
    @Test
    public void testgetBaseEntityDTOSDepartment_atLeastOne() throws Exception {
    
    
        final ArrayList<String> newArrayList = Lists.newArrayList("GNI");
        entityService.getBaseEntityDTOS(EntityType.DEPARTMENT, newArrayList);
        verify(personGroupDao, times(1)).selectByKeys(newArrayList);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.EntityService#getEntitiesByKey(org.komea.product.database.enums.EntityType, java.util.List)}
     * .
     */
    @Test
    public void testgetBaseEntityDTOSDepartment_getAll() throws Exception {
    
    
        entityService.getBaseEntityDTOS(EntityType.DEPARTMENT, Collections.EMPTY_LIST);
        verify(personGroupDao, times(1)).getAllDepartmentsPG();
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.EntityService#getEntitiesByKey(org.komea.product.database.enums.EntityType, java.util.List)}
     * .
     */
    @Test
    public void testgetBaseEntityDTOSPerson_atLeastOne() throws Exception {
    
    
        final ArrayList<String> newArrayList = Lists.newArrayList("GNI");
        entityService.getBaseEntityDTOS(EntityType.PERSON, newArrayList);
        verify(personService, times(1)).selectByKeys(newArrayList);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.EntityService#getEntitiesByKey(org.komea.product.database.enums.EntityType, java.util.List)}
     * .
     */
    @Test
    public void testgetBaseEntityDTOSPerson_getAll() throws Exception {
    
    
        entityService.getBaseEntityDTOS(EntityType.PERSON, Collections.EMPTY_LIST);
        verify(personService, times(1)).selectAll();
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.EntityService#getEntitiesByKey(org.komea.product.database.enums.EntityType, java.util.List)}
     * .
     */
    @Test
    public void testgetBaseEntityDTOSProject_atLeastOne() throws Exception {
    
    
        final ArrayList<String> newArrayList = Lists.newArrayList("GNI");
        entityService.getBaseEntityDTOS(EntityType.PROJECT, newArrayList);
        verify(projectDao, times(1)).selectByKeys(newArrayList);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.EntityService#getEntitiesByKey(org.komea.product.database.enums.EntityType, java.util.List)}
     * .
     */
    @Test
    public void testgetBaseEntityDTOSProject_getAll() throws Exception {
    
    
        entityService.getBaseEntityDTOS(EntityType.PROJECT, Collections.EMPTY_LIST);
        verify(projectDao, times(1)).selectAll();
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.EntityService#getEntitiesByKey(org.komea.product.database.enums.EntityType, java.util.List)}
     * .
     */
    @Test
    public void testgetBaseEntityDTOSTeam() throws Exception {
    
    
        entityService.getBaseEntityDTOS(EntityType.TEAM, Collections.EMPTY_LIST);
        verify(personGroupDao, times(1)).getAllTeamsPG();
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.EntityService#getEntitiesByKey(org.komea.product.database.enums.EntityType, java.util.List)}
     * .
     */
    @Test
    public void testgetBaseEntityDTOSTeam_atLeastOne() throws Exception {
    
    
        final ArrayList<String> newArrayList = Lists.newArrayList("GNI");
        entityService.getBaseEntityDTOS(EntityType.TEAM, newArrayList);
        verify(personGroupDao, times(1)).selectByKeys(newArrayList);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.entities.EntityService#getEntitiesByKey(org.komea.product.database.enums.EntityType, java.util.List)}
     * .
     */
    @Test
    public void testgetBaseEntityDTOSTeam_getAll() throws Exception {
    
    
        entityService.getBaseEntityDTOS(EntityType.TEAM, Collections.EMPTY_LIST);
        verify(personGroupDao, times(1)).getAllTeamsPG();
    }
    
    
    @Test
    public void testGetEntitiesByEntityType() {
    
    
        final Person person = new Person();
        person.setId(12);
        person.setFirstName("John");
        person.setLastName("Dalton");
        final Person person2 = new Person();
        person2.setId(13);
        person2.setFirstName("william");
        person2.setLastName("Dalton");
        
        Mockito.when(personService.selectByPrimaryKey(Matchers.anyInt())).thenReturn(null);
        Mockito.when(personService.selectByPrimaryKey(12)).thenReturn(person);
        Mockito.when(personService.selectByPrimaryKey(13)).thenReturn(person2);
        
        final List<IEntity> entities =
                entityService.getEntitiesByPrimaryKey(EntityType.PERSON,
                        Lists.newArrayList(12, 13, 14));
        
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
        
        Mockito.verify(personService, Mockito.times(3)).selectByPrimaryKey(Matchers.anyInt());
        
    }
    
    
    @Test
    public void testGetGroup() {
    
    
        final PersonGroup group = new PersonGroup();
        group.setId(12);
        group.setName("Killer Group");
        group.setDescription("The killer group");
        group.setType(PersonGroupType.TEAM);
        
        Mockito.when(personGroupDao.selectByPrimaryKey(12)).thenReturn(group);
        
        final IEntity entity =
                entityService.findEntityByEntityKey(EntityKey.of(EntityType.TEAM, 12));
        
        Assert.assertTrue(entity instanceof PersonGroup);
        final PersonGroup result = (PersonGroup) entity;
        Assert.assertEquals(12, result.getId().intValue());
        Assert.assertEquals("Killer Group", result.getName());
        Assert.assertEquals("The killer group", result.getDescription());
        
        Mockito.verify(personGroupDao, Mockito.times(1)).selectByPrimaryKey(12);
        
    }
    
    
    //
    @Test
    public void testGetPerson() {
    
    
        final Person person = new Person();
        person.setId(12);
        person.setFirstName("John");
        person.setLastName("Dalton");
        
        Mockito.when(personService.selectByPrimaryKey(12)).thenReturn(person);
        
        final IEntity entity =
                entityService.findEntityByEntityKey(EntityKey.of(EntityType.PERSON, 12));
        
        Assert.assertTrue(entity instanceof Person);
        final Person result = (Person) entity;
        Assert.assertEquals(12, result.getId().intValue());
        Assert.assertEquals("John", result.getFirstName());
        Assert.assertEquals("Dalton", result.getLastName());
        
        Mockito.verify(personService, Mockito.times(1)).selectByPrimaryKey(12);
        
    }
    
    
    @Test
    public void testGetProject() {
    
    
        final Project project = new Project();
        project.setId(12);
        project.setName("Komea");
        project.setDescription("kpi measure tool");
        project.setProjectKey("KPI");
        
        Mockito.when(projectDao.selectByPrimaryKey(12)).thenReturn(project);
        
        final IEntity entity =
                entityService.findEntityByEntityKey(EntityKey.of(EntityType.PROJECT, 12));
        
        Assert.assertTrue(entity instanceof Project);
        final Project result = (Project) entity;
        Assert.assertEquals(12, result.getId().intValue());
        Assert.assertEquals("Komea", result.getName());
        Assert.assertEquals("kpi measure tool", result.getDescription());
        Assert.assertEquals("KPI", result.getProjectKey());
        
        Mockito.verify(projectDao, Mockito.times(1)).selectByPrimaryKey(12);
    }
    
    
    @Test
    public void testGetProjectNoExistingID() {
    
    
        Mockito.when(projectDao.selectByPrimaryKey(Matchers.anyInt())).thenReturn(null);
        
        final IEntity entity =
                entityService.findEntityByEntityKey(EntityKey.of(EntityType.PROJECT, 12));
        
        Assert.assertEquals(null, entity);
        Mockito.verify(projectDao, Mockito.times(1)).selectByPrimaryKey(Matchers.anyInt());
    }
}
