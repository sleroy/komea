package org.komea.core.model.query;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.storage.IKomeaStorage;
import org.komea.core.schema.IEntityType;

public interface IEntitiesQueryManager {

	/**
	 * Count all entities stored in the storage.
	 *
	 * @return
	 */
	long count();

	/**
	 * Count the number of entities stored in the storage that are instance of
	 * an entity type.
	 *
	 * @param storage
	 * @param type
	 * @return
	 */
	long count(IEntityType type);

	/**
	 * Get the storage used by the query manager.
	 *
	 * @return
	 */
	IKomeaStorage getStorage();

	/**
	 * Execute an entities selection query on the storage.
	 *
	 * @param query
	 * @return
	 */
	Iterable<IKomeaEntity> select(String query);
}
