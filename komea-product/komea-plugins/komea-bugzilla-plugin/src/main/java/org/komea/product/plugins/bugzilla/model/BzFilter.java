package org.komea.product.plugins.bugzilla.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BzFilter {

    public static BzFilter create(final String parameterKey, final String... values) {
        return new BzFilter(parameterKey, Arrays.asList(values));
    }

    final String parameterKey;
    final List<String> values;

    public BzFilter(final String parameterKey, final List<String> values) {
        this.parameterKey = parameterKey;
        this.values = values;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(values);
    }

}
