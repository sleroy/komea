package org.komea.core.model;

import java.text.MessageFormat;

import org.komea.core.exceptions.KomeaRuntimeException;
import org.komea.core.schema.IReference;

public class MissingFieldException extends KomeaRuntimeException {

	public MissingFieldException(final IReference _property) {
		this(_property.getName() + "of type " + _property.getType());
	}

	public MissingFieldException(final String _propertyName) {
		super(MessageFormat.format("Property {0} does not exist in the entity type.", _propertyName));
	}

}
