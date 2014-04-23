package org.komea.product.plugins.bugzilla.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.komea.product.plugins.bugzilla.service.StringUtils;

public class BzFilter {

    public static BzFilter create(final String parameterKey, final boolean accept, final String... values) {
        return new BzFilter(parameterKey, accept, Arrays.asList(values));
    }

    public static BzFilter fromString(String filterString) {
        final String[] split = filterString.split("=");
        String key = split[0];
        final boolean accept = !key.endsWith("!");
        if (!accept) {
            key = key.substring(0, key.length() - 1);
        }
        return new BzFilter(key.trim(), accept, StringUtils.splitAndTrimWithoutEmpty(split[1], ","));
    }

    final String parameterKey;
    final boolean accept;
    final List<String> values;

    public BzFilter(final String parameterKey, final boolean accept, final List<String> values) {
        this.parameterKey = parameterKey;
        this.accept = accept;
        this.values = values;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(values);
    }

    public boolean isAccept() {
        return accept;
    }

    @Override
    public String toString() {
        return "BzFilter{" + "parameterKey=" + parameterKey + ", accept=" + accept + ", values=" + values + '}';
    }

    public String getString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(parameterKey).append(accept ? "=" : "!=");
        for (final String value : values) {
            stringBuilder.append(value).append(",");
        }
        if (!values.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

}
