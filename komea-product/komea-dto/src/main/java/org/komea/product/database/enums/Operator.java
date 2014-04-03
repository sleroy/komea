package org.komea.product.database.enums;

public enum Operator {

    EQUAL("="), DISTINCT("!="), LESSER("<"), GREATER(">"), LESSER_OR_EQUAL("<="), GREATER_OR_EQUAL(">=");

    private final String value;

    private Operator(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
