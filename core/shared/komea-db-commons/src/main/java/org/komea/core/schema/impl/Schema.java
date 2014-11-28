package org.komea.core.schema.impl;

import java.util.List;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;

import com.google.common.collect.Lists;

public class Schema implements IKomeaSchema {
	private final String name;
	private final List<IEntityType> types;

	public Schema(final String name) {
		super();
		this.name = name;
		this.types = Lists.newArrayList();
	}

	@Override
	public void addType(final IEntityType type) {
		this.types.add(type);
		if(type instanceof EntityType){
			((EntityType) type).setSchema(this);
		}
	}

	@Override
	public List<IEntityType> getTypes() {
		return this.types;
	}

	@Override
	public IEntityType findType(final String _name) {
		for (IEntityType type : this.types) {
			if(type.getName().equals(_name)){
				return type;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
