package org.komea.core.schema;

public interface IReference {
	String getName();

	IType getType();

	boolean isContainment();

	boolean isMandatory();

	boolean isMany();

	void setContainment(boolean containment);

	void setMandatory(boolean mandatory);

	void setMany(boolean many);
}
