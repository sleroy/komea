package org.komea.event.model.beans;

import java.io.Serializable;
import java.util.Date;

public final class BasicEvent extends AbstractEvent implements Serializable {

	public BasicEvent() {
		super();
		this.date = new Date();
	}

	public BasicEvent(final String _provider, final String _eventType) {
		super();
		this.provider = _provider;
		this.eventType = _eventType;
		this.date = new Date();
	}

	public BasicEvent(final String _provider, final String _eventType, final Date _date) {
		super();
		this.provider = _provider;
		this.eventType = _eventType;
		this.date = _date;
	}

}