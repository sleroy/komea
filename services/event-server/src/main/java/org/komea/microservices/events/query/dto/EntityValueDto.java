package org.komea.microservices.events.query.dto;

import org.komea.microservices.events.database.model.EntityValue;

public class EntityValueDto {

	private String entity;

	private Double value;

	public EntityValueDto() {
		super();
	}

	public EntityValueDto(final EntityValue _value) {
		entity = _value.getEntity();
		value = _value.getValue();
	}

	public String getEntity() {
		return entity;
	}

	public Double getValue() {
		return value;
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
				+ (entity != null ? "entity=" + entity + ", " : "")
				+ (value != null ? "value=" + value : "") + "]";
	}

}
