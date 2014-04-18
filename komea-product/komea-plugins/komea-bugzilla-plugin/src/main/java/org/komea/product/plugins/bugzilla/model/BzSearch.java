package org.komea.product.plugins.bugzilla.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BzSearch {

    public static BzSearch create(final BzFilter... filters) {
        return new BzSearch(Arrays.asList(filters));
    }

    private final Map<String, BzFilter> filters;

    public BzSearch(final List<BzFilter> filters) {
        this.filters = new HashMap<String, BzFilter>(filters.size());
        for (final BzFilter filter : filters) {
            this.filters.put(filter.getParameterKey(), filter);
        }
    }

    public Collection<BzFilter> getFilters() {
        return filters.values();
    }

    public Set<String> getParameterkeys() {
        return filters.keySet();
    }

    public List<String> getValues(final String parameterKey) {
        final BzFilter filter = filters.get(parameterKey);
        return filter == null ? null : filter.getValues();
    }

}
