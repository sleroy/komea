
package org.komea.product.plugins.bugzilla.core;



import java.util.Date;
import java.util.HashMap;

import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.EventType;
import org.komea.product.plugins.bugzilla.api.BugStatusGroup;



public class BZEventFactory
{
    
    
    private final EventTypeFactory eventTypeFactory = new EventTypeFactory();
    private final String           project;
    
    private final String           providerUrl;
    
    
    
    public BZEventFactory(final String project, final String providerUrl) {
    
    
        this.project = project;
        this.providerUrl = providerUrl;
    }
    
    
    public EventSimpleDto priorityBugs(final String priority, final int value) {
    
    
        return baseEvent(eventTypeFactory.priorityBugs(priority), value);
    }
    
    
    public EventSimpleDto priorityStatusGroupBugs(
            final String priority,
            final BugStatusGroup group,
            final int value) {
    
    
        return baseEvent(eventTypeFactory.priorityStatusGroupBugs(priority, group), value);
    }
    
    
    public EventSimpleDto severityBugs(final String severity, final int value) {
    
    
        return baseEvent(eventTypeFactory.severityBugs(severity), value);
    }
    
    
    public EventSimpleDto severityStatusGroupBugs(
            final String severity,
            final BugStatusGroup group,
            final int value) {
    
    
        return baseEvent(eventTypeFactory.severityStatusGroupBugs(severity, group), value);
    }
    
    
    public EventSimpleDto statusBugs(final String status, final int value) {
    
    
        return baseEvent(eventTypeFactory.statusBugs(status), value);
    }
    
    
    public EventSimpleDto statusGroupBugs(final BugStatusGroup group, final int value) {
    
    
        return baseEvent(eventTypeFactory.statusGroupBugs(group), value);
    }
    
    
    public EventSimpleDto totalBugs(final int value) {
    
    
        return baseEvent(eventTypeFactory.totalBugs(), value);
    }
    
    
    private EventSimpleDto baseEvent(final EventType eventType, final int value) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(eventType.getEventKey());
        event.setProvider(providerUrl);
        event.setProject(project);
        event.setValue(value);
        event.setMessage("Number of "
                + eventType.getName() + " for project " + project + " is " + value);
        final HashMap<String, String> properties = new HashMap<String, String>(0);
        event.setProperties(properties);
        return event;
    }
    
}
