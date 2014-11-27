package org.komea.event.storage.service;

import java.util.Date;

import org.komea.event.model.api.IBasicEventInformations;
import org.komea.orientdb.session.document.IODocument;

public class EventStorageValidatorService {

	public boolean hasAllFields(final IODocument _document) {
		return _document.containsField(IBasicEventInformations.FIELD_DATE)
				&& _document
				.containsField(IBasicEventInformations.FIELD_EVENT_TYPE)
				&& _document
				.containsField(IBasicEventInformations.FIELD_PROVIDER);
	}

	public boolean isValidDateField(final IODocument _document) {
		return _document.field(IBasicEventInformations.FIELD_DATE) instanceof Date;
	}

	public boolean isValidEventTypeField(final IODocument _document) {
		return _document.field(IBasicEventInformations.FIELD_EVENT_TYPE) instanceof String;
	}

	public boolean isValidProviderField(final IODocument _document) {
		return _document.field(IBasicEventInformations.FIELD_PROVIDER) instanceof String;
	}

	public boolean validate(final IODocument _document) {
		return this.hasAllFields(_document) && this.isValidDateField(_document)
				&& this.isValidProviderField(_document)
				&& this.isValidEventTypeField(_document);
	}

}
