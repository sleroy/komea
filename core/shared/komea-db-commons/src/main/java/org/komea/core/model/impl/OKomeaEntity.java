package org.komea.core.model.impl;

import java.util.Collection;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.MissingFieldException;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

/**
 * Blueprints implementation of a Komea Entity
 *
 * @author afloch
 *
 */
public class OKomeaEntity implements IKomeaEntity {
	private final IEntityType	type;
	private final OrientVertex	vertex;

	public OKomeaEntity(final IEntityType type, final OrientVertex vertex) {
		super();
		this.type = type;
		this.vertex = vertex;
	}

	@Override
	public void add(final IReference property, final Object value) {
		this.buildReferenceManager(property).addReference(value);
	}

	@Override
	public void add(final String propertyName, final Object value) {
		final IReference property = this.type.findProperty(propertyName);
		if (property == null) { throw new MissingFieldException(propertyName); }
		this.add(property, value);
	}

	@Override
	public void addAll(final IReference property, final Collection<?> values) {
		this.buildReferenceManager(property).addAll(values);
	}

	@Override
	public void addAll(final String propertyName, final Collection<?> values) {
		final IReference property = this.type.findProperty(propertyName);
		if (property == null) { throw new MissingFieldException(propertyName); }
		this.addAll(property, values);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (this.getClass() != obj.getClass()) { return false; }
		final OKomeaEntity other = (OKomeaEntity) obj;
		if (this.vertex == null) {
			if (other.vertex != null) { return false; }
		} else if (!this.vertex.equals(other.vertex)) { return false; }
		return true;
	}

	@Override
	public IEntityType getType() {
		return this.type;
	}

	public OrientVertex getVertex() {
		return this.vertex;
	}

	@Override
	public int hashCode() {
		return this.vertex == null ? 0 : this.vertex.hashCode();
	}

	@Override
	public Iterable<IKomeaEntity> references(final IReference property) {
		return this.buildReferenceManager(property).get();
	}

	@Override
	public Iterable<IKomeaEntity> references(final String propertyName) {
		final IReference property = this.type.findProperty(propertyName);
		if (property == null) { throw new MissingFieldException(propertyName); }
		return this.references(property);
	}

	@Override
	public void remove(final IReference property, final Object value) {
		this.buildReferenceManager(property).remove(value);
	}

	@Override
	public void remove(final String propertyName, final Object value) {
		final IReference property = this.type.findProperty(propertyName);
		if (property == null) { throw new MissingFieldException(propertyName); }
		this.remove(property, value);
	}

	@Override
	public void set(final IReference property, final Object value) {
		this.buildReferenceManager(property).set(value);
	}

	@Override
	public void set(final String propertyName, final Object value) {
		final IReference property = this.type.findProperty(propertyName);
		if (property == null) { throw new MissingFieldException(propertyName); }
		this.set(property, value);
	}

	@Override
	public <T> T value(final IReference property) {
		return this.buildReferenceManager(property).get();
	}

	@Override
	public <T> T value(final String propertyName) {
		final IReference property = this.type.findProperty(propertyName);
		if (property == null) { throw new MissingFieldException(propertyName); }
		return this.value(this.type.findProperty(propertyName));
	}

	private AbstractPropertyManager buildReferenceManager(final IReference property) {
		AbstractPropertyManager updater;
		if (!this.type.getProperties().contains(property)) { throw new MissingFieldException(property); }

		if (property.getType().isPrimitive()) {
			updater = new OEntityAttributeManager(this.vertex, property);
		} else {
			updater = new OEntityReferenceManager(this, property);
		}
		return updater;
	}

}
