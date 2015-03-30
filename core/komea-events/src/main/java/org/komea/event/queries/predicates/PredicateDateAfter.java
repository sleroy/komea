package org.komea.event.queries.predicates;

import java.util.Date;

public class PredicateDateAfter extends AbstractPredicate<Date> {

    public PredicateDateAfter(final String key, final Date predicateValue) {
        super(key, predicateValue);
    }

    @Override
    protected boolean valueMatches(final Date eventValue) {
        return eventValue.after(predicateValue);
    }

}
