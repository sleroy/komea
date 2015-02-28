/**
 *
 */
package org.komea.demo.gitspy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration of the server.
 *
 * @author sleroy
 *
 */
@Component
public class GitSpyConfigurationBean {
	@Value("${gitspy.extractorThreads}")
	private Integer	extractorThreads;

	@Value("${gitspy.minRefresh}")
	private Integer	minRefresh;

	@Value("${gitspy.eventoryURL}")
	private String	eventoryURL;

	@Value("${gitspy.git.commitMessage}")
	private boolean	gitCommitMessage;

	@Value("${gitspy.git.fileUpdateMessage}")
	private boolean	gitFileUpdateMessage;

	@Value("${gitspy.git.tagMessage}")
	private boolean	gitTagMessage;

	@Value("${gitspy.repositoryCacheDir}")
	private String repositoryCacheDir;
	@Value("${gitspy.timeout}")
	private Integer timeout;

	/**
	 * Returns the eventory server url.
	 *
	 * @return the eventory server url.
	 */
	public String getEventoryURL() {

		return this.eventoryURL;
	}

	/**
	 * @return the extractorThreads
	 */
	public Integer getExtractorThreads() {
		return this.extractorThreads;
	}

	/**
	 * @return the minRefresh
	 */
	public Integer getMinRefresh() {
		return this.minRefresh;
	}

	/**
	 * @return the repositoryCacheDir
	 */
	public String getRepositoryCacheDir() {
		return this.repositoryCacheDir;
	}

	/**
	 * @return the timeout
	 */
	public Integer getTimeout() {
		return this.timeout;
	}

	/**
	 * @return the gitCommitMessage
	 */
	public boolean isGitCommitMessage() {
		return this.gitCommitMessage;
	}

	/**
	 * @return the fileUpdateMessage
	 */
	public boolean isGitFileUpdateMessage() {
		return this.gitFileUpdateMessage;
	}

	/**
	 * @return the gitTagMessage
	 */
	public boolean isGitTagMessage() {
		return this.gitTagMessage;
	}

	/**
	 * @param _eventoryURL
	 *            the eventoryURL to set
	 */
	public void setEventoryURL(final String _eventoryURL) {
		this.eventoryURL = _eventoryURL;
	}

	/**
	 * @param _extractorThreads
	 *            the extractorThreads to set
	 */
	public void setExtractorThreads(final Integer _extractorThreads) {
		this.extractorThreads = _extractorThreads;
	}

	/**
	 * @param _gitCommitMessage
	 *            the gitCommitMessage to set
	 */
	public void setGitCommitMessage(final boolean _gitCommitMessage) {
		this.gitCommitMessage = _gitCommitMessage;
	}

	/**
	 * @param _fileUpdateMessage
	 *            the fileUpdateMessage to set
	 */
	public void setGitFileUpdateMessage(final boolean _fileUpdateMessage) {
		this.gitFileUpdateMessage = _fileUpdateMessage;
	}

	/**
	 * @param _gitTagMessage
	 *            the gitTagMessage to set
	 */
	public void setGitTagMessage(final boolean _gitTagMessage) {
		this.gitTagMessage = _gitTagMessage;
	}

	/**
	 * @param _minRefresh
	 *            the minRefresh to set
	 */
	public void setMinRefresh(final Integer _minRefresh) {
		this.minRefresh = _minRefresh;
	}

	/**
	 * @param _repositoryCacheDir the repositoryCacheDir to set
	 */
	public void setRepositoryCacheDir(final String _repositoryCacheDir) {
		this.repositoryCacheDir = _repositoryCacheDir;
	}

	/**
	 * @param _timeout the timeout to set
	 */
	public void setTimeout(final Integer _timeout) {
		this.timeout = _timeout;
	}
}
