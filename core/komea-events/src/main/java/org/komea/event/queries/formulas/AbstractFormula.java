package org.komea.event.queries.formulas;

import org.komea.event.model.KomeaEvent;

public abstract class AbstractFormula implements IFormula {

    protected String key;

    public AbstractFormula(final String key) {
        this.key = key;
    }

    public Number getEventValue(final KomeaEvent event) {
        return event.field(key);
    }

}
