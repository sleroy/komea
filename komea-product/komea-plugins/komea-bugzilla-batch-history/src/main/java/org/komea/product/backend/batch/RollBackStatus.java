/**
 *
 */

package org.komea.product.backend.batch;



import java.util.List;

import org.joda.time.DateTime;
import org.komea.product.database.dto.BugBugZilla;
import org.komea.product.database.dto.BugHistory;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public class RollBackStatus
{
    
    
    private static final Logger    LOGGER = LoggerFactory.getLogger(RollBackStatus.class);
    
    
    private final BugBugZilla      bug;
    
    
    private final List<BugHistory> bugChanges;
    
    
    
    public RollBackStatus(final IIssue _issue, final List<BugHistory> _bugChanges) {
    
    
        super();
        
        bug = (BugBugZilla) _issue;
        bugChanges = _bugChanges;
        
        
    }
    
    
    public IIssue rollback(final DateTime _untilDate) {
    
    
        for (final BugHistory history : bugChanges) {
            final DateTime when = history.getWhen();
            if (when == null) {
                LOGGER.error("No date for the history {} of bug {}", history, bug);
                continue;
            }
            if (when.isBefore(_untilDate)) { // This event happened before the checktime, we don't need to rollback it
                continue;
            }
            
            
            try {
                bug.getAttributes().put(history.getField(), history.getRemoved());
                
            } catch (final Exception e) {
                LOGGER.debug(e.getMessage(), e);
            }
            
            
        }
        
        
        return bug;
        
    }
    
}
