package org.komea.product.backend.kpi.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Search {

    public static Search create(final Filter... filters) {
        return new Search(Arrays.asList(filters));
    }

    public static Search fromString(String searchString) {
        final List<String> filtersString = StringUtils.splitAndTrimWithoutEmpty(searchString, ";");
        final List<Filter> filters = new ArrayList<Filter>(filtersString.size());
        for (final String filterString : filtersString) {
            final Filter bzFilter = Filter.fromString(filterString);
            filters.add(bzFilter);
        }
        return new Search(filters);
    }

    private final Map<String, Filter> filters;

    public Search(final List<Filter> filters) {
        this.filters = new HashMap<String, Filter>(filters.size());
        for (final Filter filter : filters) {
            this.filters.put(filter.getParameterKey(), filter);
        }
    }

    public Collection<Filter> getFilters() {
        return filters.values();
    }

    public Set<String> getParameterkeys() {
        return filters.keySet();
    }

    public Boolean isAccept(final String parameterKey) {
        final Filter filter = filters.get(parameterKey);
        return filter == null ? null : filter.isAccept();
    }

    public List<String> getValues(final String parameterKey) {
        final Filter filter = filters.get(parameterKey);
        return filter == null ? null : filter.getValues();
    }

    @Override
    public String toString() {
        return "Search{" + "filters=" + filters + '}';
    }

    public String getString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Filter filter : filters.values()) {
            stringBuilder.append(filter.getString()).append(";");
        }
        if (!filters.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

}
