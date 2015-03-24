/**
 *
 */
package org.komea.demo.gitspy;

import java.io.File;
import java.net.MalformedURLException;

import javax.annotation.PostConstruct;

import org.komea.demo.gitspy.repository.dao.IRepositoryDAO;
import org.komea.demo.gitspy.repository.domain.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sleroy
 *
 */
@Component
public class DemoDataBean {

	@Autowired
	private IRepositoryDAO repositoryDAO;

	@PostConstruct
	public void init() throws MalformedURLException {
		this.redmineRepo();
		this.josqlRepo();

		this.scertifyRepo();

	}
	/**
	 * @throws MalformedURLException
	 */
	private void josqlRepo() throws MalformedURLException {
		final Repository entity = new Repository();
		entity.setLogin("");
		entity.setPassword("");
		entity.setUrl(new File("/home/sleroy/git/JoSQL/.git").toURI().toURL().toString());
		entity.setType("git");
		entity.setName("JoSQL");
		this.repositoryDAO.save(entity);
	}


	/**
	 * @throws MalformedURLException
	 */
	private void redmineRepo() throws MalformedURLException {
		final Repository entity = new Repository();
		entity.setLogin("");
		entity.setPassword("");
		entity.setUrl(new File("/home/sleroy/git/redmine/.git").toURI().toURL().toString());
		entity.setType("git");
		entity.setName("redmine");
		this.repositoryDAO.save(entity);
	}
	/**
	 * @throws MalformedURLException
	 */
	private void scertifyRepo() throws MalformedURLException {
		final Repository entity = new Repository();
		entity.setLogin("");
		entity.setPassword("");
		entity.setUrl(new File("/home/sleroy/git/scertify-frameworks/.git").toURI().toURL().toString());
		entity.setType("git");
		entity.setName("Scertify frameworks");
		this.repositoryDAO.save(entity);
	}
}

