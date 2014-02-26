package org.komea.product.plugins.rss;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.backend.plugins.rss.api.plugin.IAlertReactor;
import org.komea.product.plugins.rss.bean.RssCronJob;
import org.komea.product.plugins.rss.bean.RssProviderBean;
import org.komea.product.plugins.rss.repositories.api.IRssRepositories;
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
public class RssProviderTest {

	@Autowired
	private IRssRepositories repository;
	@Autowired
	private RssProviderBean feedBean;

	@Autowired
	private IAlertReactor esperEngine;

	/**
	 * Checks the default feeds are loaded.
	 * 
	 * @throws Exception
	 */
	@Transactional
	@Test
	public void verifFeeds() throws Exception {

		/**
		 * Force execution of the feeder
		 */

		final RssCronJob timerThread = new RssCronJob(repository, esperEngine);
		timerThread.run();
	}
}
