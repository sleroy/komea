package org.komea.core.model;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;

/**
 * A Komea entity is a typed data structure.
 *
 * @author afloch
 *
 */
public interface IKomeaEntity {
	void add(IReference property, Object value);

	/**
	 * Get the type of the entity.
	 *
	 * @return
	 */
	IEntityType getType();

	void remove(IReference property, Object value);

	void set(IReference property, Object value);

	/**
	 * Get the value for a given reference property of entity type.
	 *
	 * @param property
	 * @return
	 */
	<T> T value(IReference property);

	/**
	 * Get the value for a given reference property of entity type.
	 *
	 * @param propertyName
	 *            name of the property
	 * @return the value of the first reference named as propertyName
	 */
	<T> T value(String propertyName);
}
