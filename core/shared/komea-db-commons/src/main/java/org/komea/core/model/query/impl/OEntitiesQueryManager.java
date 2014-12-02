package org.komea.core.model.query.impl;

import java.io.IOException;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.impl.OEntityIterable;
import org.komea.core.model.query.IEntitiesQueryManager;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.IKomeaStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.orientdb.session.impl.DatabaseConfiguration;

import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;

public class OEntitiesQueryManager implements IEntitiesQueryManager {
	private final IKomeaGraphStorage storage;

	public OEntitiesQueryManager(final IKomeaGraphStorage storage) {
		super();
		this.storage = storage;
	}

	public OEntitiesQueryManager(final IKomeaSchema schema,
			final DatabaseConfiguration db) {
		this(new OKomeaGraphStorage(schema, db));
	}

	@Override
	public IKomeaStorage getStorage() {
		return this.storage;
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
	public void close() throws IOException {
		this.storage.close();
	}

	@Override
	public Iterable<IKomeaEntity> select(final String query) {
		OCommandRequest command = this.storage.getGraph().command(
				new OCommandSQL(query));
		try {
			Iterable<Vertex> results = command.execute();
			return new OEntityIterable(results.iterator(),
					this.storage.getSchema());
		} catch (Exception e) {
			throw new KomeaQueryException("Unable to execute query "+query,e);
		}
	}

}
