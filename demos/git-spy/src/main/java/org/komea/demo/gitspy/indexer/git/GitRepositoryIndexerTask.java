/**
 *
 */
package org.komea.demo.gitspy.indexer.git;

import java.io.File;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.Validate;
import org.komea.connectors.git.IGitEvent;
import org.komea.connectors.git.impl.ScmRepositoryDefinition;
import org.komea.connectors.git.launch.GitPushEventsCommand;
import org.komea.connectors.git.utils.GitCloner;
import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;
import org.komea.demo.gitspy.cache.RepositoryCacheService;
import org.komea.demo.gitspy.configuration.GitSpyConfigurationBean;
import org.komea.demo.gitspy.eventory.api.IEventoryConnectorService;
import org.komea.demo.gitspy.repository.dao.IRepositoryDAO;
import org.komea.demo.gitspy.repository.domain.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class implements the component to perform the repository extraction.
 *
 * @author sleroy
 *
 */
public class GitRepositoryIndexerTask implements Callable<Object> {

	private static final String			GIT_EXTENSION	= ".git";

	private final Repository			repository;

	@Autowired
	private IEventoryConnectorService	eventoryConnectorService;

	@Autowired
	private GitSpyConfigurationBean		configurationBean;

	@Autowired
	private RepositoryCacheService		repositoryCacheService;

	@Autowired
	private IRepositoryDAO		repositoryDAO;

	private static final Logger			LOGGER			= LoggerFactory.getLogger(GitRepositoryIndexerTask.class);

	/**
	 * Builds a new task
	 *
	 * @param _repository
	 *            the repository.
	 */
	public GitRepositoryIndexerTask(final Repository _repository) {
		this.repository = _repository;
		Validate.notNull(_repository);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public Object call() throws Exception {
		LOGGER.info("Indexation of repository {} is launched...",
		            this.repository.getName());

		IEventoryClientAPI eventoryClientAPI = null;
		try {
			//			final File repositoryCacheFolder = new File(this.repositoryCacheService.obtainRepositoryCacheFolder(this.repository),
			//			                                            GIT_EXTENSION);
			final File repositoryCacheFolder = this.repositoryCacheService.obtainRepositoryCacheFolder(this.repository);
			try {
				LOGGER.info("Fetch repository {}", this.repository.getName());
				this.fetchRepository(repositoryCacheFolder);
			} catch (final Exception e) {
			} finally {
				//
			}

			try {
				LOGGER.info("Clone repository {}", this.repository.getName());
				this.cloneRepository(repositoryCacheFolder);

			} catch (final Exception e) {
				// TODO: handle exception
			}

			LOGGER.info("Analysis of  {}", this.repository.getName());
			eventoryClientAPI = this.eventoryConnectorService.newConnection();
			final GitPushEventsCommand pushEventsCommand = new GitPushEventsCommand();
			pushEventsCommand.setCommitMessage(this.configurationBean.isGitCommitMessage());
			pushEventsCommand.setFileUpdateMessage(this.configurationBean.isGitFileUpdateMessage());
			pushEventsCommand.setTagMessage(this.configurationBean.isGitTagMessage());
			pushEventsCommand.setRepositoryURL(this.repository.getUrl());
			pushEventsCommand.setRepository(new File(this.repositoryCacheService.obtainRepositoryCacheFolder(this.repository),
			                                         GIT_EXTENSION).getAbsolutePath());
			pushEventsCommand.setSince(this.repository.getLastExecutionTime());
			pushEventsCommand.sendEvents(	eventoryClientAPI,
			                             	this.repository.getLastExecutionTime());


			LOGGER.info("Event server contains {} events", eventoryClientAPI.countEvents(IGitEvent.COMMIT));

		}catch(final Exception e) {
			LOGGER.error("Refresh of repository {} has failed", this.repository.getName(), e);

		} finally {
			this.repository.setLastExecution(new Date());
			this.repositoryDAO.save(this.repository);
			eventoryClientAPI.close();
			LOGGER.info("Indexation of repository {} is finished.",
			            this.repository.getName());
		}
		return null;
	}

	/**
	 * Clones the repository
	 *
	 * @param repositoryCacheFolder
	 *            the repository cache folder.
	 */
	private void cloneRepository(final File repositoryCacheFolder) {
		repositoryCacheFolder.mkdirs();

		final ScmRepositoryDefinition gitRepo = new ScmRepositoryDefinition();
		gitRepo.setName(this.repository.getName());
		gitRepo.setUserName(this.repository.getLogin());
		gitRepo.setPassword(this.repository.getPassword());
		gitRepo.setUrl(this.repository.getUrl());
		gitRepo.setCloneDirectory(repositoryCacheFolder);
		LOGGER.info("Repository cache folder {}", repositoryCacheFolder);
		final GitCloner gitCloner = new GitCloner(repositoryCacheFolder,
		                                          gitRepo);
		gitCloner.cloneRepository();


	}

	/**
	 * Fetch the repository to update it
	 *
	 * @param _repositoryCacheFolder
	 *            the repository cache folder.
	 */
	private void fetchRepository(final File _repositoryCacheFolder) {
		final GitFetch gitFetch = new GitFetch(_repositoryCacheFolder,
		                                       this.repository);
		gitFetch.setTimeout(this.configurationBean.getTimeout());
		gitFetch.fetch();

	}

}
