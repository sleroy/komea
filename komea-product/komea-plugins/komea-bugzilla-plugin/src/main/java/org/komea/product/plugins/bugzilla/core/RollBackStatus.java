/**
 *
 */

package org.komea.product.plugins.bugzilla.core;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;



/**
 * @author sleroy
 */
public class RollBackStatus
{
    
    
    private static final Logger    LOGGER = LoggerFactory.getLogger(RollBackStatus.class);
    private final Map<?, ?>        bug;
    
    
    private final List<BugHistory> bugChanges;
    
    
    
    public RollBackStatus(final Map<?, ?> _bugProperties, final List<BugHistory> _bugChanges) {
    
    
        super();
        
        bug = _bugProperties;
        bugChanges = _bugChanges;
        
        
    }
    
    
    public Bug rollback(final DateTime _untilDate) {
    
    
        final Map<String, Object> parameterMap = new HashMap(bug);
        for (final BugHistory history : bugChanges) {
            final DateTime when = history.getWhen();
            if (when == null) {
                LOGGER.error("No date for the history {} of bug {}", history, bug);
                continue;
            }
            if (_untilDate.isAfter(new DateTime(when))) {
                continue;
            }
            for (final BugChange change : history.getBugChanges()) {

                parameterMap.put(change.getField_name(), change.getRemoved());
            }
        }
        
        
        final BugFactory bugFactory = new BugFactory();
        return bugFactory.createBug(parameterMap);
        
    }
}
