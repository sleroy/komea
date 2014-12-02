package org.komea.core.model.storage.impl;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.impl.IKomeaEntityFiller;
import org.komea.core.model.impl.KomeaEntityFiller;
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
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

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
		if (graph != null) graph.shutdown();
		this.graph = null;
		this.sessionsFactory.close();
	}

	@Override
	public void commit() {
		this.graph.commit();
	}

	@Override
	public IKomeaEntity create(final IEntityType type) {
		Validate.isTrue(type.getSchema() != null && type.getSchema().equals(this.getSchema()),
				"Type is not defined in the same schema than the one used by the storage");
		final OrientVertex vertex = this.graph.addVertex("class:" + type.getName());
		return new OKomeaEntity(type, vertex);
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
	public Iterable<IKomeaEntity> find(final IEntityType type, final String index, final Object value) {
		Validate.notNull(type.findProperty(index), "Property " + type.getName() + "." + index
				+ " doesn't exists in the entity type " + type.getName());
		Validate.isTrue(type.findProperty(index).isIndexed(), "Property " + type.getName() + "." + index
				+ " is not indexed");
		final Iterator<Vertex> vertices = this.graph.getVertices(type.getName() + "." + index, value).iterator();
		return new OEntityIterable(vertices, this.schema);
	}

	@Override
	public OrientGraph getGraph() {
		if (this.graph == null) {
			this.graph = this.sessionsFactory.getGraph();
		}
		return this.graph;
	}

	@Override
	public IKomeaEntity getOrCreate(final IEntityType type, final String index, final Object value) {
		Validate.notNull(type.findProperty(index), "Property " + type.getName() + "." + index
				+ " doesn't exists in the entity type " + type.getName());
		Validate.isTrue(type.findProperty(index).isUnique(), "Property " + type.getName() + "." + index
				+ " is not a unique index");
		final Iterator<Vertex> vertices = this.graph.getVertices(type.getName() + "." + index, value).iterator();
		if (vertices.hasNext()) {
			final Vertex next = vertices.next();
			final OKomeaEntity oKomeaEntity = new OKomeaEntity(type, (OrientVertex) next);
			return oKomeaEntity;
		} else {
			final IKomeaEntity created = this.create(type);
			created.set(index, value);
			return created;
		}
	}

	@Override
	public IKomeaSchema getSchema() {
		return this.schema;
	}

	public <T> IKomeaEntityFiller<T> newEntityFiller(final IEntityType _humanType) {

		return new KomeaEntityFiller<T>(this, _humanType);
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
