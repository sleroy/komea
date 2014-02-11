
package org.komea.product.backend.service.it;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.IEntityService;
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

@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class EntityServiceIT extends AbstractSpringIntegrationTestCase {
    
    @Autowired
    private IEntityService entityService;
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetAllDepartments() {
    
        List<DepartmentDto> departments = entityService.getAllDepartments();
        Assert.assertEquals(1, departments.size());
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetPersonList() {
    
        List<PersonDto> personList = entityService.getPersonList();
        Assert.assertEquals(2, personList.size());
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetProjectList() {
    
        List<ProjectDto> projectList = entityService.getAllProjects();
        Assert.assertEquals(1, projectList.size());
    }
    
}
