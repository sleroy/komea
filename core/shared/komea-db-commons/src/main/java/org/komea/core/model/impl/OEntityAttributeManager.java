package org.komea.core.model.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.komea.core.schema.IReference;

import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public class OEntityAttributeManager extends AbstractPropertyManager {

	public OEntityAttributeManager(final OrientVertex vertex,
			final IReference reference) {
		super(vertex, reference);
		validateReference();
	}

	@Override
	public <T> T get() {

		return this.vertex.getProperty(this.reference.getName());

	}

	@Override
	protected void doSet(final Object value) {

		this.vertex.setProperty(this.reference.getName(), value);

	}

	private void validateReference() {
		Validate.isTrue(isAttribute(), "Reference is not on a primitive type.");
	}

	private boolean isAttribute() {
		return this.reference.getType().isPrimitive();
	}

	@Override
	public void remove(final Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doAdd(final Object value) {
		@SuppressWarnings("unchecked")
		Collection<Object> values = (Collection<Object>) this.vertex
				.getProperty(this.reference.getName());
		if (values == null) {
			values = Lists.newArrayList();
		}
		values.add(value);
		this.vertex.setProperty(this.reference.getName(), values);
	}

	@Override
	protected void doAddAll(final Collection<?> input) {
		@SuppressWarnings("unchecked")
		Collection<Object> values = (Collection<Object>) this.vertex
				.getProperty(this.reference.getName());
		if (values == null) {
			values = Lists.newArrayList();
		}
		values.addAll(input);
		this.vertex.setProperty(this.reference.getName(), values);
	}

}
