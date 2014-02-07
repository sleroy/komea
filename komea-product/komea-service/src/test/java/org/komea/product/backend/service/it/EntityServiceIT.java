
package org.komea.product.backend.service.it;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.IEntityService;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class EntityServiceIT extends AbstractSpringIntegrationTestCase
{
    
    @Autowired
    private IEntityService entityService;
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetAllDepartments() {
    
        List<PersonGroup> departments = entityService.getAllDepartments();
        Assert.assertEquals(1, departments.size());
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetPersonList() {
    
        List<Person> personList = entityService.getPersonList();
        Assert.assertEquals(2, personList.size());
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetProjectList() {
    
        List<Project> projectList = entityService.getAllProjects();
        Assert.assertEquals(1, projectList.size());
    }
    
}
