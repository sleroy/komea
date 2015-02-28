package org.komea.connectors.git.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.komea.connectors.git.impl.ScmRepositoryDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitCloner {

	private static final Logger				LOGGER	= LoggerFactory.getLogger(GitCloner.class);

	private Repository						fileRepository;

	private Git								git;

	private final ScmRepositoryDefinition	scmRepositoryDefinition;
	private final File						storageFolder;

	/**
	 * Builds the repository.
	 *
	 * @param _storageFolder
	 * @param _repositoryName
	 * @param _repositoryURL
	 */
	public GitCloner(final File _storageFolder,
			final ScmRepositoryDefinition _gitRepo) {

		super();
		Validate.notNull(_storageFolder);
		Validate.notNull(_gitRepo);
		Validate.notNull(_gitRepo.getName());
		this.storageFolder = _storageFolder;
		this.scmRepositoryDefinition = _gitRepo;

	}

	public void cloneRepository() {

		this.initializeStorageFolder();
		try {
			LOGGER.info("Execution of the clone command in {}", this.storageFolder);
			LOGGER.info("Repository URL in {}", this.scmRepositoryDefinition.getUrl());
			final CloneCommand cloneRepository = Git.cloneRepository();
			cloneRepository.setURI(	this.scmRepositoryDefinition.getUrl());
			cloneRepository.setDirectory(	this.scmRepositoryDefinition.getCloneDirectory());
			//cloneRepository.setNoCheckout(true);
			cloneRepository.setProgressMonitor(new TextProgressMonitor());
			cloneRepository.setCloneSubmodules(true);
			cloneRepository.setCloneAllBranches(true);
			//cloneRepository.set


			if (StringUtils.isNotEmpty(this.scmRepositoryDefinition.getUserName())
					&& StringUtils.isNotEmpty(this.scmRepositoryDefinition.getPassword())) {
				final UsernamePasswordCredentialsProvider credentials = new UsernamePasswordCredentialsProvider(this.scmRepositoryDefinition.getUserName(),
				                                                                                                this.scmRepositoryDefinition.getPassword());

				cloneRepository.setCredentialsProvider(credentials);
			}

			this.git = cloneRepository
					.call();
		} catch (final Exception e) {
			throw new IllegalArgumentException(e);
		}
		this.fileRepository = this.git.getRepository();

	}

	public Git getGit() {

		return this.git;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GitCloner [fileRepository=" + this.fileRepository + ", git="
				+ this.git + ", scmRepositoryDefinition="
				+ this.scmRepositoryDefinition + ", storageFolder="
				+ this.storageFolder + "]";
	}

	/**
	 * Initialize the storage folder.
	 */
	private void initializeStorageFolder() {

		if (this.scmRepositoryDefinition.getCloneDirectory() == null
				|| !this.scmRepositoryDefinition.getCloneDirectory().exists()) {
			final File scmClonedDirectory = new File(this.storageFolder,
			                                         this.scmRepositoryDefinition.getName());
			try {
				FileUtils.deleteDirectory(scmClonedDirectory);
			} catch (final IOException e) {
				LOGGER.error(	"Could not delete the folder : {}",
				             	scmClonedDirectory);
			}

			if (!scmClonedDirectory.exists() && !scmClonedDirectory.mkdirs()) {
				throw new IllegalArgumentException("Could not create "
						+ scmClonedDirectory);
			}

			this.scmRepositoryDefinition.setCloneDirectory(new File(scmClonedDirectory
			                                                        + "/.git"));
		} else {
			this.scmRepositoryDefinition.getCloneDirectory().mkdirs();

		}
	}
}
