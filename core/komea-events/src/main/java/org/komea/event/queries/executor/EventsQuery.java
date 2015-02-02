package org.komea.event.queries.executor;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.komea.event.queries.formulas.FormulaDto;

public class EventsQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private EventsFilter filter;

    @NotNull
    @Size(min = 1)
    private String groupBy;

    @NotNull
    private FormulaDto formula;

    public EventsQuery() {
    }

    public EventsQuery(final EventsFilter filter, final String groupBy, final FormulaDto formula) {
        this.filter = filter;
        this.groupBy = groupBy;
        this.formula = formula;
    }

    public EventsFilter getFilter() {
        return filter;
    }

    public void setFilter(final EventsFilter filter) {
        this.filter = filter;
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
        return "EventsQuery{" + "filter=" + filter
                + ", groupBy=" + groupBy + ", formula=" + formula + '}';
    }

}
