package org.komea.event.model.api;

import java.io.Serializable;
import java.util.Map;

public interface IComplexEvent extends IBasicEvent {
	/**
	 * Returns a list of extra properties for the event stored into an hashmap.
	 *
	 * @return the properties
	 */
	public Map<String, ? extends Serializable> getProperties();

}
