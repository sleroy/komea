
package org.komea.product.database.dao;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.CustomerCriteria;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/spring/applicationContext-test.xml")
@TransactionConfiguration(defaultRollback = true)
public class ProjectDAOTest
{
    
    @Autowired
    private ProjectMapper  projectDAO;
    
    @Autowired
    private CustomerMapper customerDAO;
    
    @Test
    @Transactional
    public void test() {
    
        ProjectCriteria request = new ProjectCriteria();
        request.createCriteria().andNameEqualTo("projet1");
        // Assert.assertTrue(projectDAO.selectByExample(request).isEmpty());
        
        Customer jguidoux = new Customer();
        jguidoux.setName("jguidoux");
        this.customerDAO.insert(jguidoux);
        
        CustomerCriteria request2 = new CustomerCriteria();
        // request2.createCriteria();
        List<Customer> customers = this.customerDAO.selectByExample(request2);
        
        System.out.println("client id = " + jguidoux.getId());
        Project project = new Project();
        project.setProjectKey("TEST1");
        project.setName("projet1");
        project.setIdCustomer(jguidoux.getId());
        project.setDescription("");
        
        this.projectDAO.insert(project);
        
        Assert.assertFalse(this.projectDAO.selectByExample(request).isEmpty());
        Assert.assertEquals(1, this.projectDAO.selectByExample(request).size());
    }
    
}
