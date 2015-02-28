/**
 *
 */
package org.komea.demo.gitspy.indexer;

import java.util.concurrent.Callable;

import org.komea.demo.gitspy.indexer.git.GitRepositoryIndexerTask;
import org.komea.demo.gitspy.repository.domain.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This class defines the factory to build an repository indexer.
 *
 * @author sleroy
 *
 */
@Component
public class RepositoryIndexerTaskFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryIndexerTaskFactory.class);

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Performs the spring injection.
	 * @param _task the task

	 */
	public Callable inject(final Callable _task) {
		this.applicationContext.getAutowireCapableBeanFactory().autowireBean(_task);
		return _task;
	}

	/**
	 * Initializes the new git repository task.
	 * @param _repository the repository
	 * @return the new task.
	 */

	public GitRepositoryIndexerTask newGitRepositoryTask(
			final Repository _repository) {
		return new GitRepositoryIndexerTask(_repository);
	}

	/**
	 * Builds a new indexer from a repository.
	 *
	 * @param _repository the repository
	 * @return the task to handle the repository
	 */
	public Callable newIndexer(final Repository _repository) {
		Callable task = null;
		switch (_repository.getType()) {
		case "git": //$NON-NLS-1$
			task = this.newGitRepositoryTask(_repository);
			break;
		default:
			LOGGER.error("Repository of type {}", _repository.getType());
			break;

		}
		return this.inject(task);
	}

}
