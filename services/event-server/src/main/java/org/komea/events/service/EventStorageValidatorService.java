package org.komea.events.service;

import java.util.Date;
import org.komea.events.dto.KomeaEvent;
import org.springframework.stereotype.Service;

@Service
public class EventStorageValidatorService {

    public boolean hasAllFields(final KomeaEvent _document) {
        return _document.containsField(KomeaEvent.FIELD_DATE)
                && _document.containsField(KomeaEvent.FIELD_EVENT_TYPE)
                && _document.containsField(KomeaEvent.FIELD_PROVIDER);
    }

    public boolean isValidDateField(final KomeaEvent _document) {
        return _document.field(KomeaEvent.FIELD_DATE) instanceof Date;
    }

    public boolean isValidEventTypeField(final KomeaEvent _document) {
        return _document.field(KomeaEvent.FIELD_EVENT_TYPE) instanceof String;
    }

    public boolean isValidProviderField(final KomeaEvent _document) {
        return _document.field(KomeaEvent.FIELD_PROVIDER) instanceof String;
    }

    public boolean validate(final KomeaEvent _document) {
        return this.hasAllFields(_document) && this.isValidDateField(_document) && this.isValidProviderField(_document)
                && this.isValidEventTypeField(_document);
    }

}
