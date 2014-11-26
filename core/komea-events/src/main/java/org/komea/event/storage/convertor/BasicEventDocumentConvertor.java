package org.komea.event.storage.convertor;

import org.apache.commons.lang3.Validate;
import org.komea.event.model.api.IBasicEvent;
import org.komea.event.model.api.IBasicEventInformations;
import org.komea.orientdb.session.document.IODocument;

/**
 * This class is a convertor from an basic event to a new orient DB document.
 *
 * @author sleroy
 *
 */
public class BasicEventDocumentConvertor<TEvent extends IBasicEvent> {

	protected final TEvent event;

	public BasicEventDocumentConvertor(final TEvent _event) {
		super();
		this.event = _event;
		Validate.notNull(_event);
	}

	public void convert(final IODocument _newDocument) {

		Validate.notNull(this.event.getProvider());
		Validate.notNull(this.event.getEventType());
		Validate.notNull(this.event.getDate());

		_newDocument.field(IBasicEventInformations.FIELD_DATE,
				this.event.getDate());
		_newDocument.field(IBasicEventInformations.FIELD_EVENT_TYPE,
				this.event.getEventType());
		_newDocument.field(IBasicEventInformations.FIELD_PROVIDER,
				this.event.getProvider());

	}

}
