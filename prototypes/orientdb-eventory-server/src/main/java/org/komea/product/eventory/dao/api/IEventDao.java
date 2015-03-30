package org.komea.product.eventory.dao.api;

import java.util.Map;

public interface IEventDao {

	long countEvents(String _className);

	void insertGeneric(final String _eventKey, Map<String, Object> _fields);

	<TEvent extends IEventSerializable> void insertPojo(TEvent _serializable);

}