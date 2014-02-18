
package org.komea.product.backend.service.entities;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.PersonGroup;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersonGroupServiceTest {
    
    @InjectMocks
    private final IPersonGroupService groupService = new PersonGroupService();
    
    @Mock
    private PersonGroupDao            personGroupDaoMock;
    
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
    
    private PersonGroup getDepartment() {
    
        PersonGroup group = new PersonGroup();
        
        group.setDescription("research department");
        group.setId(1);
        group.setName("research department");
        group.setPersonGroupKey("RESEARCH_DEPARTMENT");
        group.setType(PersonGroupType.DEPARTMENT);
        return group;
        
    }
    
}
