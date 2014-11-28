package org.komea.core.schema.impl;

import java.util.List;
import java.util.Map;

import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Schema implements IKomeaSchema {
	private final String name;
	private final List<IEntityType> types;
	private transient Map<String, IEntityType> typesFromName;

	public Schema(final String name) {
		super();
		this.name = name;
		this.types = Lists.newArrayList();
		this.typesFromName = Maps.newHashMap();
	}

	@Override
	public void addType(final IEntityType type) {
		this.types.add(type);
		if (type instanceof EntityType) {
			((EntityType) type).setSchema(this);
		}
	}

	@Override
	public List<IEntityType> getTypes() {
		return this.types;
	}

	@Override
	public IEntityType findType(final String name) {
		IEntityType result = this.typesFromName.get(name);
		if (result == null) {
			for (IEntityType type : this.types) {
				if (type.getName().equals(name)) {
					this.typesFromName.put(name, type);
					return type;
				}
			}
		}
		return result;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
