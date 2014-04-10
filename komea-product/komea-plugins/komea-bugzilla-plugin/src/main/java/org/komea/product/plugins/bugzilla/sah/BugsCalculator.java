package org.komea.product.plugins.bugzilla.sah;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.komea.product.plugins.bugzilla.data.BugzillaBug;

public final class BugsCalculator {

    private static final Logger LOGGER = Logger.getLogger(BugsCalculator.class.getName());
    private final BugzillaServerConfiguration configuration;
    private final List<BugzillaBug> bugs;
    private final int total;
    private final Map<String, Integer> severityMap = new HashMap<String, Integer>();
    private final Map<String, Integer> priorityMap = new HashMap<String, Integer>();
    private final Map<String, Integer> statusMap = new HashMap<String, Integer>();
    private final Map<BugStatusGroup, Integer> statusGroupMap = new EnumMap<BugStatusGroup, Integer>(BugStatusGroup.class);
    private final Map<BugStatusGroup, Map<String, Integer>> statusGroupBySeverityMap
            = new EnumMap<BugStatusGroup, Map<String, Integer>>(BugStatusGroup.class);
    private final Map<BugStatusGroup, Map<String, Integer>> statusGroupByPriorityMap
            = new EnumMap<BugStatusGroup, Map<String, Integer>>(BugStatusGroup.class);

    public BugsCalculator(final BugzillaServerConfiguration configuration, final List<BugzillaBug> bugs) {
        this.bugs = bugs;
        this.configuration = configuration;
        this.total = bugs.size();
        for (final String bugSeverity : configuration.getSeverities()) {
            severityMap.put(bugSeverity, 0);
        }
        for (final String bugStatus : configuration.getStatutes()) {
            statusMap.put(bugStatus, 0);
        }
        for (final String bugPriority : configuration.getPriorities()) {
            priorityMap.put(bugPriority, 0);
        }
        for (final BugzillaBug bug : bugs) {
            final String bugPriority = bug.getPriority();
            final String bugSeverity = bug.getSeverity();
            final String bugStatus = bug.getStatus();
            if (bugSeverity != null) {
                severityMap.put(bugSeverity, severityMap.get(bugSeverity) + 1);
            }
            if (bugPriority != null) {
                priorityMap.put(bugPriority, priorityMap.get(bugPriority) + 1);
            }
            if (bugStatus != null) {
                statusMap.put(bugStatus, statusMap.get(bugStatus) + 1);
            }
        }
        for (final BugStatusGroup statusGroup : BugStatusGroup.values()) {
            statusGroupMap.put(statusGroup,
                    countByStatutes(configuration.getStatusGroups().get(statusGroup)));
            statusGroupBySeverityMap.put(statusGroup, new HashMap<String, Integer>());
            statusGroupByPriorityMap.put(statusGroup, new HashMap<String, Integer>());
            for (final String bugSeverity : configuration.getSeverities()) {
                statusGroupBySeverityMap.get(statusGroup).put(bugSeverity,
                        countBySeverityAndStatutes(bugSeverity, configuration.getStatusGroups().get(statusGroup)));
            }
            for (final String bugPriority : configuration.getPriorities()) {
                statusGroupByPriorityMap.get(statusGroup).put(bugPriority,
                        countByPriorityAndStatutes(bugPriority, configuration.getStatusGroups().get(statusGroup)));
            }
        }
    }

    public int countByPriorityAndStatutes(final String bugPriority, final List<String> bugStatutes) {
        int count = 0;
        for (final BugzillaBug bug : bugs) {
            if (bugPriority.equals(bug.getPriority()) && bugStatutes.contains(bug.getStatus())) {
                count++;
            }
        }
        return count;
    }

    public int countBySeverityAndStatutes(final String bugSeverity, final List<String> bugStatutes) {
        int count = 0;
        for (final BugzillaBug bug : bugs) {
            if (bugSeverity.equals(bug.getSeverity()) && bugStatutes.contains(bug.getStatus())) {
                count++;
            }
        }
        return count;
    }

    public int countByStatutes(final List<String> bugStatutes) {
        int count = 0;
        for (final String bugStatus : bugStatutes) {
            count += statusMap.get(bugStatus);
        }
        return count;
    }

    public int countStatusGroupBugs(BugStatusGroup group) {
        return statusGroupMap.get(group);
    }

    public int countTotalBugs() {
        return total;
    }

    public int countBugsByStatus(final String status) {
        return statusMap.get(status);
    }

    public int countBugsBySeverity(final String severity) {
        return severityMap.get(severity);
    }

    public int countBugsByPriority(final String priority) {
        return priorityMap.get(priority);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Total: ").append(total).append("\n");
        for (final BugStatusGroup group : BugStatusGroup.values()) {
            stringBuilder.append("StatusGroup ").append(group.name()).append(": ")
                    .append(statusGroupMap.get(group)).append("\n");
        }
        stringBuilder.append("\n");
        for (final String status : configuration.getStatutes()) {
            stringBuilder.append("Status ").append(status).append(": ")
                    .append(statusMap.get(status)).append("\n");
        }
        stringBuilder.append("\n");
        for (final String severity : configuration.getSeverities()) {
            stringBuilder.append("Severity ").append(severity).append(": ")
                    .append(severityMap.get(severity)).append("\n");
        }
        stringBuilder.append("\n");
        for (final String priority : configuration.getPriorities()) {
            stringBuilder.append("Priority ").append(priority).append(": ")
                    .append(priorityMap.get(priority)).append("\n");
        }
        return stringBuilder.toString();
    }

    public BugzillaServerConfiguration getConfiguration() {
        return configuration;
    }

}
