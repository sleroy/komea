package org.komea.event.storage.impl;

import java.io.IOException;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the service to store events into the OrientDB database.
 * .
 *
 * @author sleroy
 */
public class EventStorage implements IEventStorage {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(EventStorage.class);

    private final EventStorageValidatorService validator = new EventStorageValidatorService();

    private final IEventDBFactory eventDBFactory;

    public EventStorage(final IEventDBFactory _eventDBFactory) {
        eventDBFactory = _eventDBFactory;
    }

    @Override
    public void clearEventsOfType(final String _eventType) {
        IEventDB storage = eventDBFactory.getEventDB(_eventType);
        storage.removeAll();
    }

    @Override
    public void close() throws IOException {
        LOGGER.info("Closing the event storage and its database connection.");
        eventDBFactory.close();

    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.event.storage.IEventStorage#declareEventType(java.lang.String)
     */
    @Override
    public void declareEventType(final String _type) {
        eventDBFactory.declareEventType(_type);

    }

    @Override
    public void storeEvent(final KomeaEvent _event) {
        save(_event);
    }

    private void save(final KomeaEvent _document) {
        if (!validator.validate(_document)) {
            LOGGER.error("Event has been rejected {}", _document);
        } else {
            final IEventDB storage = eventDBFactory.getEventDB(_document
                    .getEventType());
            storage.put(_document);
        }
    }

    @Override
    public IEventDB getEventDB(final String eventType) {
        return eventDBFactory.getEventDB(eventType);
    }
}
