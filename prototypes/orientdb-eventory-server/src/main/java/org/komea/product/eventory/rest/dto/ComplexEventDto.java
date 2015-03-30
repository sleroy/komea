package org.komea.product.eventory.rest.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class ComplexEventDto extends SimpleEventDto {

	private Map<String, String> extraFields = Collections.emptyMap();

	public ComplexEventDto() {
	}

	public Map<String, String> getExtraFields() {
		return this.extraFields;
	}

	@JsonIgnore
	public void initExtraFields() {
		this.extraFields = new HashMap();

	}

	@JsonIgnore
	@Override
	public void serialize(final ODocument _document) {
		super.serialize(_document);
		for (final Entry<String, String> entry : this.extraFields.entrySet()) {
			_document.field(entry.getKey(), entry.getValue());
		}

	}

	@JsonIgnore
	public void setExtraFields(final Map<String, String> extraFields) {
		this.extraFields = extraFields;
	}

	@Override
	public String toString() {
		return "ComplexEventDto ["
				+ (this.extraFields != null ? "extraFields=" + this.extraFields
						+ ", " : "")
						+ (super.toString() != null ? "toString()=" + super.toString()
								: "") + "]";
	}
}
