package org.komea.event.storage.impl;

import java.util.Date;

import org.komea.event.model.IBasicEventInformations;
import org.komea.event.model.beans.FlatEvent;

public class EventStorageValidatorService {

	public boolean hasAllFields(final FlatEvent _document) {
		return _document.containsField(IBasicEventInformations.FIELD_DATE)
				&& _document.containsField(IBasicEventInformations.FIELD_EVENT_TYPE)
				&& _document.containsField(IBasicEventInformations.FIELD_PROVIDER);
	}

	public boolean isValidDateField(final FlatEvent _document) {
		return _document.field(IBasicEventInformations.FIELD_DATE) instanceof Date;
	}

	public boolean isValidEventTypeField(final FlatEvent _document) {
		return _document.field(IBasicEventInformations.FIELD_EVENT_TYPE) instanceof String;
	}

	public boolean isValidProviderField(final FlatEvent _document) {
		return _document.field(IBasicEventInformations.FIELD_PROVIDER) instanceof String;
	}

	public boolean validate(final FlatEvent _document) {
		return this.hasAllFields(_document) && this.isValidDateField(_document) && this.isValidProviderField(_document)
				&& this.isValidEventTypeField(_document);
	}

}
