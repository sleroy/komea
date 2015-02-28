/**
 *
 */
package org.komea.demo.gitspy.indexer.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.komea.connectors.git.impl.GitCredentials;
import org.komea.demo.gitspy.repository.domain.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class fetches the repository on the given branches;
 *
 * @author sleroy
 *
 */
public class GitFetch {

	private final File			repositoryCacheFolder;
	private final Repository	repository;

	private static final Logger LOGGER = LoggerFactory.getLogger(GitFetch.class);
	private Integer	timeout;

	/**
	 * Builds a fetch action.
	 * @param _repositoryCacheFolder
	 * @param _repository
	 */
	public GitFetch(final File _repositoryCacheFolder,
			final Repository _repository) {
		this.repositoryCacheFolder = _repositoryCacheFolder;
		this.repository = _repository;
	}

	/**
	 * Fetch the repository
	 */
	public void fetch() {
		Git git = null;
		try {
			git = Git.open(this.repositoryCacheFolder);
			final FetchCommand fetch = git.fetch();
			//fetch.setRemote(this.repositoryCacheFolder.toURI().toURL().toString());
			fetch.setTimeout(this.timeout);
			fetch.setRemoveDeletedRefs(true);

			final GitCredentials gitCredentials = new GitCredentials(this.repository.getLogin(), this.repository.getPassword());
			final CredentialsProvider credentialsProvider = gitCredentials.init();
			fetch.setCredentialsProvider(credentialsProvider);
			fetch.call();
		} catch (final IOException | GitAPIException e) {
			LOGGER.error("Error during the repository fetch {}", this.repository, e);
		} finally {
			git.close();
		}

	}

	/**
	 * @param _timeout
	 */
	public void setTimeout(final Integer _timeout) {
		this.timeout = _timeout;


	}

}
