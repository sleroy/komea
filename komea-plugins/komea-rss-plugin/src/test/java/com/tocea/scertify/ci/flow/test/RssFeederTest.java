package com.tocea.scertify.ci.flow.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.backend.plugins.rss.bean.RssExampleFeedBean;
import org.komea.backend.plugins.rss.repositories.api.IRssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextHierarchy({
		@ContextConfiguration("classpath:/spring/application-context.xml"),
		@ContextConfiguration("classpath:/spring/dispatcher-servlet.xml") })
public class RssFeederTest {

	@Autowired
	private IRssRepository repository;
	@Autowired
	private RssExampleFeedBean feedBean;

	/**
	 * Checks the default feeds are loaded.
	 * 
	 * @throws Exception
	 */
	@Transactional
	@Test
	public void verifFeeds() throws Exception {

		Assert.assertEquals(10, repository.findAll().size());
	}
}
