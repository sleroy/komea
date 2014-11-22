package org.komea.product.eventory.dao.api;

public interface IEventSerializable extends IObjectSerializable {

	/**
	 * Returns the type of event key.
	 *
	 * @return
	 */
	String getEventKey();

}
