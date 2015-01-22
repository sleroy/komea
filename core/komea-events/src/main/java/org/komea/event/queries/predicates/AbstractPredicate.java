package org.komea.event.queries.predicates;

import com.google.common.base.Predicate;
import org.komea.event.model.KomeaEvent;

public abstract class AbstractPredicate<T> implements Predicate<KomeaEvent> {

    protected String key;
    protected T predicateValue;

    public AbstractPredicate(final String key, final T predicateValue) {
        this.key = key;
        this.predicateValue = predicateValue;
    }

    public T getEventProperty(final KomeaEvent event) {
        return event.field(key);
    }

    protected abstract boolean valueMatches(final T eventValue);

    @Override
    public boolean apply(final KomeaEvent event) {
        final T eventValue = getEventProperty(event);
        if (eventValue == null) {
            return false;
        }
        return valueMatches(eventValue);
    }

}
