package org.komea.product.plugins.bugzilla.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.komea.product.backend.kpi.search.ISearchedElement;

public class BzBug implements ISearchedElement {

    public static BzBug create(final String status, final String resolution,
            final String severity, final String priority) {
        final Map<String, String> parameters = new HashMap<String, String>(4);
        parameters.put("status", status);
        parameters.put("resolution", resolution);
        parameters.put("severity", severity);
        parameters.put("priority", priority);
        final BzBug bzBug = new BzBug(parameters);
        return bzBug;
    }

    private final Map<String, String> parameters;

    public BzBug(final Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String getParameter(final String key) {
        return parameters.get(key.toLowerCase());
    }

    @Override
    public Collection<String> getKeys() {
        final Collection<String> keys = new HashSet<String>(parameters.size());
        for (final String key : parameters.keySet()) {
            keys.add(key.toLowerCase());
        }
        return keys;
    }

    @Override
    public String toString() {
        return "BzBug{" + "parameters=" + parameters + '}';
    }

}
