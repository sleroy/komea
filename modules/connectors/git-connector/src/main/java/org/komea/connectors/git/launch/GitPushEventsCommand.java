/**
 *
 */
package org.komea.connectors.git.launch;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.joda.time.DateTime;
import org.kohsuke.args4j.Option;
import org.komea.connectors.git.events.CommitEventProducer;
import org.komea.connectors.git.events.FileModificationEventProducer;
import org.komea.connectors.git.events.GitEventTagProducer;
import org.komea.connectors.git.events.TimedCompositeCommitProcessor;
import org.komea.connectors.git.impl.GitRepository;
import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;
import org.komea.connectors.sdk.std.impl.AbstractPushEventsCommand;

/**
 * Git push event
 *
 * @author sleroy
 *
 */
public class GitPushEventsCommand extends AbstractPushEventsCommand {
	/**
	 *
	 */
	private static final String GIT = ".git";

	@Option(name = "-git", usage = "Path to the  cloned repository", required = true)
	protected String repository;

	@Option(name = "-gitURL", usage = "URL to the  git repository")
	protected String repositoryURL;

	@Option(name = "-commit", usage = "Send commit messages")
	protected boolean commitMessage;

	@Option(name = "-tag", usage = "Send tags")
	protected boolean tagMessage;

	@Option(name = "-fileUpdate", usage = "Send file updates")
	protected boolean fileUpdateMessage;

	public String getRepository() {
		return repository;
	}

	public String getRepositoryURL() {
		return repositoryURL;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.komea.connectors.sdk.main.IConnectorCommand#init()
	 */
	@Override
	public void init() {
		//

	}

	public boolean isCommitMessage() {
		return commitMessage;
	}

	public boolean isFileUpdateMessage() {
		return fileUpdateMessage;
	}

	public boolean isTagMessage() {
		return tagMessage;
	}

	public void setCommitMessage(final boolean _commitMessage) {
		commitMessage = _commitMessage;
	}

	public void setFileUpdateMessage(final boolean _fileUpdateMessage) {
		fileUpdateMessage = _fileUpdateMessage;
	}

	public void setRepository(final String _repository) {
		repository = _repository;
	}

	public void setRepositoryURL(final String _repositoryURL) {
		repositoryURL = _repositoryURL;
	}

	public void setTagMessage(final boolean _tagMessage) {
		tagMessage = _tagMessage;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.connectors.sdk.std.impl.AbstractPushEventsCommand#sendEvents
	 * (org.komea.connectors.sdk.rest.impl.EventoryClientAPI)
	 */
	@Override
	protected void sendEvents(final IEventoryClientAPI _eventoryClientAPI,
			final DateTime _lastExecution) {
		File repositoryFile = new File(repository);

		if (!repositoryFile.exists()) {
			if (!repositoryFile.getName().toLowerCase().endsWith(GIT)) {
				repositoryFile = new File(repositoryFile.getAbsolutePath(), GIT);
			}
			if (!repositoryFile.exists()) {
				LOGGER.error("The path does not exist: {}", repository);
				throw new IllegalArgumentException("Repository does not exist");
			}
		}

		final GitRepository gitRepository = new GitRepository(repositoryFile,
				repositoryURL);
		final Git git = gitRepository.getGit();

		final DateTime now = new DateTime();
		LOGGER.info("Processing between {} and {}", _lastExecution, now);
		final TimedCompositeCommitProcessor processor = new TimedCompositeCommitProcessor(
				_lastExecution, now);
		if (commitMessage) {
			LOGGER.info("Enabled commit events processor");
			processor.addCommitProcessor(new CommitEventProducer(
					_eventoryClientAPI.getEventStorage()));
		}
		if (tagMessage) {
			LOGGER.info("Enabled tag events processor");
			processor.addCommitProcessor(new GitEventTagProducer(
					_eventoryClientAPI.getEventStorage(), git));
		}
		if (fileUpdateMessage) {
			LOGGER.info("Enabled file update events processor");
			processor.addCommitProcessor(new FileModificationEventProducer(
					_eventoryClientAPI.getEventStorage()));
		}
		gitRepository.processAllCommits(processor);

	}
}
