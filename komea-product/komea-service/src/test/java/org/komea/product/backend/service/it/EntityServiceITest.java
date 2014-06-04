
package org.komea.product.backend.service.it;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.model.Project;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

/**
 */

public class EntityServiceITest extends AbstractSpringDBunitIntegrationTest {
    
    @Autowired
    private IPersonGroupService groupService;
    
    @Autowired
    private IPersonService      personService;
    
    @Autowired
    private IProjectService     projectService;
    
    @Test
    public void testGetAllDepartments() {
    
        final List<DepartmentDto> departments = groupService.getAllDepartments();
        Assert.assertEquals(5, departments.size());
    }
    
    @Test
    public void testGetPersonList() {
    
        final List<PersonDto> personList = personService.convertAllPersonsIntoPersonDTO();
        Assert.assertEquals(22, personList.size());
    }
    
    @Test
    public void testGetProjectList() {
    
        final List<ProjectDto> projectList = projectService.getAllProjectsAsDtos();
        Assert.assertEquals(3, projectList.size());
    }
    
    @Test
    @ExpectedDatabase(value = "database_insertProject.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertProject() {
    
        final Project project = new Project();
        project.setProjectKey("P1");
        project.setName("name");
        
        projectService.saveOrUpdate(project);
    }
}
