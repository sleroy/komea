/**
 * 
 */

package org.komea.product.plugins.bugzilla.api;



import java.util.List;

import org.komea.product.database.model.EventType;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;



/**
 * @author sleroy
 *
 */
public interface IBZEventTypeFactory
{
    
    
    public abstract List<EventType> allEventTypes(BZServerConfiguration configuration);
    
    
    public abstract EventType priorityBugs(String priority);
    
    
    public abstract EventType priorityStatusGroupBugs(String priority, BugStatusGroup group);
    
    
    public abstract EventType severityBugs(String severity);
    
    
    public abstract EventType severityStatusGroupBugs(String severity, BugStatusGroup group);
    
    
    public abstract EventType statusBugs(String status);
    
    
    public abstract EventType statusGroupBugs(BugStatusGroup group);
    
    
    public abstract EventType totalBugs();
    
}
