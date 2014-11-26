package org.komea.core.schema.services.impl;

import java.util.Collection;
import java.util.List;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;
import org.komea.core.schema.services.IValidatorService;

public class ValidatorService implements IValidatorService {

	@Override
	public boolean validate(final IKomeaEntity entity) {
		final IEntityType type = entity.getType();
		if (type == null) {
			return false;
		}
		final List<IReference> properties = entity.getType().getProperties();
		for (final IReference property : properties) {
			if (!this.validate(entity, property)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean validate(final IKomeaEntity entity, final IReference property) {
		final Object value = entity.value(property);
		if (property.isMandatory() && value == null) {
			return false;
		}
		if (property.isMany()) {
			return this.validateManyReference(property, value);
		} else {

			return this.validateType(value, property.getType());
		}
	}

	private boolean validateManyReference(final IReference property,
			final Object value) {
		if (!(value instanceof Collection)) {
			return false;
		}
		final Collection<?> collection = (Collection<?>) value;
		for (final Object element : collection) {
			if (!this.validateType(element, property.getType())) {
				return false;
			}
		}
		return true;
	}

	private boolean validateType(final Object value, final IType type) {
		if (type.isPrimitive()) {
			return type.getName().equals(value.getClass().getName());
		}
		final IKomeaEntity evalue = (IKomeaEntity) value;
		return type.equals(evalue.getType());

	}

}
