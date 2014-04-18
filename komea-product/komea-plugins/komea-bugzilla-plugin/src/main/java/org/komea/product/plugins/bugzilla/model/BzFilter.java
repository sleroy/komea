package org.komea.product.plugins.bugzilla.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BzFilter {

    public static BzFilter create(final String parameterKey, final String... values) {
        return new BzFilter(parameterKey, Arrays.asList(values));
    }

    public static BzFilter fromString(String filterString) {
        final String[] split = filterString.split("=");
        return new BzFilter(split[0], Arrays.asList(split[1].split(",")));
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

    @Override
    public String toString() {
        return "BzFilter{" + "parameterKey=" + parameterKey + ", values=" + values + '}';
    }

}
