package org.komea.core.schema.impl;

import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;

public class Reference implements IReference {
	private boolean many;
	private boolean containment;
	private boolean mandatory;
	private boolean aggregation;
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
	public boolean isAggregation() {
		return this.aggregation;
	}
	
	@Override
	public IReference setAggregation(final boolean aggregation) {
		this.aggregation = aggregation;
		return this;
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
	public IReference setContainment(final boolean containment) {
		this.containment = containment;
		return this;
	}

	@Override
	public IReference setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
		return this;

	}

	@Override
	public IReference setMany(final boolean many) {
		this.many = many;
		return this;
	}

}
