package org.komea.core.schema.services;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.IReference;

public interface IValidatorService {
	/**
	 * Check if the content of a software entity respect its data schema.
	 *
	 * @param entity
	 * @return
	 */
	boolean validate(IKomeaEntity entity);

	/**
	 * Check if an entity is a legal value for a reference property.
	 *
	 * @param entity
	 * @param property
	 * @return
	 */
	boolean validate(IKomeaEntity entity, IReference property);
}
