package org.komea.product.eventory.rest.dto;

import java.util.Date;

import org.komea.product.eventory.dao.api.IEventSerializable;
import org.komea.product.eventory.database.model.BasicEvent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class SimpleEventDto implements IEventSerializable {
	private String provider;

	private Date date;

	private String eventKey;

	private Double value;

	private Double value2;

	private Double value3;

	private Double value4;

	private Double value5;

	private String entityKey1;

	private String entityKey2;

	private String entityKey3;

	private String entityKey4;

	private String message;

	public SimpleEventDto() {
		super();
	}

	public SimpleEventDto(final String provider, final String eventKey,
			final Double value, final String message, final String entityType,
			final String entityKey, final String serverURL) {
		super();
		this.provider = provider;
		this.eventKey = eventKey;
		this.value = value;
		this.message = message;
	}

	public Date getDate() {
		return this.date;
	}

	public String getEntityKey1() {
		return this.entityKey1;
	}

	public String getEntityKey2() {
		return this.entityKey2;
	}

	public String getEntityKey3() {
		return this.entityKey3;
	}

	public String getEntityKey4() {
		return this.entityKey4;
	}

	@Override
	public String getEventKey() {
		return this.eventKey;
	}

	public String getMessage() {
		return this.message;
	}

	public String getProvider() {
		return this.provider;
	}

	public Double getValue() {
		return this.value;
	}

	public Double getValue2() {
		return this.value2;
	}

	public Double getValue3() {
		return this.value3;
	}

	public Double getValue4() {
		return this.value4;
	}

	public Double getValue5() {
		return this.value5;
	}

	@JsonIgnore
	@Override
	public void serialize(final ODocument _document) {

		final BasicEvent basicEvent = new BasicEvent();
		basicEvent.setDate(this.date);
		basicEvent.setEventKey(this.eventKey);
		basicEvent.setProvider(this.provider);
		basicEvent.serialize(_document);

		_document.field("value", this.value);
		_document.field("value2", this.value2);
		_document.field("value3", this.value3);
		_document.field("value4", this.value4);
		_document.field("value5", this.value5);

		_document.field("entityKey1", this.entityKey1);
		_document.field("entityKey2", this.entityKey1);
		_document.field("entityKey3", this.entityKey3);
		_document.field("entityKey4", this.entityKey4);

		_document.field("message", this.message);

	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public void setEntityKey1(final String entityKey1) {
		this.entityKey1 = entityKey1;
	}

	public void setEntityKey2(final String entityKey2) {
		this.entityKey2 = entityKey2;
	}

	public void setEntityKey3(final String entityKey3) {
		this.entityKey3 = entityKey3;
	}

	public void setEntityKey4(final String entityKey4) {
		this.entityKey4 = entityKey4;
	}

	public void setEventKey(final String eventKey) {
		this.eventKey = eventKey;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setProvider(final String provider) {
		this.provider = provider;
	}

	public void setValue(final Double value) {
		this.value = value;
	}

	public void setValue2(final Double value2) {
		this.value2 = value2;
	}

	public void setValue3(final Double value3) {
		this.value3 = value3;
	}

	public void setValue4(final Double value4) {
		this.value4 = value4;
	}

	public void setValue5(final Double value5) {
		this.value5 = value5;
	}

	@Override
	public String toString() {
		return "SimpleEventDto [provider=" + this.provider + ", date="
				+ this.date + ", eventKey=" + this.eventKey + ", value="
				+ this.value + ", value2=" + this.value2 + ", value3="
				+ this.value3 + ", value4=" + this.value4 + ", value5="
				+ this.value5 + ", entityKey1=" + this.entityKey1
				+ ", entityKey2=" + this.entityKey2 + ", entityKey3="
				+ this.entityKey3 + ", entityKey4=" + this.entityKey4
				+ ", message=" + this.message + "]";
	}
}
