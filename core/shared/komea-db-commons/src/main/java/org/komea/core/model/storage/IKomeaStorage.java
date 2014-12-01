package org.komea.core.model.storage;

import java.io.Closeable;

import org.komea.core.model.IKomeaEntity;
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
public interface IKomeaStorage extends Closeable {

	/**
	 * Delete an entity from the storage.
	 * 
	 * @param entity
	 */
	void delete(IKomeaEntity entity);

	/**
	 * Find all entities of a given type.
	 * 
	 * @param type
	 * @return
	 */
	Iterable<IKomeaEntity> entities(IEntityType type);

	/**
	 * Find all entities.
	 * 
	 * @return
	 */
	Iterable<IKomeaEntity> entities();

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
	
	
	void commit();

	/**
	 * Update the schema used to type the stored entities.
	 * 
	 * @param schema
	 */
	void update(IKomeaSchema schema);
}
