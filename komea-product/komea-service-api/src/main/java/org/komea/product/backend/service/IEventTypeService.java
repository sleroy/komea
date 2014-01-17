package org.komea.product.backend.service;

import org.komea.product.database.dao.EventTypeDao;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;

public interface IEventTypeService {

    public EventTypeDao getEventTypeDAO();

    /**
     * Registers an event
     *
     * @param _provider the provider
     * @param _eventType the event type.
     */
    public void registerEvent(Provider _provider, EventType _eventType);

}
