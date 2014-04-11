
package org.komea.product.plugins.bugzilla.core;



import java.util.List;

import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.plugins.bugzilla.api.BugStatusGroup;
import org.komea.product.plugins.bugzilla.api.IBZEventTypeFactory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;

import com.google.common.collect.Lists;



public class EventTypeFactory implements IBZEventTypeFactory
{
    
    
    public EventTypeFactory() {
    
    
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.service.IBZEventTypeFactory#allEventTypes(org.komea.product.plugins.bugzilla.model.
     * BZServerConfiguration)
     */
    @Override
    public List<EventType> allEventTypes(final BZServerConfiguration configuration) {
    
    
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
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.service.IBZEventTypeFactory#priorityBugs(java.lang.String)
     */
    @Override
    public EventType priorityBugs(final String priority) {
    
    
        return baseEventType(priority.toLowerCase() + "_priority_bugs");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.service.IBZEventTypeFactory#priorityStatusGroupBugs(java.lang.String,
     * org.komea.product.plugins.bugzilla.api.BugStatusGroup)
     */
    @Override
    public EventType priorityStatusGroupBugs(final String priority, final BugStatusGroup group) {
    
    
        return baseEventType(group.name().toLowerCase()
                + "_" + priority.toLowerCase() + "_priority_bugs");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.service.IBZEventTypeFactory#severityBugs(java.lang.String)
     */
    @Override
    public EventType severityBugs(final String severity) {
    
    
        return baseEventType(severity.toLowerCase() + "_severity_bugs");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.service.IBZEventTypeFactory#severityStatusGroupBugs(java.lang.String,
     * org.komea.product.plugins.bugzilla.api.BugStatusGroup)
     */
    @Override
    public EventType severityStatusGroupBugs(final String severity, final BugStatusGroup group) {
    
    
        return baseEventType(group.name().toLowerCase()
                + "_" + severity.toLowerCase() + "_severity_bugs");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.service.IBZEventTypeFactory#statusBugs(java.lang.String)
     */
    @Override
    public EventType statusBugs(final String status) {
    
    
        return baseEventType(status.toLowerCase() + "_status_bugs");
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.bugzilla.service.IBZEventTypeFactory#statusGroupBugs(org.komea.product.plugins.bugzilla.api.BugStatusGroup)
     */
    @Override
    public EventType statusGroupBugs(final BugStatusGroup group) {
    
    
        return baseEventType(group.name().toLowerCase() + "_bugs");
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.service.IBZEventTypeFactory#totalBugs()
     */
    @Override
    public EventType totalBugs() {
    
    
        return baseEventType("total_bugs");
    }
    
    
    private EventType baseEventType(final String key) {
    
    
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
    
    
    private String keyToName(final String key) {
    
    
        return key.substring(0, 1).toUpperCase() + key.substring(1).replace('_', ' ');
    }
    
}
