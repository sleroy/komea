package org.komea.orientdb.viz;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public interface ILabelProvider {
	String label(Vertex v);

	String label(Edge e);
}