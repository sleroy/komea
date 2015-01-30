package org.komea.events.queries.predicates;

public class PredicateStringCountains extends AbstractPredicate<String> {

    public PredicateStringCountains(final String key, final String predicateValue) {
        super(key, predicateValue);
    }

    @Override
    protected boolean valueMatches(final String eventValue) {
        return eventValue.contains(predicateValue);
    }

}
