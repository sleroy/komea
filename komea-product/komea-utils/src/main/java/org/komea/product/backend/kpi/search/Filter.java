package org.komea.product.backend.kpi.search;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Filter {

    public static Filter create(final String parameterKey, final boolean accept, final String... values) {
        return new Filter(parameterKey, accept, Arrays.asList(values));
    }

    public static Filter fromString(String filterString) {
        final boolean accept = !filterString.contains("!=");
        final String[] split = filterString.split(accept ? "=" : "!=");
        final String key = split[0].trim();
        return new Filter(key, accept, StringUtils.splitAndTrimWithoutEmpty(split[1], ","));
    }

    final String parameterKey;
    final boolean accept;
    final List<String> values;

    public Filter(final String parameterKey, final boolean accept, final List<String> values) {
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
        return "Filter{" + "parameterKey=" + parameterKey + ", accept=" + accept + ", values=" + values + '}';
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
