package org.komea.core.schema.impl;

import org.komea.core.schema.IType;

public abstract class AbstractType implements IType {

	@Override
	public String toString() {
		return getName();
	}
}
