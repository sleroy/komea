package org.komea.core.model.viz;

import java.util.Map;

import org.komea.orientdb.viz.IAttributesProvider;

import com.google.common.collect.Maps;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class KomeaModelAttributesProvider implements IAttributesProvider{

	@Override
	public Map<String, String> attributes(final Vertex v) {
		Map<String, String> attributes = Maps.newHashMap();
		return attributes;
	}

	@Override
	public Map<String, String> attributes(final Edge e) {
		Map<String, String> attributes = Maps.newHashMap();
		return attributes;
	}

}
