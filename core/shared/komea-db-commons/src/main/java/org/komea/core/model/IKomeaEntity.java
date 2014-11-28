package org.komea.core.model;

import java.util.Collection;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;

/**
 * A Komea entity is a typed data structure (see {@link IEntityType}).
 *
 * @author afloch
 *
 */
public interface IKomeaEntity {

	/**
	 * Add a value into a collection reference.
	 * 
	 * @param property
	 * @param value
	 */
	void add(IReference property, Object value);

	/**
	 * Add a value into a collection reference.
	 * 
	 * @param property
	 * @param value
	 */
	void add(String property, Object value);

	/**
	 * Add values into a collection reference.
	 * 
	 * @param property
	 * @param values
	 */
	void addAll(IReference property, Collection<?> values);

	/**
	 * Add values into a collection reference.
	 * 
	 * @param property
	 * @param values
	 */
	void addAll(String property, Collection<?> values);

	/**
	 * Get the type of the entity.
	 *
	 * @return
	 */
	IEntityType getType();

	/**
	 * Remove a value from a collection reference.
	 * 
	 * @param property
	 * @param value
	 */
	void remove(IReference property, Object value);

	/**
	 * Remove a value from a collection reference.
	 * 
	 * @param property
	 * @param value
	 */
	void remove(String property, Object value);

	/**
	 * Set a reference to a value.
	 * 
	 * @param property
	 * @param value
	 */
	void set(IReference property, Object value);

	/**
	 * Set a reference to a value.
	 * 
	 * @param property
	 * @param value
	 */
	void set(String property, Object value);

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

	/**
	 * Get all values stored in an entity reference.
	 * 
	 * @param property
	 * @return
	 */
	Iterable<IKomeaEntity> references(IReference property);

	/**
	 * Get all values stored in an entity reference.
	 * 
	 * @param propertyName
	 * @return
	 */
	Iterable<IKomeaEntity> references(String propertyName);
}
