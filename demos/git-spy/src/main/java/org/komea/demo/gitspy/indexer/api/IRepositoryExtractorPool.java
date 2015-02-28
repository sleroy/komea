/**
 *
 */
package org.komea.demo.gitspy.indexer.api;

import org.komea.demo.gitspy.repository.domain.Repository;

/**
 * This interface defines the pool to launch an extraction.
 * @author sleroy
 *
 */
public interface IRepositoryExtractorPool {
	/**
	 * Submits a job  : the
	 * @param _repository
	 */
	public void submitJob(Repository _repository);
}
