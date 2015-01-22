package org.komea.event.queries.predicates;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import org.komea.event.model.KomeaEvent;

public abstract class PredicateUtils {

    public static Predicate<KomeaEvent> fromPredicateDto(final PredicateDto dto) {
        final Object predicateValue = dto.getPredicateValue();
        final String key = dto.getKey();
        final List<Predicate<KomeaEvent>> predicates = Lists.newArrayList();
        for (final PredicateDto subPredicateDto : dto.getPredicates()) {
            final Predicate<KomeaEvent> subPredicate = fromPredicateDto(subPredicateDto);
            predicates.add(subPredicate);
        }
        switch (dto.getType()) {
            case AND:
                return new PredicateAnd(predicates);
            case DATE_AFTER:
                return new PredicateDateAfter(key, (Date) predicateValue);
            case DATE_BEFORE:
                return new PredicateDateBefore(key, (Date) predicateValue);
            case OR:
                return new PredicateOr(predicates);
            case STRING_EQUALS:
                return new PredicateStringEquals(key, (String) predicateValue);
            case STRING_CONTAINS:
                return new PredicateStringCountains(key, (String) predicateValue);
            case NUMBER_GREATER:
                return new PredicateNumberGreater(key, (Number) predicateValue);
            case NUMBER_LOWER:
                return new PredicateNumberLower(key, (Number) predicateValue);
        }
        return null;
    }

    private PredicateUtils() {
    }

}
