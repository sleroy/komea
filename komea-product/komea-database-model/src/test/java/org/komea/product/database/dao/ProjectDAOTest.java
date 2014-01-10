package org.komea.product.database.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.ProjectCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:/spring/applicationContext-test.xml")
public class ProjectDAOTest {

	@Autowired
	private ProjectMapper projectDAO;

	@Transactional
	@Test
	public void test() {
		final Project project = new Project();
		project.setName("projet1");
		final ProjectCriteria example = new ProjectCriteria();
		example.createCriteria().andNameEqualTo("projet1");
		Assert.assertTrue(projectDAO.selectByExample(example).isEmpty());

		projectDAO.insert(project);

		Assert.assertFalse(projectDAO.selectByExample(example).isEmpty());
	}

}
