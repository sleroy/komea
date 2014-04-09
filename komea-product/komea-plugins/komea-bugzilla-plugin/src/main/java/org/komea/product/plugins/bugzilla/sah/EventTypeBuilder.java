package org.komea.product.plugins.bugzilla.sah;

import java.util.ArrayList;
import java.util.List;
import org.komea.product.plugins.bugzilla.sah.model.BugPriority;
import org.komea.product.plugins.bugzilla.sah.model.BugSeverity;
import org.komea.product.plugins.bugzilla.sah.model.BugStatus;
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

    public static EventType closedBugs() {
        return baseEventType("closed_bugs");
    }

    public static EventType openBugs() {
        return baseEventType("open_bugs");
    }

    public static EventType openNotFixedBugs() {
        return baseEventType("open_not_fixed_bugs");
    }

    public static EventType statusBugs(final BugStatus status) {
        return baseEventType(status.name().toLowerCase() + "_status_bugs");
    }

    public static EventType severityBugs(final BugSeverity severity) {
        return baseEventType(severity.name().toLowerCase() + "_severity_bugs");
    }

    public static EventType priorityBugs(final BugPriority priority) {
        return baseEventType(priority.name().toLowerCase() + "_priority_bugs");
    }

    public static EventType priorityOpenBugs(final BugPriority priority) {
        return baseEventType("open_" + priority.name().toLowerCase() + "_priority_bugs");
    }

    public static EventType severityOpenBugs(final BugSeverity severity) {
        return baseEventType("open_" + severity.name().toLowerCase() + "_severity_bugs");
    }

    public static EventType priorityOpenNotFixedBugs(final BugPriority priority) {
        return baseEventType("open_not_fixed_" + priority.name().toLowerCase() + "_priority_bugs");
    }

    public static EventType severityOpenNotFixedBugs(final BugSeverity severity) {
        return baseEventType("open_not_fixed_" + severity.name().toLowerCase() + "_severity_bugs");
    }

    public static List<EventType> allEventTypes() {
        final List<EventType> eventTypes = new ArrayList<EventType>(22);
        eventTypes.add(totalBugs());
        eventTypes.add(closedBugs());
        eventTypes.add(openBugs());
        eventTypes.add(openNotFixedBugs());
        for (final BugStatus status : BugStatus.values()) {
            eventTypes.add(statusBugs(status));
        }
        for (final BugSeverity severity : BugSeverity.values()) {
            eventTypes.add(severityBugs(severity));
            eventTypes.add(severityOpenBugs(severity));
            eventTypes.add(severityOpenNotFixedBugs(severity));
        }
        for (final BugPriority priority : BugPriority.values()) {
            eventTypes.add(priorityBugs(priority));
            eventTypes.add(priorityOpenBugs(priority));
            eventTypes.add(priorityOpenNotFixedBugs(priority));
        }
        return eventTypes;
    }

    private EventTypeBuilder() {
    }

}
