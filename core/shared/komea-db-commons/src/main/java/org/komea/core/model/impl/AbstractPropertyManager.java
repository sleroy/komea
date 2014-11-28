package org.komea.core.model.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.komea.core.model.validation.KomeaModelValidator;
import org.komea.core.schema.IReference;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public abstract class AbstractPropertyManager {
	protected final OrientVertex vertex;
	protected final IReference reference;

	public AbstractPropertyManager(final OrientVertex vertex,
			final IReference reference) {
		super();
		this.vertex = vertex;
		this.reference = reference;

	}

	public abstract <T> T get();

	public void set(final Object value) {
		Validate.isTrue(!this.reference.isMany(),
				"Reference is a collection: use add/remove to update its content.");
		validateType(value);
		doSet(value);
	}
	
	protected abstract void doSet(final Object value);

	public abstract void remove(final Object value);

	public void add(final Object value) {
		Validate.isTrue(this.reference.isMany(),
				"Reference is not a collection: use set to update its content.");
		validateType(value);
		doAdd(value);

	}

	private void validateType(final Object value) {
		Validate.isTrue(
				KomeaModelValidator.validateType(value,
						this.reference.getType()), "Illegal value type");
	}

	protected abstract void doAdd(final Object value);

	public void addAll(final Collection<?> values) {
		Validate.isTrue(this.reference.isMany(),
				"Reference is not a collection: use set to update its content.");
		doAddAll(values);

	}

	protected abstract void doAddAll(final Collection<?> input);

}