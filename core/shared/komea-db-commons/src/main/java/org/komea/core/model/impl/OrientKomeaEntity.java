package org.komea.core.model.impl;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public class OrientKomeaEntity implements IKomeaEntity {
	private final IEntityType type;
	private final OrientVertex vertex;

	public OrientKomeaEntity(final IEntityType type, final OrientVertex vertex) {
		super();
		this.type = type;
		this.vertex = vertex;
	}

	@Override
	public void add(final IReference property, final Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public IEntityType getType() {
		return this.type;
	}

	@Override
	public void remove(final IReference property, final Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void set(final IReference property, final Object value) {
		if (property.getType().isPrimitive()) {
			this.vertex.setProperty(property.getName(), value);
		}

	}

	@Override
	public <T> T value(final IReference property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T value(final String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

}
