package org.komea.event.queries.formulas;

import java.io.Serializable;
import javax.annotation.Nonnull;

public class FormulaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Nonnull
    private FormulaType type;

    private String key;

    public FormulaDto() {
    }

    public FormulaDto(FormulaType type) {
        this.type = type;
    }

    public FormulaDto(FormulaType type, String key) {
        this.type = type;
        this.key = key;
    }

    public FormulaType getType() {
        return type;
    }

    public void setType(FormulaType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Formula{" + "type=" + type + ", key=" + key + '}';
    }

}
