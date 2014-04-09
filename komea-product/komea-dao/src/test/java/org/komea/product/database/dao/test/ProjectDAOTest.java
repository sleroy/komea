
package org.komea.product.database.dao.test;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/application-context-test.xml")
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class ProjectDAOTest
{
    
    
    @Autowired
    private ProjectDao  projectDAO;
    
    @Autowired
    private CustomerDao customerDAO;
    
    
    
    @Test
    @Transactional
    @DatabaseSetup("database.xml")
    @ExpectedDatabase(value = "addCustomer.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testInsertProject() {
    
    
        final ProjectCriteria request = new ProjectCriteria();
        request.createCriteria().andNameEqualTo("projet1");
        // Assert.assertTrue(projectDAO.selectByExample(request).isEmpty());
        
        final Customer jguidoux = customerDAO.selectByPrimaryKey(1);
        
        System.out.println("client id = " + jguidoux.getId());
        final Project project = new Project();
        project.setProjectKey("TEST1");
        project.setName("projet1");
        project.setIdCustomer(jguidoux.getId());
        project.setDescription("");
        
        projectDAO.insert(project);
        
        Assert.assertFalse(projectDAO.selectByCriteria(request).isEmpty());
        Assert.assertEquals(1, projectDAO.selectByCriteria(request).size());
    }
    
}
