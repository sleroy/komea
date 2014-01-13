package org.komea.product.plugins.sample;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tocea.scertify.ci.flow.app.api.service.model.IEPLMetricService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextHierarchy({
		@ContextConfiguration("classpath:/spring/application-context.xml"),
		@ContextConfiguration("classpath:/spring/dispatcher-servlet.xml") })
public class LoadingStatementsTest {

	@Autowired
	private IEPLMetricService provider;

	@Test
	public void test() {
		int before = 0;
		before = provider.findAll().size();
		final int statements = provider.loadStatements(getClass()
				.getResourceAsStream("/config/statements/samples2.xml"));
		Assert.assertEquals(statements, 1);
		Assert.assertEquals(before + statements, provider.findAll().size());

	}
}
