package org.komea.product.eventory.database.model;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;

import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Serialize into the database a basic event + extra properties.
 *
 * @author sleroy
 *
 */
public class ComplexGenericEvent extends BasicEvent {

	private Map<String, Object> extraFields;

	public ComplexGenericEvent() {
		super();
	}

	public ComplexGenericEvent(final Map<String, Object> _extraFields) {
		super();
		this.extraFields = Collections.unmodifiableMap(_extraFields);
		Validate.notNull(this.extraFields);

	}

	public Map<String, Object> getExtraFields() {
		return this.extraFields;
	}

	@Override
	public void serialize(final ODocument _document) {
		super.serialize(_document);
		Validate.notNull(_document, "Document could not be generated");
		for (final Entry<String, Object> entry : this.extraFields.entrySet()) {
			_document.field(entry.getKey(), entry.getValue());
		}
	}

	public void setExtraFields(final Map<String, Object> extraFields) {
		this.extraFields = extraFields;
	}

}
