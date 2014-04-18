package org.komea.product.plugins.bugzilla.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BzBug {

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

    public String getParameter(final String key) {
        return parameters.get(key);
    }

    public Set<String> getKeys() {
        return parameters.keySet();
    }

    @Override
    public String toString() {
        return "BzBug{" + "parameters=" + parameters + '}';
    }

}
