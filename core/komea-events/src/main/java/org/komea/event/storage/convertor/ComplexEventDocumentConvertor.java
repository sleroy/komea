package org.komea.event.storage.convertor;

import java.util.Map.Entry;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.model.beans.FlatEvent;

/**
 * This class defines a convertor from a complex event to OrientDB.
 *
 * @author sleroy
 *
 */
public class ComplexEventDocumentConvertor {

    private final ComplexEvent event;

    public ComplexEventDocumentConvertor(final ComplexEvent _event) {
        this.event = _event;

    }

    public void convert(final FlatEvent _newDocument) {
        new BasicEventDocumentConvertor(this.event).convert(_newDocument);
        for (final Entry<String, Object> entry : this.event.getProperties().entrySet()) {
            _newDocument.put(entry.getKey(), entry.getValue());

        }
    }
}
