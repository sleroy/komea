package org.komea.core.model.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.komea.core.model.IKomeaEntity;
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
	private final IEntityType type;

	private final OrientVertex vertex;

	public OKomeaEntity(final IEntityType type, final OrientVertex vertex) {
		super();
		this.type = type;
		this.vertex = vertex;
	}

	public OrientVertex getVertex() {
		return this.vertex;
	}

	private AbstractPropertyManager buildReferenceManager(
			final IReference property) {
		AbstractPropertyManager updater;
		Validate.isTrue(this.type.getProperties().contains(property),
				"Property doesn't exists in the entity type");
		if (property.getType().isPrimitive()) {
			updater = new OEntityAttributeManager(this.vertex, property);
		} else {
			updater = new OEntityReferenceManager(this, property);
		}
		return updater;
	}

	@Override
	public void add(final IReference property, final Object value) {
		buildReferenceManager(property).addReference(value);
	}

	@Override
	public IEntityType getType() {
		return this.type;
	}

	@Override
	public void remove(final IReference property, final Object value) {
		buildReferenceManager(property).remove(value);
	}

	@Override
	public void remove(final String propertyName, final Object value) {
		IReference property = this.type.findProperty(propertyName);
		Validate.notNull(property, "Property doesn't exists in the entity type");
		remove(property, value);
	}

	@Override
	public void set(final IReference property, final Object value) {
		buildReferenceManager(property).set(value);
	}

	@Override
	public <T> T value(final IReference property) {
		return buildReferenceManager(property).get();
	}

	@Override
	public <T> T value(final String propertyName) {
		IReference property = this.type.findProperty(propertyName);
		Validate.notNull(property, "Property doesn't exists in the entity type");
		return value(this.type.findProperty(propertyName));
	}

	@Override
	public void add(final String propertyName, final Object value) {
		IReference property = this.type.findProperty(propertyName);
		Validate.notNull(property, "Property doesn't exists in the entity type");
		add(property, value);
	}

	@Override
	public void set(final String propertyName, final Object value) {
		IReference property = this.type.findProperty(propertyName);
		Validate.notNull(property, "Property doesn't exists in the entity type");
		set(property, value);
	}

	@Override
	public void addAll(final IReference property, final Collection<?> values) {
		buildReferenceManager(property).addAll(values);
	}

	@Override
	public void addAll(final String propertyName, final Collection<?> values) {
		IReference property = this.type.findProperty(propertyName);
		Validate.notNull(property, "Property doesn't exists in the entity type");
		addAll(property, values);
	}

	@Override
	public int hashCode() {
		return ((this.vertex == null) ? 0 : this.vertex.hashCode());
	}

	@Override
	public String toString() {
		return "OKomeaEntity [type=" + this.type + ", vertex=" + this.vertex + "]";
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OKomeaEntity other = (OKomeaEntity) obj;
		if (this.vertex == null) {
			if (other.vertex != null) {
				return false;
			}
		} else if (!this.vertex.equals(other.vertex)) {
			return false;
		}
		return true;
	}

	@Override
	public Iterable<IKomeaEntity> references(final IReference property) {
		return buildReferenceManager(property).get();
	}

	@Override
	public Iterable<IKomeaEntity> references(final String propertyName) {
		IReference property = this.type.findProperty(propertyName);
		Validate.notNull(property, "Property doesn't exists in the entity type");
		return references(property);
	}

}
