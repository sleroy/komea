package org.komea.product.backend.service.event;

import java.util.List;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.EventTypeCriteria;

public interface IEventTypeService extends IGenericService<EventType, Integer, EventTypeCriteria> {

    List<EventType> getEventTypes(EntityType entityType);
}
