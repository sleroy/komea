package org.komea.core.schema.impl;

import org.komea.core.schema.IPrimitiveType;

public class PrimitiveType extends AbstractType implements IPrimitiveType {

	private final Primitive primitive;

	public PrimitiveType(final Primitive primitive) {
		super();
		this.primitive = primitive;
	}

	@Override
	public String getName() {
		return this.primitive.getTypeName();
	}

	@Override
	public Primitive getPrimitive() {
		return this.primitive;
	}

	@Override
	public boolean isPrimitive() {
		return true;
	}

}
