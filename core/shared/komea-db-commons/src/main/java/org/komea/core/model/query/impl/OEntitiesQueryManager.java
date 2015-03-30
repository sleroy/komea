package org.komea.core.model.query.impl;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.impl.OEntityIterable;
import org.komea.core.model.query.IEntitiesQueryManager;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.IKomeaStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public class OEntitiesQueryManager implements IEntitiesQueryManager {
	private final IKomeaGraphStorage	storage;
	private static final Logger	     LOGGER	= LoggerFactory.getLogger(OEntitiesQueryManager.class);

	public OEntitiesQueryManager(final IKomeaGraphStorage storage) {
		super();
		this.storage = storage;
	}

	public OEntitiesQueryManager(final IKomeaSchema schema, final OrientGraph db) {
		this(new OKomeaGraphStorage(schema, db));
	}

	@Override
	public long count() {
		return this.storage.getGraph().countVertices();
	}

	@Override
	public long count(final IEntityType type) {
		return this.storage.getGraph().countVertices(type.getName());
	}

	@Override
	public IKomeaStorage getStorage() {
		return this.storage;
	}

	@Override
	public Iterable<IKomeaEntity> select(final String query) {
		final OCommandRequest command = this.storage.getGraph().command(new OCommandSQL(query));
		try {
			final Iterable<Vertex> results = command.execute();
			return new OEntityIterable(results.iterator(), this.storage.getSchema());
		} catch (final Exception e) {
			throw new KomeaQueryException("Unable to execute query " + query, e);
		}
	}

}
