package org.komea.event.queries.predicates;

import com.google.common.base.Predicate;

import java.util.List;

import org.komea.event.model.impl.KomeaEvent;

public class PredicateOr extends AbstractPredicates {

    public PredicateOr(final List<Predicate<KomeaEvent>> predicates) {
        super(predicates);
    }

    @Override
    public boolean apply(final KomeaEvent event) {
        for (final Predicate<KomeaEvent> predicate : predicates) {
            if (predicate.apply(event)) {
                return true;
            }
        }
        return false;
    }

}
