package org.komea.events.queries.predicates;

public class PredicateNumberGreater extends AbstractPredicate<Number> {

    public PredicateNumberGreater(final String key, final Number predicateValue) {
        super(key, predicateValue);
    }

    @Override
    protected boolean valueMatches(final Number eventValue) {
        return eventValue.doubleValue() > predicateValue.doubleValue();
    }

}
