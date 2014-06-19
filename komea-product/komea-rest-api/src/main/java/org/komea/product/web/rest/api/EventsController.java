package org.komea.product.web.rest.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.enums.Severity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/events")
public class EventsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    private IEventPushService eventPushService;
    @Autowired
    private IEventViewerService eventService;
    @Autowired
    private IEntityService entityService;

    /**
     * This method find events which have been stored into komea
     *
     * @param _searchEvent looking for event of some entities and of dome type
     * @return an event list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/find")
    @ResponseBody
    public List<IEvent> findEvents(@Valid @RequestBody final SearchEventDto _searchEvent) {

        LOGGER.debug("call rest method /events/find to find events {}", _searchEvent);
        final List<IEvent> globalActivity = eventService.getGlobalActivity();
        final List<IEvent> events = new ArrayList<IEvent>(_searchEvent.getMaxEvents());
        final Iterator<IEvent> iterator = globalActivity.iterator();
        while (iterator.hasNext() && events.size() < _searchEvent.getMaxEvents()) {
            final IEvent event = iterator.next();
            if (eventMatches(event, _searchEvent)) {
                events.add(event);
            }
        }
        return events;
    }

    public IEventPushService getEventPushService() {

        return eventPushService;
    }

    /**
     * This method push a new event into komea
     *
     * @param _event the event to push
     */
    @RequestMapping(method = RequestMethod.POST, value = "/push")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushEvent(@Valid @RequestBody final EventSimpleDto _event) {

        LOGGER.debug("call rest method /events/push to push event {}", _event.getMessage());
        // TODO
        eventPushService.sendEventDto(_event);
    }

    private boolean eventMatches(final IEvent event, final SearchEventDto searchEvent) {

        final Severity severity = searchEvent.getSeverityMin();
        final List<String> eventTypeKeys = searchEvent.getEventTypeKeys();
        final List<String> parentEntityKeys = searchEvent.getEntityKeys();
        final ExtendedEntityType extendedEntityType = searchEvent.getEntityType();
        final EntityType kpiType = extendedEntityType.getKpiType();
        final List<BaseEntityDto> parentEntities = entityService.getBaseEntityDTOS(extendedEntityType.getEntityType(), parentEntityKeys);
        final List<BaseEntityDto> entities = entityService.getSubEntities(extendedEntityType, parentEntities);
        final List<String> entityKeys = Lists.newArrayList();
        for (final BaseEntityDto baseEntityDto : entities) {
            entityKeys.add(baseEntityDto.getKey());
        }
        if ((eventTypeKeys.isEmpty() || eventTypeKeys.contains(event.getEventType().getEventKey()))
                && (kpiType == null || kpiType.equals(event.getEventType().getEntityType()))
                && event.getEventType().getSeverity().compareTo(severity) >= 0) {
            if (kpiType == null) {
                return true;
            }
            final String entityKey = eventService.getEntityKey(kpiType, event);
            if (entityKeys.isEmpty() || entityKeys.contains(entityKey)) {
                return true;
            }
        }
        return false;
    }
}
