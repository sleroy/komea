package org.komea.product.eventory.rest.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ComplexEventDto extends SimpleEventDto {

	private Map<String, String> extraFields = Collections.emptyMap();

	public Map<String, String> getExtraFields() {
		return this.extraFields;
	}

	@JsonIgnore
	public void initExtraFields() {
		this.extraFields = new HashMap();

	}

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
