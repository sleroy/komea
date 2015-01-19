package org.komea.event.storage.convertor;

import java.io.Serializable;
import java.util.Map.Entry;
import org.apache.commons.lang3.Validate;
import org.komea.event.model.IBasicEventInformations;
import org.komea.event.model.beans.FlatEvent;
import org.komea.orientdb.session.document.IODocument;
import org.komea.orientdb.session.document.IODocumentToolbox;

/**
 * This class defines a convertor from a flat structure to an event.
 *
 * @author sleroy
 *
 */
public class FlatEventDocumentConvertor {

    private final IODocumentToolbox documentDatabaseFactory;
    private final FlatEvent event;

    public FlatEventDocumentConvertor(final IODocumentToolbox _toolbox, final FlatEvent _event) {
        this.documentDatabaseFactory = _toolbox;
        this.event = _event;
        Validate.notNull(_event);
        Validate.notNull(_toolbox);

    }

    public IODocument convert() {

        if (!this.event.getProperties().containsKey(IBasicEventInformations.FIELD_EVENT_TYPE)) {
            throw new IllegalArgumentException(
                    "Missing event type : an event must provide the field " + IBasicEventInformations.FIELD_EVENT_TYPE);
        }
        final Object rawEventType = this.event.getProperties().get(IBasicEventInformations.FIELD_EVENT_TYPE);
        if (!(rawEventType instanceof String)) {
            throw new IllegalArgumentException(
                    "Event type field must be provided as a string");
        }
        final String eventType = String.class.cast(rawEventType);
        final IODocument newDocument = this.documentDatabaseFactory.newDocument(eventType);
        for (final Entry<String, Object> entry : this.event.getProperties().entrySet()) {
            newDocument.field(entry.getKey(), (Serializable) entry.getValue());
        }
        return newDocument;
    }
}
