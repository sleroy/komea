package org.komea.product.database.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.model.Customer;
import org.komea.product.database.model.CustomerCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:/spring/applicationContext-test.xml")
public class ProjectDAOTest {

	@Autowired
	private ProjectMapper projectDAO;

	@Autowired
	private CustomerMapper customerDAO;



	@Test
	public void test() {

		ProjectCriteria request = new ProjectCriteria();
		request.createCriteria().andNameEqualTo("projet1");
		Assert.assertTrue(projectDAO.selectByExample(request).isEmpty());

		
 
		
		Customer jguidoux = new Customer();
		jguidoux.setName("jguidoux");
		customerDAO.insert(jguidoux);
		
		
		CustomerCriteria request2 = new CustomerCriteria();
//		request2.createCriteria();
		List<Customer> customers = customerDAO.selectByExample(request2);

		
		Project project = new Project();
		project.setKey("TEST1");
		project.setName("projet1");
		project.setIdCustomer(customers.get(0).getId());


		projectDAO.insert(project);

		Assert.assertFalse(projectDAO.selectByExample(request).isEmpty());
		Assert.assertEquals(1,projectDAO.selectByExample(request).size());
	}

}
