package org.komea.product.eventory.storage.service;

import java.util.Date;

import org.komea.product.eventory.dao.api.IEventSerializable;
import org.komea.product.eventory.rest.dto.ComplexEventDto;
import org.komea.product.eventory.rest.dto.SimpleEventDto;
import org.komea.product.eventory.utils.validation.ObjectValidation;
import org.springframework.stereotype.Service;

@Service
public class EventStorageValidator {

	public void validate(final IEventSerializable eventStorage) {

		final ObjectValidation objectValidation = new ObjectValidation();
		objectValidation.validateObject(eventStorage);
	}

	public void validateAndRefine(final ComplexEventDto _event) {
		if (_event.getDate() == null) {
			_event.setDate(new Date());
		}
		final ObjectValidation objectValidation = new ObjectValidation();
		objectValidation.validateObject(_event);

	}

	public void validateAndRefine(final SimpleEventDto _event) {
		if (_event.getDate() == null) {
			_event.setDate(new Date());
		}
		final ObjectValidation objectValidation = new ObjectValidation();
		objectValidation.validateObject(_event);

	}

}
