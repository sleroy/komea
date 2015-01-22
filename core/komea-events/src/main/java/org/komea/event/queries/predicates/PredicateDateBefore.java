package org.komea.event.queries.predicates;

import java.util.Date;

public class PredicateDateBefore extends AbstractPredicate<Date> {

    public PredicateDateBefore(final String key, final Date predicateValue) {
        super(key, predicateValue);
    }

    @Override
    protected boolean valueMatches(final Date eventValue) {
        return eventValue.before(predicateValue);
    }

}
