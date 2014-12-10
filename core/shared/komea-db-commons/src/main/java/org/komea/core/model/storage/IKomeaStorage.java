package org.komea.core.model.storage;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.IKomeaEntityFactory;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;

/**
 * A storage for Komea entities. The storage is linked to a {@link IKomeaSchema}
 * that can be updated dynamically. Behavior of this update depends on the
 * storage implementation. For example, in a NoSQL database implementation, data
 * may not be dropped after a schema update.
 *
 * @author afloch
 *
 */
public interface IKomeaStorage extends IKomeaEntityFactory {

	void commit();

	/**
	 * Delete an entity from the storage.
	 *
	 * @param entity
	 */
	void delete(IKomeaEntity entity);

	/**
	 * Find all entities.
	 *
	 * @return
	 */
	Iterable<IKomeaEntity> entities();

	/**
	 * Find all entities of a given type.
	 *
	 * @param type
	 * @return
	 */
	Iterable<IKomeaEntity> entities(IEntityType type);

	/**
	 * Find all entities of a given type that use the same indexed value.
	 *
	 * @param type
	 * @param index
	 * @param value
	 * @return
	 */
	Iterable<IKomeaEntity> find(final IEntityType type, final String index, final Object value);

	/**
	 * Get an existing entity from its unique index value or create a new
	 * instance if it does'nt exits. Indexed value will be set in that case.
	 *
	 * @param type
	 *            entity type
	 * @param index
	 *            index name
	 * @param value
	 *            indexed value
	 * @return
	 */
	IKomeaEntity getOrCreate(final IEntityType type, final String index, final Object value);

	/**
	 * Get the schema used to type the stored entities.
	 *
	 * @return
	 */
	IKomeaSchema getSchema();

	/**
	 * Save an entity in the storage.
	 *
	 * @param entity
	 */
	void save(IKomeaEntity entity);

	/**
	 * Update the schema used to type the stored entities.
	 *
	 * @param schema
	 */
	void update(IKomeaSchema schema);
}
