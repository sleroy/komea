package org.komea.event.storage.convertor;

import java.io.Serializable;
import java.util.Map.Entry;

import org.apache.commons.lang3.Validate;
import org.komea.event.model.api.IBasicEventInformations;
import org.komea.event.model.api.IFlatEvent;
import org.komea.orientdb.session.IDocumentSessionFactory;
import org.komea.orientdb.session.document.IODocument;

/**
 * This class defines a convertor from a flat structure to an event.
 *
 * @author sleroy
 *
 */
public class FlatEventDocumentConvertor {

	private final IDocumentSessionFactory documentDatabaseFactory;
	private final IFlatEvent event;

	public FlatEventDocumentConvertor(
			final IDocumentSessionFactory _documentDatabaseFactory,
			final IFlatEvent _event) {
		this.documentDatabaseFactory = _documentDatabaseFactory;
		this.event = _event;
		Validate.notNull(_event);
		Validate.notNull(_documentDatabaseFactory);

	}

	public IODocument convert() {

		if (!this.event.getProperties().containsKey(
				IBasicEventInformations.FIELD_EVENT_TYPE)) {
			throw new IllegalArgumentException(
					"Missing event type : an event must provide the field "
							+ IBasicEventInformations.FIELD_EVENT_TYPE);
		}
		final Serializable rawEventType = this.event.getProperties().get(
				IBasicEventInformations.FIELD_EVENT_TYPE);
		if (!(rawEventType instanceof String)) {
			throw new IllegalArgumentException(
					"Event type field must be provided as a string");
		}
		final String eventType = String.class.cast(rawEventType);
		final IODocument newDocument = this.documentDatabaseFactory
				.newDocument(eventType);
		for (final Entry<String, ? extends Serializable> entry : this.event
				.getProperties().entrySet()) {
			newDocument.field(entry.getKey(), entry.getValue());
		}
		return newDocument;
	}
}
