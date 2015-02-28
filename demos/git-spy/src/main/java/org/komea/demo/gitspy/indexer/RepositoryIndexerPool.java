/**
 *
 */
package org.komea.demo.gitspy.indexer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.komea.demo.gitspy.configuration.GitSpyConfigurationBean;
import org.komea.demo.gitspy.indexer.api.IRepositoryExtractorPool;
import org.komea.demo.gitspy.repository.domain.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This interface defines the service to handle concurrent extractions of the repositories.
 *
 * @author sleroy
 *
 */
@Component
public class RepositoryIndexerPool implements IRepositoryExtractorPool {

	private ExecutorService executor;


	@Autowired
	private GitSpyConfigurationBean gitConfiguration;

	@Autowired
	private RepositoryIndexerTaskFactory taskFactory;

	public void after() {
		this.executor.shutdown();
	}

	@PostConstruct
	public void init() {
		this.executor = Executors.newFixedThreadPool(this.gitConfiguration.getExtractorThreads());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.demo.gitspy.extractor.api.IRepositoryExtractorPool#submitJob
	 * (org.komea.demo.gitspy.repository.domain.Repository)
	 */
	@Override
	public void submitJob(final Repository _repository) {
		this.executor.submit(this.taskFactory.newIndexer(_repository));

	}

}
