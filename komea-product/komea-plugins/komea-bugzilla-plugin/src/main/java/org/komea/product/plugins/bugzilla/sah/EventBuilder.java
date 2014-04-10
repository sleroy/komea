package org.komea.product.plugins.bugzilla.sah;

import java.util.Date;
import java.util.HashMap;
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

    public EventSimpleDto statusGroupBugs(final BugStatusGroup group, final int value) {
        return baseEvent(EventTypeBuilder.statusGroupBugs(group), value);
    }

    public EventSimpleDto statusBugs(final String status, final int value) {
        return baseEvent(EventTypeBuilder.statusBugs(status), value);
    }

    public EventSimpleDto severityBugs(final String severity, final int value) {
        return baseEvent(EventTypeBuilder.severityBugs(severity), value);
    }

    public EventSimpleDto priorityBugs(final String priority, final int value) {
        return baseEvent(EventTypeBuilder.priorityBugs(priority), value);
    }

    public EventSimpleDto priorityStatusGroupBugs(final String priority, final BugStatusGroup group, final int value) {
        return baseEvent(EventTypeBuilder.priorityStatusGroupBugs(priority, group), value);
    }

    public EventSimpleDto severityStatusGroupBugs(final String severity, final BugStatusGroup group, final int value) {
        return baseEvent(EventTypeBuilder.severityStatusGroupBugs(severity, group), value);
    }

}
