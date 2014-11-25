package org.komea.core.schema;

import java.util.List;

public interface IKomeaSchema {
	void addType(IEntityType type);

	List<IEntityType> getEntities();

	String getName();

}
