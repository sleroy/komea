/**
 *
 */
package org.komea.demo.gitspy.indexer;

import org.komea.demo.gitspy.configuration.GitSpyConfigurationBean;
import org.komea.demo.gitspy.indexer.api.IIndexerSystemFacade;
import org.komea.demo.gitspy.repository.dao.IRepositoryDAO;
import org.komea.demo.gitspy.repository.domain.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service defines the indexer system.
 * @author sleroy
 *
 */
@Service
public class IndexerSystemService implements IIndexerSystemFacade {

	@Autowired
	private GitSpyConfigurationBean configuration;

	@Autowired
	private IRepositoryDAO repositoryDAO;

	@Autowired
	private RepositoryIndexerPool repositoryIndexPool;

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexerSystemService.class);

	/* (non-Javadoc)
	 * @see org.komea.demo.gitspy.extractor.api.IExtractorSystemFacade#requestSynchronization()
	 */
	@Override
	public void requestSynchronization() {

		for (final Repository repository : this.repositoryDAO.findAll()) {
			LOGGER.info("Checking status of repository {}", repository.getName());
			if (repository.isOutdated(this.configuration.getMinRefresh())) {
				LOGGER.info("Repository is outdate {}", repository.getName());
				this.repositoryIndexPool.submitJob(repository);
			}
		}
	}
}
