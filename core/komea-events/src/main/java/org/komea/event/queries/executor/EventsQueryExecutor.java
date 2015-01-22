package org.komea.event.queries.executor;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.komea.event.model.KomeaEvent;
import org.komea.event.queries.formulas.FormulaUtils;
import org.komea.event.queries.formulas.IFormula;
import org.komea.event.queries.predicates.PredicateDto;
import org.komea.event.queries.predicates.PredicateUtils;
import org.komea.event.storage.IEventDB;
import org.skife.jdbi.v2.ResultIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsQueryExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsQueryExecutor.class);

    private final IEventDB eventDB;
    private final EventsQuery eventsQuery;

    public EventsQueryExecutor(final IEventDB _eventDB, final EventsQuery _eventsQuery) {
        eventDB = _eventDB;
        eventsQuery = _eventsQuery;
    }

    public Map<String, Number> execute() {
        final List<KomeaEvent> filteredEvents = filterEvents();
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

    private List<KomeaEvent> filterEvents() {
        final List<KomeaEvent> events = Lists.newArrayList();
        ResultIterator<KomeaEvent> iterator = null;
        try {
            if (eventsQuery.hasInterval()) {
                iterator = eventDB.loadOnPeriod(eventsQuery.getInterval());
            } else {
                iterator = eventDB.loadAll();
            }
            while (iterator.hasNext()) {
                final KomeaEvent event = iterator.next();
                if (eventMatches(event)) {
                    events.add(event);
                }
            }
        } finally {
            IOUtils.closeQuietly(iterator);
        }
        return events;
    }

    private boolean eventMatches(final KomeaEvent _event) {
        if (_event == null) {
            return false;
        }
        final PredicateDto predicateDto = eventsQuery.getWhere();
        if (predicateDto == null) {
            return true;
        }
        final Predicate<KomeaEvent> predicate = PredicateUtils.fromPredicateDto(predicateDto);
        return predicate.apply(_event);
    }

}
