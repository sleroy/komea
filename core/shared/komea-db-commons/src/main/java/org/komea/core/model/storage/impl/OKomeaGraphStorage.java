package org.komea.core.model.storage.impl;

import java.io.IOException;

import org.apache.commons.lang.Validate;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.impl.OEntityIterable;
import org.komea.core.model.impl.OKomeaEntity;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.orientdb.session.IGraphSessionFactory;
import org.komea.orientdb.session.impl.DatabaseConfiguration;
import org.komea.orientdb.session.impl.OrientGraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * A storage service that will store Komea entities as nodes in a graph OrientDB
 * database.
 *
 * @author afloch
 *
 */
public class OKomeaGraphStorage implements IKomeaGraphStorage {
	private final IGraphSessionFactory	sessionsFactory;
	private IKomeaSchema	           schema;
	private OrientGraph	               graph;
	private final static Logger	       LOGGER	= LoggerFactory.getLogger(OKomeaGraphStorage.class);

	public OKomeaGraphStorage(final IKomeaSchema schema, final DatabaseConfiguration _configuration) {
		super();
		this.sessionsFactory = new OrientGraphDatabaseFactory(_configuration);
		this.update(schema);
	}

	public OKomeaGraphStorage(final IKomeaSchema schema, final IGraphSessionFactory sessionsFactory) {
		super();
		this.sessionsFactory = sessionsFactory;
		this.update(schema);
	}

	@Override
	public void close() throws IOException {
		this.getGraph().commit();
		this.getGraph().shutdown();
	}

	@Override
	public void delete(final IKomeaEntity entity) {
		if (entity instanceof OKomeaEntity) {
			final OKomeaEntity oEntity = (OKomeaEntity) entity;
			this.graph.removeVertex(oEntity.getVertex());
		} else {
			LOGGER.warn("Entity {} can't be managed by the storage. It won't be deleted", entity.getClass());
		}

	}

	@Override
	public Iterable<IKomeaEntity> entities() {
		final Iterable<Vertex> vertices = this.graph.getVertices();
		return new OEntityIterable(vertices.iterator(), this.schema);
	}

	@Override
	public Iterable<IKomeaEntity> entities(final IEntityType type) {
		Validate.isTrue(type.getSchema() != null && type.getSchema().equals(this.schema),
		        "Type is not defined in the same schema than the one used by the storage");
		final Iterable<Vertex> vertices = this.graph.getVerticesOfClass(type.getName());
		return new OEntityIterable(vertices.iterator(), this.schema);
	}

	@Override
	public void commit() {
		this.graph.commit();
	}
	
	@Override
	public OrientGraph getGraph() {
		if (this.graph == null) {
			this.graph = this.sessionsFactory.getGraph();
		}
		return this.graph;
	}

	@Override
	public IKomeaSchema getSchema() {
		return this.schema;
	}

	@Override
	public void save(final IKomeaEntity entity) {
		// nothing to do here since a Komea entity is added to the graph by the
		// factory

	}

	/**
	 * Set the schema of the storage : create vertex types and properties if
	 * needed. All previous types and properties definitions are dropped.
	 */
	@Override
	public void update(final IKomeaSchema _schema) {
		this.schema = _schema;
		new OGraphSchemaUpdater(this.getGraph()).update(_schema);
	}

}
