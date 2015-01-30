/**
 *
 */
package org.komea.event.model.impl;

import java.util.Date;

/**
 * @author sleroy
 *
 */
public class BasicEvent {
	private String provider;

	private String eventType;

	private Date date;

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param _date
	 *            the date to set
	 */
	public void setDate(final Date _date) {
		date = _date;
	}

	/**
	 * @param _eventType
	 *            the eventType to set
	 */
	public void setEventType(final String _eventType) {
		eventType = _eventType;
	}

	/**
	 * @param _provider
	 *            the provider to set
	 */
	public void setProvider(final String _provider) {
		provider = _provider;
	}
}
