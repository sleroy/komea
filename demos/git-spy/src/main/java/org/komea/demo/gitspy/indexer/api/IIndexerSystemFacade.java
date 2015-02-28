/**
 *
 */
package org.komea.demo.gitspy.indexer.api;

/**
 * This interface defines the facade to trig the update/analysis
 * @author sleroy
 *
 */
public interface IIndexerSystemFacade {



	/**
	 * Requests the synchronization of the repositories.
	 */
	public void requestSynchronization();
}
