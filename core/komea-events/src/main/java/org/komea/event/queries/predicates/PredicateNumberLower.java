package org.komea.event.queries.predicates;

public class PredicateNumberLower extends AbstractPredicate<Number> {

    public PredicateNumberLower(final String key, final Number predicateValue) {
        super(key, predicateValue);
    }

    @Override
    protected boolean valueMatches(final Number eventValue) {
        return eventValue.doubleValue() < predicateValue.doubleValue();
    }

}
