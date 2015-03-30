package org.komea.core.schema.impl;

import org.komea.core.schema.IType;

import com.google.common.base.Objects;

public abstract class AbstractType implements IType {

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EntityType other = (EntityType) obj;
		return Objects.equal(getName(),other.getName());
	}

}
