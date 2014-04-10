package org.komea.product.plugins.bugzilla.sah;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class BugzillaServerConfiguration {

    private final List<String> statutes = Lists.newArrayList();
    private final List<String> severities = Lists.newArrayList();
    private final List<String> priorities = Lists.newArrayList();
    private final Map<BugStatusGroup, List<String>> statusGroups
            = new EnumMap<BugStatusGroup, List<String>>(BugStatusGroup.class);

    public BugzillaServerConfiguration() {
        for (final BugStatusGroup group : BugStatusGroup.values()) {
            statusGroups.put(group, new ArrayList<String>());
        }
    }

    public BugzillaServerConfiguration(final List<String> statutes, final List<String> severities,
            final List<String> priorities, final Map<BugStatusGroup, List<String>> statusGroups) {
        this();
        this.statutes.addAll(statutes);
        this.severities.addAll(severities);
        this.priorities.addAll(priorities);
        this.statusGroups.putAll(statusGroups);
    }

    public List<String> getStatutes() {
        return statutes;
    }

    public List<String> getSeverities() {
        return severities;
    }

    public List<String> getPriorities() {
        return priorities;
    }

    public Map<BugStatusGroup, List<String>> getStatusGroups() {
        return statusGroups;
    }

}
