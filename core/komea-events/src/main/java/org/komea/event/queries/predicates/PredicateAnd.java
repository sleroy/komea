package org.komea.event.queries.predicates;

import com.google.common.base.Predicate;
import java.util.List;
import org.komea.event.model.KomeaEvent;

public class PredicateAnd extends AbstractPredicates {

    public PredicateAnd(final List<Predicate<KomeaEvent>> predicates) {
        super(predicates);
    }

    @Override
    public boolean apply(final KomeaEvent event) {
        for (final Predicate<KomeaEvent> predicate : predicates) {
            if (!predicate.apply(event)) {
                return false;
            }
        }
        return true;
    }

}
