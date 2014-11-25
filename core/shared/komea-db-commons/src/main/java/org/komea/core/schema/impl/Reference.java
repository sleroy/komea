package org.komea.core.schema.impl;

import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;

public class Reference implements IReference {
	private boolean many;
	private boolean containment;
	private boolean mandatory;
	private final IType type;
	private final String name;

	public Reference(final String name, final IType type, final boolean many,
			final boolean containment) {
		super();
		this.many = many;
		this.containment = containment;
		this.type = type;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public IType getType() {
		return this.type;
	}

	@Override
	public boolean isContainment() {
		return this.containment;
	}

	@Override
	public boolean isMandatory() {
		return this.mandatory;
	}

	@Override
	public boolean isMany() {
		return this.many;
	}

	@Override
	public void setContainment(final boolean containment) {
		this.containment = containment;
	}

	@Override
	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;

	}

	@Override
	public void setMany(final boolean many) {
		this.many = many;
	}

}
