package org.komea.product.eventory.storage.api;

import org.komea.product.eventory.database.model.EventStorage;
import org.komea.product.eventory.rest.dto.ComplexEventDto;
import org.komea.product.eventory.rest.dto.MessageComplexEventDto;
import org.komea.product.eventory.rest.dto.SimpleEventDto;

public interface IEventStorageService {

	void store(ComplexEventDto simpleEventDto);

	void store(MessageComplexEventDto simpleEventDto);

	void store(SimpleEventDto simpleEventDto);

	Integer storeEvent(EventStorage eventStorage);

}
