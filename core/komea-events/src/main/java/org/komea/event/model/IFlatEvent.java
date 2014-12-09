package org.komea.event.model;

import java.io.Serializable;
import java.util.Map;

public interface IFlatEvent {

	public Map<String, ? extends Serializable> getProperties();

}
