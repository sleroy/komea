package org.komea.orientdb.viz;

import java.util.Map;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public interface IAttributesProvider {

	public Map<String, String> attributes(Vertex v);

	public Map<String, String> attributes(Edge e);
}