package org.komea.core.model.services;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;

public interface IStorageService {
	void delete(IKomeaEntity entity);

	Iterable<IKomeaEntity> entities(IEntityType type);

	IKomeaSchema getSchema();

	void save(IKomeaEntity entity);

	void update(IKomeaSchema schema);
}
