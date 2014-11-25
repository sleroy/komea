package org.komea.core.schema;

import java.util.List;

public interface IEntityType extends IType {
	void addProperty(IReference reference);

	IReference findProperty(String name);

	List<IReference> getProperties();

	IEntityType getSuperType();

	void setSuperType(IEntityType type);
}
