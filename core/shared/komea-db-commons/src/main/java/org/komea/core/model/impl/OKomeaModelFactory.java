package org.komea.core.model.impl;

import org.apache.commons.lang.Validate;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.IKomeaFactory;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.core.schema.IEntityType;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public class OKomeaModelFactory implements IKomeaFactory {
	private final OKomeaGraphStorage storageService;

	public OKomeaModelFactory(final OKomeaGraphStorage storageService) {
		super();
		this.storageService = storageService;
	}

	public <T> IKomeaEntityFiller<T> newEntityFiller(
			final IEntityType _humanType) {

		return new KomeaEntityFiller<T>(this, _humanType);
	}

	@Override
	public IKomeaEntity newInstance(final IEntityType type) {
		Validate.isTrue(
				type.getSchema() != null
						&& type.getSchema().equals(
								this.storageService.getSchema()),
				"Type is not defined in the same schema than the one used by the storage");
		final OrientGraph graph = this.storageService.getGraph();
		final OrientVertex vertex = graph.addVertex("class:" + type.getName());
		return new OKomeaEntity(type, vertex);
	}

}
