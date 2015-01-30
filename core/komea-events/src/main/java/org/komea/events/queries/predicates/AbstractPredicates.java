package org.komea.events.queries.predicates;

import com.google.common.base.Predicate;
import java.util.List;
import org.komea.events.dto.KomeaEvent;

public abstract class AbstractPredicates implements Predicate<KomeaEvent> {

    protected List<Predicate<KomeaEvent>> predicates;

    public AbstractPredicates(final List<Predicate<KomeaEvent>> predicates) {
        super();
        this.predicates = predicates;
    }

}
