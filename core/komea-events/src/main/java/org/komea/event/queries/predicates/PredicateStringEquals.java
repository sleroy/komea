package org.komea.event.queries.predicates;

public class PredicateStringEquals extends AbstractPredicate<String> {

    public PredicateStringEquals(final String key, final String predicateValue) {
        super(key, predicateValue);
    }

    @Override
    protected boolean valueMatches(final String eventValue) {
        return eventValue.equals(predicateValue);
    }

}
