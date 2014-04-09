package org.komea.product.database.enums;

public enum Operator {

    EQUAL("="), LESSER("<"), GREATER(">"), DISTINCT("!="), GREATER_OR_EQUAL(">="), LESSER_OR_EQUAL("<=");

    private final String value;

    private Operator(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Operator getOpposite() {
        final int size = values().length;
        return values()[(ordinal() + size / 2) % size];
    }
}
