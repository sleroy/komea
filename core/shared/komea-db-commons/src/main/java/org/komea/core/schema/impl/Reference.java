package org.komea.core.schema.impl;

import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;

public class Reference implements IReference {
	private boolean mandatory;
	private boolean unique;
	private boolean indexed;
	private final IType type;
	private final String name;
	private ReferenceArity arity = ReferenceArity.ONE;
	private ReferenceKind kind = ReferenceKind.REFERENCE;


	public Reference(final String name, final IType type) {
		this.name = name;
		this.type = type;
	}

	public IReference setArity(final ReferenceArity arity) {
		this.arity = arity;
		return this;
	}

	public IReference setKind(final ReferenceKind kind) {
		this.kind = kind;
		return this;
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
		return this.kind==ReferenceKind.AGGREGATION;
	}

	@Override
	public boolean isContainment() {
		return this.kind==ReferenceKind.CONTAINMENT;
	}

	@Override
	public boolean isMandatory() {
		return this.mandatory;
	}

	@Override
	public boolean isMany() {
		return this.arity==ReferenceArity.MANY;
	}

	@Override
	public ReferenceKind getKind() {
		return this.kind;
	}

	@Override
	public ReferenceArity getArity() {
		return this.arity;
	}

	@Override
	public IReference enableMandatory() {
		this.mandatory=true;
		return this;
	}

	@Override
	public IReference disableMandatory() {
		this.mandatory=false;
		return this;
	}

	@Override
	public IReference enableIndexation() {
		this.indexed=true;
		return this;
	}

	@Override
	public IReference disableIndexation() {
		this.indexed=false;
		return this;
	}
	
	public boolean isIndexed() {
		return this.indexed;
	}

	@Override
	public IReference enableUnique() {
		this.unique=true;
		return this;
	}

	@Override
	public IReference disableUnique() {
		this.unique=false;
		return this;
	}

	public boolean isUnique() {
		return this.unique;
	}

}
