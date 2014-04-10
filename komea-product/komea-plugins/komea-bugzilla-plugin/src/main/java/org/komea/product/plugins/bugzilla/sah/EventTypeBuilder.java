package org.komea.product.plugins.bugzilla.sah;

import com.google.common.collect.Lists;
import java.util.List;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;

public abstract class EventTypeBuilder {

    private static EventType baseEventType(final String key) {
        final String name = keyToName(key);
        final EventType eventType = new EventType();
        eventType.setProviderType(ProviderType.BUGTRACKER);
        eventType.setDescription("Number of " + name);
        eventType.setEnabled(true);
        eventType.setEntityType(EntityType.PROJECT);
        eventType.setEventKey(key);
        eventType.setName(name);
        eventType.setSeverity(Severity.INFO);
        return eventType;
    }

    private static String keyToName(final String key) {
        return key.substring(0, 1).toUpperCase() + key.substring(1).replace('_', ' ');
    }

    public static EventType totalBugs() {
        return baseEventType("total_bugs");
    }

    public static EventType statusGroupBugs(BugStatusGroup group) {
        return baseEventType(group.name().toLowerCase() + "_bugs");
    }

    public static EventType statusBugs(final String status) {
        return baseEventType(status.toLowerCase() + "_status_bugs");
    }

    public static EventType severityBugs(final String severity) {
        return baseEventType(severity.toLowerCase() + "_severity_bugs");
    }

    public static EventType priorityBugs(final String priority) {
        return baseEventType(priority.toLowerCase() + "_priority_bugs");
    }

    public static EventType priorityStatusGroupBugs(final String priority, final BugStatusGroup group) {
        return baseEventType(group.name().toLowerCase() + "_" + priority.toLowerCase() + "_priority_bugs");
    }

    public static EventType severityStatusGroupBugs(final String severity, final BugStatusGroup group) {
        return baseEventType(group.name().toLowerCase() + "_" + severity.toLowerCase() + "_severity_bugs");
    }

    public static List<EventType> allEventTypes(final BugzillaServerConfiguration configuration) {
        final List<EventType> eventTypes = Lists.newArrayList();
        eventTypes.add(totalBugs());
        for (final String status : configuration.getStatutes()) {
            eventTypes.add(statusBugs(status));
        }
        for (final String severity : configuration.getSeverities()) {
            eventTypes.add(severityBugs(severity));
        }
        for (final String priority : configuration.getPriorities()) {
            eventTypes.add(priorityBugs(priority));
        }
        for (final BugStatusGroup group : BugStatusGroup.values()) {
            eventTypes.add(statusGroupBugs(group));
            for (final String severity : configuration.getSeverities()) {
                eventTypes.add(severityStatusGroupBugs(severity, group));
            }
            for (final String priority : configuration.getPriorities()) {
                eventTypes.add(priorityStatusGroupBugs(priority, group));
            }
        }
        return eventTypes;
    }

    private EventTypeBuilder() {
    }

}
