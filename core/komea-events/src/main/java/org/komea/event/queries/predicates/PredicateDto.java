package org.komea.event.queries.predicates;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Nonnull;

public class PredicateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Nonnull
    private PredicateType type;
    private String key;
    private Object predicateValue;
    @Nonnull
    private List<PredicateDto> predicates = Lists.newArrayList();

    public PredicateDto() {
    }

    public PredicateDto(PredicateType type, String key, Object predicateValue) {
        this.type = type;
        this.key = key;
        this.predicateValue = predicateValue;
    }

    public PredicateDto(PredicateType type, List<PredicateDto> predicates) {
        this.type = type;
        this.predicates = predicates;
    }

    public PredicateType getType() {
        return type;
    }

    public void setType(PredicateType type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getPredicateValue() {
        return predicateValue;
    }

    public void setPredicateValue(Object predicateValue) {
        this.predicateValue = predicateValue;
    }

    public List<PredicateDto> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<PredicateDto> predicates) {
        this.predicates = predicates;
    }

    @Override
    public String toString() {
        return "PredicateDto{" + "type=" + type + ", key=" + key
                + ", predicateValue=" + predicateValue + ", predicates=" + predicates + '}';
    }

}
