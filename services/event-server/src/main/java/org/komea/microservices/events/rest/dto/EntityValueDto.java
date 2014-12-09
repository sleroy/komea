package org.komea.microservices.events.rest.dto;

import org.komea.microservices.events.database.model.EntityValue;

public class EntityValueDto {

	private String entity;

	private Double value;

	public EntityValueDto() {
		super();
	}

	public EntityValueDto(final EntityValue _value) {
		this.entity = _value.getEntity();
		this.value = _value.getValue();
	}

	public String getEntity() {
		return this.entity;
	}

	public Double getValue() {
		return this.value;
	}

	public void setEntity(final String entity) {
		this.entity = entity;
	}

	public void setValue(final Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "EntityValue ["
				+ (this.entity != null ? "entity=" + this.entity + ", " : "")
				+ (this.value != null ? "value=" + this.value : "") + "]";
	}

}
