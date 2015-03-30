package org.komea.core.model.viz;

import org.komea.orientdb.viz.ILabelProvider;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public class KomeaModelLabelProvider implements ILabelProvider {

	@Override
	public String label(final Vertex v) {

		OrientVertex ov = (OrientVertex) v;
		if (v.getProperty("name") != null) {
			return v.getProperty("name");
		}
		return ov.getLabel();
	}

	@Override
	public String label(final Edge e) {
		OrientEdge oe = (OrientEdge) e;
		String label = oe.getLabel();
		String[] split = label.split("_");
		if (split.length > 1) {
			return split[1];
		} else {
			return label;
		}
	}

}
