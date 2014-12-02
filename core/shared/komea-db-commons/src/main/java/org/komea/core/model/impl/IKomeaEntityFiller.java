package org.komea.core.model.impl;

import org.komea.core.model.IKomeaEntity;

public interface IKomeaEntityFiller<T> {
	/**
	 * Put a pojo as a komea entity.
	 *
	 * @param _entity
	 *            entity.
	 */
	IKomeaEntity put(T _entity);
}
