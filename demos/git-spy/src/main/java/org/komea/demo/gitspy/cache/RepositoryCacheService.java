/**
 *
 */
package org.komea.demo.gitspy.cache;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Validate;
import org.komea.demo.gitspy.configuration.GitSpyConfigurationBean;
import org.komea.demo.gitspy.repository.domain.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class defines the repository cache service. This service handles the
 * creation of folders to store the repository informations.
 *
 * @author sleroy
 *
 */
@Service
public class RepositoryCacheService {

	@Autowired
	private GitSpyConfigurationBean	configurationBean;
	private File					cacheFolder;

	private static final Logger		LOGGER	= LoggerFactory.getLogger(RepositoryCacheService.class);

	@PostConstruct
	public void initCache() {
		this.cacheFolder = new File(this.configurationBean.getRepositoryCacheDir());
		LOGGER.info("Initialization of the cache folder in {}",
		            this.cacheFolder);
		this.cacheFolder.mkdirs();

	}

	/**
	 * This class allocates a folder to store informations for a repository if
	 * necessary.
	 *
	 * @param _repository
	 *            the repository
	 * @return the new cache folder.
	 */
	public File obtainRepositoryCacheFolder(final Repository _repository) {
		final File repositoryFile = this.getRepositoryFolder(_repository);
		if (!repositoryFile.exists()) {
			LOGGER.debug("Creation of repository {}", repositoryFile);
			final boolean mkdirs = repositoryFile.mkdirs();
			Validate.isTrue(mkdirs);
		}

		return repositoryFile;


	}

	/**
	 * Returns the repository folder.
	 * @param _repository the repository.
	 * @return
	 */
	private File getRepositoryFolder(final Repository _repository) {
		return new File(this.cacheFolder, _repository.getType()
		                + _repository.getId());
	}

}
