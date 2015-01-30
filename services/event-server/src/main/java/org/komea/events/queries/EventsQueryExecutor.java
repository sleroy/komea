package org.komea.events.queries;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.komea.events.dto.EventsFilter;
import org.komea.events.dto.EventsQuery;
import org.komea.events.dto.KomeaEvent;
import org.komea.events.queries.formulas.FormulaUtils;
import org.komea.events.queries.formulas.IFormula;
import org.komea.events.queries.predicates.PredicateDto;
import org.komea.events.queries.predicates.PredicateUtils;
import org.komea.events.service.IEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsQueryExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsQueryExecutor.class);

    public static List<KomeaEvent> filterEvents(final EventsFilter filter, final IEventsService service) {
        final List<KomeaEvent> events = Lists.newArrayList();
        final Iterator<KomeaEvent> iterator;
        if (filter.hasInterval()) {
            iterator = service.loadEventsOfTypeOnPeriod(filter.getEventType(),
                    filter.getInterval());
        } else {
            iterator = service.loadEventsOfType(filter.getEventType());
        }
        while (iterator.hasNext()) {
            final KomeaEvent event = iterator.next();
            if (eventMatches(event, filter)) {
                events.add(event);
            }
        }
        return events;
    }

    private static boolean eventMatches(final KomeaEvent _event, final EventsFilter filter) {
        if (_event == null) {
            return false;
        }
        final PredicateDto predicateDto = filter.getWhere();
        if (predicateDto == null) {
            return true;
        }
        final Predicate<KomeaEvent> predicate = PredicateUtils.fromPredicateDto(predicateDto);
        return predicate.apply(_event);
    }

    private final EventsQuery eventsQuery;
    private final IEventsService eventsService;

    public EventsQueryExecutor(final IEventsService eventStorage, final EventsQuery _eventsQuery) {
        this.eventsService = eventStorage;
        this.eventsQuery = _eventsQuery;
    }

    public Map<String, Number> execute() {
        final List<KomeaEvent> filteredEvents = filterEvents(eventsQuery.getFilter(), eventsService);
        final Map<String, List<KomeaEvent>> groupedEvents = groupEvents(filteredEvents);
        return calculateResults(groupedEvents);
    }

    private Number calculateResults(final List<KomeaEvent> events) {
        final IFormula formula = FormulaUtils.fromFormulaDto(eventsQuery.getFormula());
        return formula.calculate(events);
    }

    private Map<String, Number> calculateResults(final Map<String, List<KomeaEvent>> events) {
        final Map<String, Number> results = Maps.newHashMap();
        for (final String key : events.keySet()) {
            final Number value = calculateResults(events.get(key));
            results.put(key, value);
        }
        return results;
    }

    private Map<String, List<KomeaEvent>> groupEvents(final List<KomeaEvent> events) {
        final Map<String, List<KomeaEvent>> groupedEvents = Maps.newHashMap();
        final String groupBy = eventsQuery.getGroupBy();
        for (final KomeaEvent event : events) {
            final String key = event.field(groupBy);
            if (key == null) {
                continue;
            }
            if (!groupedEvents.containsKey(key)) {
                groupedEvents.put(key, Lists.<KomeaEvent>newArrayList());
            }
            groupedEvents.get(key).add(event);
        }
        return groupedEvents;
    }

}
