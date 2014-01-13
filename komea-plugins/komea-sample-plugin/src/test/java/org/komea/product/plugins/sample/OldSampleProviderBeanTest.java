package org.komea.product.plugins.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.plugins.sample.OldSampleProviderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tocea.scertify.ci.flow.app.api.service.model.IProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextHierarchy({
		@ContextConfiguration("classpath:/spring/application-context.xml"),
		@ContextConfiguration("classpath:/spring/dispatcher-servlet.xml") })
public class OldSampleProviderBeanTest {

	@Autowired
	private OldSampleProviderBean provider;

	@Autowired
	private IProjectService service;

	@Test
	public void test() {

		provider.buildJenkinsEvents();
		provider.buildScmEvents();
		provider.buildSonarEvents();
	}

}
