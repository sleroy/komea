package org.komea.events.service;

import java.util.Iterator;
import java.util.List;
import org.komea.events.dao.IEventsDao;
import org.komea.events.dto.DateInterval;
import org.komea.events.dto.KomeaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsService implements IEventsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsService.class);

    @Autowired
    private EventStorageValidatorService validator;

    @Autowired
    private IEventsDao eventsDao;

    @Override
    public void clearEventsOfType(final String eventType) {
        eventsDao.clearEventsOfType(eventType);
    }

    @Override
    public long countAllEvents() {
        long cpt = 0;
        for (final String eventType : getEventTypes()) {
            cpt += countEventsOfType(eventType);
        }
        return cpt;
    }

    @Override
    public long countEventsOfType(final String eventType) {
        return eventsDao.countEventsOfType(eventType);
    }

    @Override
    public void declareEventType(final String _type) {
        eventsDao.declareEventType(_type);
    }

    @Override
    public void storeEvent(final KomeaEvent _event) {
        LOGGER.info("storeEvent " + _event);
        save(_event);
    }

    private void save(final KomeaEvent event) {
        if (!validator.validate(event)) {
            LOGGER.error("Event has been rejected {}", event);
        } else {
            final String eventType = event.getEventType();
            eventsDao.declareEventType(eventType);
            eventsDao.putEvent(event);
        }
    }

    @Override
    public List<KomeaEvent> getAllEventsOnPeriod(final DateInterval interval, final int limit) {
        return eventsDao.getAllEventsOnPeriod(interval, limit);
    }

    @Override
    public Iterator<KomeaEvent> loadEventsOfType(String eventType) {
        return eventsDao.loadEventsOfType(eventType);
    }

    @Override
    public Iterator<KomeaEvent> loadEventsOfTypeOnPeriod(String eventType, DateInterval period) {
        return eventsDao.loadEventsOfTypeOnPeriod(eventType, period);
    }

    @Override
    public List<String> getEventTypes() {
        return eventsDao.getEventTypes();
    }

    @Override
    public void clearAllEvents() {
        for (final String eventType : getEventTypes()) {
            clearEventsOfType(eventType);
        }
    }

    public void setValidator(EventStorageValidatorService validator) {
        this.validator = validator;
    }

    public void setEventsDao(IEventsDao eventsDao) {
        this.eventsDao = eventsDao;
    }

}
