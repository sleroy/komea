package org.komea.product.eventory.storage.service;

import java.util.Date;

import org.komea.product.eventory.database.model.EventStorage;
import org.springframework.stereotype.Service;

@Service
public class EventStorageValidator {

	public EventStorage validateAndRefine(final EventStorage eventStorage) {
		if (eventStorage.getDate() == null) {
			eventStorage.setDate(new Date());
		}
		final ObjectValidation objectValidation = new ObjectValidation();
		objectValidation.validateObject(eventStorage);
		return eventStorage;
	}

}
