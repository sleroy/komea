/**
 * 
 */

package org.komea.product.database.dto;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;



/**
 * @author sleroy
 */
public class BaseEntityDtoTest
{
    
    
    /**
     * Test method for {@link org.komea.product.database.dto.BaseEntityDto#convertEntities(java.util.List)}.
     */
    @Test
    public void testConvertEntities() throws Exception {
    
    
        final List<IEntity> entityList = new ArrayList<IEntity>();
        final Project newFakeProject = newFakeProject();
        entityList.add(newFakeProject);
        final List<BaseEntityDto> convertEntities = BaseEntityDto.convertEntities(entityList);
        validDtoForProject(newFakeProject, convertEntities.get(0));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.database.dto.BaseEntityDto#newFromPersonGroup(org.komea.product.database.model.PersonGroup)}
     * .
     */
    @Test
    public void testNewFromDepartment() throws Exception {
    
    
        final PersonGroup team = newFakeDepartment();
        
        
        final BaseEntityDto newFromMember = BaseEntityDto.newFromPersonGroup(team);
        assertEquals(newFromMember.getDescription(), team.getDescription());
        assertEquals(newFromMember.getKey(), team.getKey());
        assertEquals(newFromMember.getId(), team.getId());
        assertEquals(newFromMember.getName(), team.getName());
    }
    
    
    /**
     * Test method for {@link org.komea.product.database.dto.BaseEntityDto#newFromMember(org.komea.product.database.model.Person)}.
     */
    @Test
    public void testNewFromMember() throws Exception {
    
    
        final Person person = newFakeMember();
        
        final BaseEntityDto newFromMember = BaseEntityDto.newFromMember(person);
        assertEquals(newFromMember.getDescription(), person.getEmail());
        assertEquals(newFromMember.getKey(), person.getLogin());
        assertEquals(newFromMember.getId(), person.getId());
        assertEquals(newFromMember.getName(), person.getFullName());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.database.dto.BaseEntityDto#newFromProject(org.komea.product.database.model.Project)}.
     */
    @Test
    public final void testNewFromProject() throws Exception {
    
    
        final Project project = newFakeProject();
        final BaseEntityDto projectDTO = BaseEntityDto.newFromProject(project);
        validDtoForProject(project, projectDTO);
    }
    
    
    /**
     * Test method for {@link org.komea.product.database.dto.BaseEntityDto#newFromPersonGroup(org.komea.product.database.model.PersonGroup)}
     * .
     */
    @Test
    public void testNewFromTeam() throws Exception {
    
    
        final PersonGroup team = newFakeTeam();
        
        
        final BaseEntityDto newFromMember = BaseEntityDto.newFromPersonGroup(team);
        assertEquals(newFromMember.getDescription(), team.getDescription());
        assertEquals(newFromMember.getKey(), team.getKey());
        assertEquals(newFromMember.getId(), team.getId());
        assertEquals(newFromMember.getName(), team.getName());
    }
    
    
    /**
     * @return
     */
    private PersonGroup newFakeDepartment() {
    
    
        final PersonGroup team = new PersonGroup();
        team.setType(PersonGroupType.DEPARTMENT);
        team.setName("name");
        team.setId(1);
        team.setDescription("desc");
        team.setPersonGroupKey("depKey");
        return team;
        
    }
    
    
    private Person newFakeMember() {
    
    
        final Person person = new Person();
        person.setId(1);
        person.setFirstName("first");
        person.setLastName("last");
        person.setEmail("email");
        return person;
    }
    
    
    /**
     * Returns a fake project.
     * 
     * @return a fake project
     */
    private Project newFakeProject() {
    
    
        return new Project();
    }
    
    
    private PersonGroup newFakeTeam() {
    
    
        final PersonGroup team = new PersonGroup();
        team.setType(PersonGroupType.TEAM);
        team.setName("name");
        team.setId(1);
        team.setDescription("desc");
        team.setPersonGroupKey("teamKey");
        return team;
    }
    
    
    private void validDtoForProject(final Project project, final BaseEntityDto projectDTO) {
    
    
        assertEquals(projectDTO.getDescription(), project.getDescription());
        assertEquals(projectDTO.getKey(), project.getKey());
        assertEquals(projectDTO.getId(), project.getId());
        assertEquals(projectDTO.getName(), project.getName());
    }
    
    
}
