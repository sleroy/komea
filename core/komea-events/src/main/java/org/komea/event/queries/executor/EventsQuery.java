package org.komea.event.queries.executor;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Nonnull;
import org.komea.event.model.DateInterval;
import org.komea.event.queries.formulas.FormulaDto;
import org.komea.event.queries.predicates.PredicateDto;

public class EventsQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Nonnull
    private String eventType;
    private DateInterval interval;
    private PredicateDto where;
    @Nonnull
    private String groupBy;
    @Nonnull
    private FormulaDto formula;

    public EventsQuery() {
    }

    public EventsQuery(final String eventType, final String groupBy, final FormulaDto formula) {
        this.eventType = eventType;
        this.groupBy = groupBy;
        this.formula = formula;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }

    public boolean hasInterval() {
        return interval != null;
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

    public FormulaDto getFormula() {
        return formula;
    }

    public void setFormula(final FormulaDto formula) {
        this.formula = formula;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(final String groupBy) {
        this.groupBy = groupBy;
    }

    @Override
    public String toString() {
        return "EventsQuery{" + "eventType=" + eventType + ", interval=" + interval
                + ", where=" + where + ", groupBy=" + groupBy + ", formula=" + formula + '}';
    }

}
