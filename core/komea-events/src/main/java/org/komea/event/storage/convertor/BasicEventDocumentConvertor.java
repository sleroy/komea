package org.komea.event.storage.convertor;

import java.util.Date;

import org.apache.commons.lang3.Validate;
import org.komea.event.model.IBasicEventInformations;
import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.FlatEvent;

/**
 * This class is a convertor from an basic event to a new orient DB document.
 *
 * @author sleroy
 *
 */
public class BasicEventDocumentConvertor {

	protected final AbstractEvent	event;

	public BasicEventDocumentConvertor(final AbstractEvent _event) {
		super();
		this.event = _event;
		Validate.notNull(_event);
	}

	public void convert(final FlatEvent _newDocument) {

		Validate.notNull(this.event.getProvider());
		Validate.notNull(this.event.getEventType());
		if (this.event.getDate() == null) {
			this.event.setDate(new Date());
		}
		Validate.notNull(this.event.getDate());

		_newDocument.put(IBasicEventInformations.FIELD_DATE, this.event.getDate());
		_newDocument.put(IBasicEventInformations.FIELD_EVENT_TYPE, this.event.getEventType());
		_newDocument.put(IBasicEventInformations.FIELD_PROVIDER, this.event.getProvider());

	}
}
