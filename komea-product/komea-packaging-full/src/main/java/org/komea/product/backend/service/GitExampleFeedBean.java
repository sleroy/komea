package org.komea.product.backend.service;

import javax.annotation.PostConstruct;

import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.repository.model.ScmType;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Inserts a default RSS Feed.
 * 
 * @author sleroy
 */
@Component
public class GitExampleFeedBean {

	@Autowired
	private IScmRepositoryService	repository;

	public GitExampleFeedBean() {

		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.product.plugins.rss.bean.IRssExampleFeedBean#initRssFeed()
	 */

	@PostConstruct
	public void initGitFeed() {

		if (!repository.getAllRepositories().isEmpty()) {

		return; }

		registerGitRepo("GITHub Komea", "https://github.com/sleroy/komea.git", "KOMEA_FOLDER");

	}

	private void registerGitRepo(final String _repoName, final String _url, final String _project) {

		final ScmRepositoryDefinition gitRepo = new ScmRepositoryDefinition();
		gitRepo.setRepoName(_repoName);
		gitRepo.setUrl(_url);
		gitRepo.setProjectForRepository(_project);
		gitRepo.setType(ScmType.GIT);

		gitRepo.setPassword("");
		gitRepo.setUserName("");
		gitRepo.setKey(ScmRepositoryDefinition.transformNameInKey(_repoName));
		repository.saveOrUpdate(gitRepo);

	}
}
