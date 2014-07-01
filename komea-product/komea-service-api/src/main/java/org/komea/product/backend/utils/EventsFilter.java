package org.komea.product.backend.utils;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.enums.Severity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventsFilter {

    @Autowired
    private IEventViewerService eventService;
    
    @Autowired
    private IEntityService entityService;

    public List<IEvent> filterEvents(SearchEventDto searchEvent, final List<IEvent> _events) {   	
        final List<IEvent> result = Lists.newArrayList();
        final Iterator<IEvent> iterator = _events.iterator();
        while (iterator.hasNext() && _events.size() < searchEvent.getMaxEvents()) {
            final IEvent event = iterator.next();
            if (eventMatches(searchEvent, event)) {
                _events.add(event);
            }
        }
        return result;
    }

    private boolean eventMatches(SearchEventDto searchEvent, final IEvent event) {
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
