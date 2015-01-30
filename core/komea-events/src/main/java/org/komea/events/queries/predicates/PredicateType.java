package org.komea.events.queries.predicates;

public enum PredicateType {

    AND, OR,
    STRING_EQUALS, STRING_CONTAINS,
    DATE_BEFORE, DATE_AFTER,
    NUMBER_LOWER, NUMBER_GREATER;
}
