package org.komea.event.model.api;

import java.util.Date;

public interface IBasicEvent {

	/**
	 *
	 * @return the date the event has been sent.
	 */
	public Date getDate();

	/**
	 * Returns the type of event
	 *
	 * @return a string identifying the type of event i.e ("new_bugzilla_bug")
	 */
	public String getEventType();

	/**
	 * Returns the name of the event provider
	 *
	 * @return i.e "Bugzilla_3.4"
	 */
	public String getProvider();
}
