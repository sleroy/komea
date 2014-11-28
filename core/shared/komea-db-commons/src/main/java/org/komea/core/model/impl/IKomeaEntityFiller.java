package org.komea.core.model.impl;

public interface IKomeaEntityFiller<T> {
	/**
	 * Put a pojo as a komea entity.
	 *
	 * @param _entity
	 *            entity.
	 */
	void put(T _entity);
}
