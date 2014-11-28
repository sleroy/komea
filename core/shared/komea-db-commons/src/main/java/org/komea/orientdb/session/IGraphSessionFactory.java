package org.komea.orientdb.session;

import java.io.Closeable;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public interface IGraphSessionFactory extends Closeable {

	/**
	 * Obtains a graph representation of the database
	 *
	 * @return the orient graph
	 */
	public OrientGraph getGraph();

}