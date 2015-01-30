package org.komea.event.storage.impl;

import java.util.Date;

import org.komea.event.model.IKomeaEvent;
import org.komea.event.model.impl.KomeaEvent;

public class EventStorageValidatorService {

    public boolean hasAllFields(final KomeaEvent _document) {
        return _document.containsField(IKomeaEvent.FIELD_DATE)
                && _document.containsField(IKomeaEvent.FIELD_EVENT_TYPE)
                && _document.containsField(IKomeaEvent.FIELD_PROVIDER);
    }

    public boolean isValidDateField(final KomeaEvent _document) {
        return _document.field(IKomeaEvent.FIELD_DATE) instanceof Date;
    }

    public boolean isValidEventTypeField(final KomeaEvent _document) {
        return _document.field(IKomeaEvent.FIELD_EVENT_TYPE) instanceof String;
    }

    public boolean isValidProviderField(final KomeaEvent _document) {
        return _document.field(IKomeaEvent.FIELD_PROVIDER) instanceof String;
    }

    public boolean validate(final KomeaEvent _document) {
        return this.hasAllFields(_document) && this.isValidDateField(_document) && this.isValidProviderField(_document)
                && this.isValidEventTypeField(_document);
    }

}
