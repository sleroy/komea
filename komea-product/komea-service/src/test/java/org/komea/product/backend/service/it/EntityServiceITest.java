
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
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;



/**
 */
@TestExecutionListeners(
    {
            DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class })
public class EntityServiceITest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IPersonGroupService groupService;
    
    @Autowired
    private IPersonService      personService;
    
    @Autowired
    private IProjectService     projectService;
    
    
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetAllDepartments() {
    
    
        final List<DepartmentDto> departments = groupService.getAllDepartments();
        Assert.assertEquals(0, departments.size());
    }
    
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetPersonList() {
    
    
        final List<PersonDto> personList = personService.convertAllPersonsIntoPersonDTO();
        Assert.assertEquals(2, personList.size());
    }
    
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetProjectList() {
    
    
        final List<ProjectDto> projectList = projectService.getAllProjects();
        Assert.assertEquals(1, projectList.size());
    }
    
}
