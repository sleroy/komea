package org.komea.product.eventory.storage.api;

import org.komea.product.eventory.dao.api.IEventSerializable;
import org.komea.product.eventory.rest.dto.ComplexEventDto;
import org.komea.product.eventory.rest.dto.SimpleEventDto;

public interface IEventStorageService {

	void store(ComplexEventDto simpleEventDto);

	void store(IEventSerializable simpleEventDto);

	void store(SimpleEventDto simpleEventDto);

}
