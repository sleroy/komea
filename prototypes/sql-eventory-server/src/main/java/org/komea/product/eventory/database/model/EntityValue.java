package org.komea.product.eventory.database.model;

public class EntityValue {

	private String entity;

	private Double value;

	public EntityValue() {
		super();
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
