package org.komea.core.model.services.impl;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.services.IStorageService;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.orientdb.session.IGraphSessionFactory;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 * A storage service that will store Komea entities as nodes in a graph OrientDB
 * database.
 *
 * @author afloch
 *
 */
public class OrientGraphStorageService implements IStorageService {
	private final IGraphSessionFactory sessionsFactory;
	private IKomeaSchema schema;
	private OrientGraph graph;

	public OrientGraphStorageService(final IKomeaSchema schema,
			final IGraphSessionFactory sessionsFactory) {
		super();
		this.sessionsFactory = sessionsFactory;
		update(schema);
	}

	@Override
	public void delete(final IKomeaEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterable<IKomeaEntity> entities(final IEntityType type) {
		// TODO Auto-generated method stub
		return null;
	}

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
		// TODO Auto-generated method stub

	}

	/**
	 * Set the schema of the storage : create vertex types and properties if
	 * needed.
	 */
	@Override
	public void update(final IKomeaSchema _schema) {
		final OrientGraph graphInstance = getGraph();
		new OrientGraphSchemaUpdater(graphInstance).update(_schema);
	}

}
