package org.komea.core.model.validation;

import java.util.Collection;
import java.util.List;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;

public class KomeaModelValidator {
	

	public static boolean validate(final IKomeaEntity entity) {
		final IEntityType type = entity.getType();
		if (type == null) {
			return false;
		}
		final List<IReference> properties = entity.getType().getProperties();
		for (final IReference property : properties) {
			if (!validate(entity, property)) {
				return false;
			}
		}
		return true;
	}


	public static boolean validate(final IKomeaEntity entity, final IReference property) {
		final Object value = entity.value(property);
		if (property.isMandatory() && value == null) {
			return false;
		}
		if (property.isMany()) {
			return validateManyReference(property, value);
		} else {

			return validateType(value, property.getType());
		}
	}

	private static boolean validateManyReference(final IReference property,
			final Object value) {
		if (!(value instanceof Collection)) {
			return false;
		}
		final Collection<?> collection = (Collection<?>) value;
		for (final Object element : collection) {
			if (!validateType(element, property.getType())) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateType(final Object value, final IType type) {
		if (type.isPrimitive()) {
			String name = value.getClass().getSimpleName();
			return type.getName().equals(name);
		}
		final IKomeaEntity evalue = (IKomeaEntity) value;
		return type.equals(evalue.getType());

	}

}
