package org.komea.product.eventory.dao.bean;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.komea.product.eventory.dao.api.IEventSerializable;
import org.komea.product.eventory.database.model.BasicEvent;

import com.orientechnologies.orient.core.record.impl.ODocument;

public class AbstractEventSerializable implements IEventSerializable {

	private final String eventKey;
	private final Map<String, Object> fields;

	public AbstractEventSerializable(final String _eventKey,
			final Map<String, Object> _fields) {
		this.eventKey = _eventKey;
		Validate.notEmpty(_eventKey);
		this.fields = _fields;
		Validate.notNull(_fields);
		Validate.notEmpty(_fields);

	}

	@Override
	public String getEventKey() {

		return this.eventKey;
	}

	@Override
	public void serialize(final ODocument _document) {
		_document.field(BasicEvent.FIELD_EVENTKEY, this.eventKey);
		for (final Entry<String, Object> entry : this.fields.entrySet()) {
			_document.field(entry.getKey(), entry.getValue());
		}

	}

}
