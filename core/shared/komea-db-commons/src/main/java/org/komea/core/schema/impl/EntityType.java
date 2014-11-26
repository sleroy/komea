package org.komea.core.schema.impl;

import java.util.List;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;

import com.google.common.collect.Lists;

public class EntityType extends AbstractType implements IEntityType {
	private final List<IReference> references;
	private IEntityType superType;
	private final String name;

	public EntityType(final String name) {
		this.name = name;
		this.references = Lists.newArrayList();
	}

	@Override
	public void addProperty(final IReference reference) {
		this.references.add(reference);

	}

	@Override
	public IReference findProperty(final String _name) {
		for (final IReference ref : this.references) {
			if (ref.getName().equals(_name)) {
				return ref;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<IReference> getProperties() {
		return this.references;
	}

	@Override
	public IEntityType getSuperType() {
		return this.superType;
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}

	@Override
	public void setSuperType(final IEntityType type) {
		this.superType = type;
	}

}
