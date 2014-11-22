package org.komea.product.eventory.database.session.api;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public interface IGraphSessionFactory {

	/**
	 * Obtains a graph representation of the database
	 *
	 * @return the orient graph
	 */
	public OrientGraph getGraph();

}