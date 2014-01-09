package org.komea.product.database.dao;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.espertech.esper.client.annotation.Audit;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:/spring/applicationContext-test.xml")
public class ProjectDAOTest {

	
	@Autowired
	private ProjectMapper projectDAO;
	
	
	@Test
	public void test() {
		Project project = new Project();
		project.setName("projet1");
		ProjectExample example = new ProjectExample();
		example.createCriteria().andNameEqualTo("projet1");
		Assert.assertTrue(projectDAO.selectByExample(example).isEmpty());
		
		projectDAO.insert(project);
		
		Assert.assertFalse(projectDAO.selectByExample(example).isEmpty());
	}

}
