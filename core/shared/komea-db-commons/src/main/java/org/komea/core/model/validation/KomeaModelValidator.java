package org.komea.core.model.validation;

import java.util.Collection;
import java.util.List;

import org.komea.core.model.IKomeaEntity;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KomeaModelValidator {

	public static boolean validate(final IKomeaEntity entity) {
		final IEntityType type = entity.getType();
		if (type == null) { return false; }
		final List<IReference> properties = entity.getType().getProperties();
		for (final IReference property : properties) {
			if (!validate(entity, property)) { return false; }
		}
		return true;
	}

	public static boolean validate(final IKomeaEntity entity, final IReference property) {
		final Object value = entity.value(property);
		if (property.isMandatory() && value == null) { return false; }
		if (property.isMany()) {
			return validateManyReference(property, value);
		} else {

			return validateType(value, property.getType());
		}
	}

	public static boolean validateType(final Object value, final IType type) {
		if (type.isPrimitive()) {
			final boolean isValidPrimitiveType = isValidPrimitiveType(value, type);
			if (!isValidPrimitiveType) {
				LOGGER.error("Invalid primitive type provided for the type {} and the value {}", type, value);

			}
			return isValidPrimitiveType;

		} else {
			final boolean validReferenceType = isValidReferenceType(value, type);
			if (!validReferenceType) {
				LOGGER.error("Invalid reference type provided for the type {} and the value {}", type, value);
			}
			return validReferenceType;
		}

	}

	private static boolean isValidPrimitiveType(final Object value, final IType type) {
		final String name = value.getClass().getSimpleName();
		return type.getName().equals(name);
	}

	private static boolean isValidReferenceType(final Object value, final IType type) {
		final IKomeaEntity evalue = (IKomeaEntity) value;
		return type != null && value != null && type.equals(evalue.getType());
	}

	private static boolean validateManyReference(final IReference property, final Object value) {
		if (!(value instanceof Collection)) { return false; }
		final Collection<?> collection = (Collection<?>) value;
		for (final Object element : collection) {
			if (!validateType(element, property.getType())) { return false; }
		}
		return true;
	}

	private static final Logger	LOGGER	= LoggerFactory.getLogger(KomeaModelValidator.class);
}
