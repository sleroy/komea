package org.komea.product.database.dao.it;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.dao.ProjectDao;
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
@ContextConfiguration("classpath:/spring/*-context-test.xml")
@TransactionConfiguration(defaultRollback = true)
public class ProjectDAOIT {

    @Autowired
    private ProjectDao projectDAO;

    @Autowired
    private CustomerDao customerDAO;

    @Test
    @Transactional
    public void test() {

        final ProjectCriteria request = new ProjectCriteria();
        request.createCriteria().andNameEqualTo("projet1");
        // Assert.assertTrue(projectDAO.selectByExample(request).isEmpty());

        final Customer jguidoux = new Customer();
        jguidoux.setName("jguidoux");
        customerDAO.insert(jguidoux);

        final CustomerCriteria request2 = new CustomerCriteria();
        // request2.createCriteria();
        final List<Customer> customers = customerDAO.selectByCriteria(request2);

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
