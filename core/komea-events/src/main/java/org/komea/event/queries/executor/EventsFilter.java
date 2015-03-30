package org.komea.event.queries.executor;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.komea.event.model.impl.DateInterval;
import org.komea.event.queries.predicates.PredicateDto;

public class EventsFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1)
    private String eventType;

    private DateInterval interval;

    private PredicateDto where;

    public EventsFilter() {
    }

    public EventsFilter(final String eventType) {
        this(eventType, null, null);
    }

    public EventsFilter(final String eventType, final DateInterval interval) {
        this(eventType, interval, null);
    }

    public EventsFilter(final String eventType, final PredicateDto where) {
        this(eventType, null, where);
    }

    public EventsFilter(final String eventType, final DateInterval interval, final PredicateDto where) {
        this.eventType = eventType;
        this.interval = interval;
        this.where = where;
    }

    public boolean hasInterval() {
        return interval != null;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }

    public DateInterval getInterval() {
        return interval;
    }

    public void setInterval(final Date from, final Date to) {
        setInterval(new DateInterval(from, to));
    }

    public void setInterval(final DateInterval interval) {
        this.interval = interval;
    }

    public PredicateDto getWhere() {
        return where;
    }

    public void setWhere(final PredicateDto where) {
        this.where = where;
    }

    @Override
    public String toString() {
        return "EventsFilter{" + "eventType=" + eventType
                + ", interval=" + interval + ", where=" + where + '}';
    }

}
