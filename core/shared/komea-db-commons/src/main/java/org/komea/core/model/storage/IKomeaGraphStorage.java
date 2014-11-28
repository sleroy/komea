package org.komea.core.model.storage;

import com.tinkerpop.blueprints.Graph;

/**
 * A Komea storage using a blueprints Graph implementation to store entities and
 * their relationships.
 * 
 * @author afloch
 *
 */
public interface IKomeaGraphStorage extends IKomeaStorage {

	/**
	 * Get the blueprints graph used to store entities and relations.
	 * 
	 * @return
	 */
	Graph getGraph();
}
