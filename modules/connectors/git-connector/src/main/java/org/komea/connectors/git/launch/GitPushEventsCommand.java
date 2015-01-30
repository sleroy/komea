/**
 *
 */
package org.komea.connectors.git.launch;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.joda.time.DateTime;
import org.kohsuke.args4j.Option;
import org.komea.connectors.git.IGitEvent;
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

	public GitPushEventsCommand() {
		super(IGitEvent.COMMIT);
	}

	public String getRepository() {
		return this.repository;
	}

	public String getRepositoryURL() {
		return this.repositoryURL;
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
		return this.commitMessage;
	}

	public boolean isFileUpdateMessage() {
		return this.fileUpdateMessage;
	}

	public boolean isTagMessage() {
		return this.tagMessage;
	}

	public void setCommitMessage(final boolean _commitMessage) {
		this.commitMessage = _commitMessage;
	}

	public void setFileUpdateMessage(final boolean _fileUpdateMessage) {
		this.fileUpdateMessage = _fileUpdateMessage;
	}

	public void setRepository(final String _repository) {
		this.repository = _repository;
	}

	public void setRepositoryURL(final String _repositoryURL) {
		this.repositoryURL = _repositoryURL;
	}

	public void setTagMessage(final boolean _tagMessage) {
		this.tagMessage = _tagMessage;
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
		File repositoryFile = new File(this.repository);

		if (!repositoryFile.exists()) {
			if (!repositoryFile.getName().toLowerCase().endsWith(GIT)) {
				repositoryFile = new File(repositoryFile.getAbsolutePath(), GIT);
			}
			if (!repositoryFile.exists()) {
				LOGGER.error("The path does not exist: {}", this.repository);
				throw new IllegalArgumentException("Repository does not exist");
			}
		}

		_eventoryClientAPI.getEventStorage().declareEventType(IGitEvent.COMMIT);
		_eventoryClientAPI.getEventStorage().declareEventType(IGitEvent.TAG);
		_eventoryClientAPI.getEventStorage().declareEventType(IGitEvent.UPDATE);

		final GitRepository gitRepository = new GitRepository(repositoryFile,
				this.repositoryURL);
		final Git git = gitRepository.getGit();

		final DateTime now = new DateTime();
		LOGGER.info("Processing between {} and {}", _lastExecution, now);
		final TimedCompositeCommitProcessor processor = new TimedCompositeCommitProcessor(
				_lastExecution, now);
		if (this.commitMessage) {
			LOGGER.info("Enabled commit events processor");
			processor.addCommitProcessor(new CommitEventProducer(
					_eventoryClientAPI.getEventStorage()));
		}
		if (this.tagMessage) {
			LOGGER.info("Enabled tag events processor");
			processor.addCommitProcessor(new GitEventTagProducer(
					_eventoryClientAPI.getEventStorage(), git));
		}
		if (this.fileUpdateMessage) {
			LOGGER.info("Enabled file update events processor");
			processor.addCommitProcessor(new FileModificationEventProducer(
					_eventoryClientAPI.getEventStorage()));
		}
		gitRepository.processAllCommits(processor);

	}
}
