package org.komea.product.plugins.bugzilla.sah;

import java.util.Date;
import java.util.HashMap;
import org.komea.product.plugins.bugzilla.sah.model.BugPriority;
import org.komea.product.plugins.bugzilla.sah.model.BugSeverity;
import org.komea.product.plugins.bugzilla.sah.model.BugStatus;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.EventType;

public class EventBuilder {

    private final String project;
    private final String providerUrl;

    public EventBuilder(String project, String providerUrl) {
        this.project = project;
        this.providerUrl = providerUrl;
    }

    private EventSimpleDto baseEvent(final EventType eventType, final int value) {
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(eventType.getEventKey());
        event.setProvider(providerUrl);
        event.setProject(project);
        event.setValue(value);
        event.setMessage("Number of " + eventType.getName() + " for project " + project + " is " + value);
        final HashMap<String, String> properties = new HashMap<String, String>(0);
        event.setProperties(properties);
        return event;
    }

    public EventSimpleDto totalBugs(final int value) {
        return baseEvent(EventTypeBuilder.totalBugs(), value);
    }

    public EventSimpleDto closedBugs(final int value) {
        return baseEvent(EventTypeBuilder.closedBugs(), value);
    }

    public EventSimpleDto openBugs(final int value) {
        return baseEvent(EventTypeBuilder.openBugs(), value);
    }

    public EventSimpleDto openNotFixedBugs(final int value) {
        return baseEvent(EventTypeBuilder.openNotFixedBugs(), value);
    }

    public EventSimpleDto statusBugs(final BugStatus status, final int value) {
        return baseEvent(EventTypeBuilder.statusBugs(status), value);
    }

    public EventSimpleDto severityBugs(final BugSeverity severity, final int value) {
        return baseEvent(EventTypeBuilder.severityBugs(severity), value);
    }

    public EventSimpleDto priorityBugs(final BugPriority priority, final int value) {
        return baseEvent(EventTypeBuilder.priorityBugs(priority), value);
    }

    public EventSimpleDto priorityOpenBugs(final BugPriority priority, final int value) {
        return baseEvent(EventTypeBuilder.priorityOpenBugs(priority), value);
    }

    public EventSimpleDto severityOpenBugs(final BugSeverity severity, final int value) {
        return baseEvent(EventTypeBuilder.severityOpenBugs(severity), value);
    }

    public EventSimpleDto priorityOpenNotFixedBugs(final BugPriority priority, final int value) {
        return baseEvent(EventTypeBuilder.priorityOpenNotFixedBugs(priority), value);
    }

    public EventSimpleDto severityOpenNotFixedBugs(final BugSeverity severity, final int value) {
        return baseEvent(EventTypeBuilder.severityOpenNotFixedBugs(severity), value);
    }
}
