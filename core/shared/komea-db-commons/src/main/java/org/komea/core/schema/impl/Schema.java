package org.komea.core.schema.impl;

import java.util.List;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;

import com.google.common.collect.Lists;

public class Schema implements IKomeaSchema {
	private final String name;
	private final List<IEntityType> entities;

	public Schema(final String name) {
		super();
		this.name = name;
		this.entities = Lists.newArrayList();
	}

	@Override
	public void addType(final IEntityType type) {
		this.entities.add(type);
	}

	@Override
	public List<IEntityType> getEntities() {
		return this.entities;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
