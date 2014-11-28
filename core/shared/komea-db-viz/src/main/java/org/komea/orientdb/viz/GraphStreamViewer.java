package org.komea.orientdb.viz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.google.common.collect.Maps;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class GraphStreamViewer {
	private final ILabelProvider labelProvider;
	private final IAttributesProvider attributesProvider;

	
	public GraphStreamViewer(final ILabelProvider labelProvider,
			final IAttributesProvider attributesProvider) {
		super();
		this.labelProvider = labelProvider;
		this.attributesProvider = attributesProvider;
	}
	

	public void display(final String name, final InputStream css, final Graph graph)
			throws IOException {
		org.graphstream.graph.Graph vizgraph = new SingleGraph(name);
		vizgraph.setStrict(false);
		vizgraph.setAutoCreate(true);
		vizgraph.display();

		String stylesheet = IOUtils.toString(css);
		vizgraph.setAttribute("ui.stylesheet", stylesheet);

		int id = 0;
		Map<Vertex, Node> nodesMap = Maps.newHashMap();
		for (Vertex v : graph.getVertices()) {
			Node node = vizgraph.addNode("n" + id++);
			node.setAttribute("ui.label", this.labelProvider.label(v));
			for (Map.Entry<String, String> entry : this.attributesProvider
					.attributes(v).entrySet()) {
				node.setAttribute(entry.getKey(), entry.getValue());
			}
			nodesMap.put(v, node);
		}

		int eid = 0;
		for (Edge e : graph.getEdges()) {
			org.graphstream.graph.Edge edge = vizgraph.addEdge("e" + eid++,
					nodesMap.get(e.getVertex(Direction.OUT)),
					nodesMap.get(e.getVertex(Direction.IN)));
			edge.setAttribute("ui.label", this.labelProvider.label(e));
			for (Map.Entry<String, String> entry : this.attributesProvider
					.attributes(e).entrySet()) {
				edge.setAttribute(entry.getKey(), entry.getValue());
			}
		}
	}

}
