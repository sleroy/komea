package org.komea.core.model.storage;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * A Komea storage using an Orient DB Graph implementation to store entities and
 * their relationships.
 * 
 * @author afloch
 *
 */
public interface IKomeaGraphStorage extends IKomeaStorage {

	/**
	 * Get the Orient DB graph used to store entities and relations.
	 * 
	 * @return
	 */
	OrientGraph getGraph();


}
