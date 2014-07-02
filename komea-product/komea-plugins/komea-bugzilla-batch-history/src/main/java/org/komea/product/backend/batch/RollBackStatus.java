/**
 *
 */

package org.komea.product.backend.batch;



import java.util.List;

import org.joda.time.DateTime;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.BugzillaDao;
import org.komea.product.database.dto.BZUser;
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
    
    
    private final BugzillaDao      bugzillaDao;
    
    
    
    public RollBackStatus(
            final IIssue _issue,
            final List<BugHistory> _bugChanges,
            final BugzillaDao _bugzillaDao) {
    
    
        super();
        bugzillaDao = _bugzillaDao;
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
            if (history.getRemoved().equals(history.getAdded())) {
                continue;
            }

            try {
                if ("assigned_to".equals(history.getField())) {
                    final BZUser user =
                            CollectionUtil.singleOrNull(bugzillaDao.findUser(history.getRemoved()));
                    if (user == null) {
                        bug.getAttributes().put(history.getField(), 0);
                    } else {
                        bug.getAttributes().put(history.getField(), user.getUserid());
                    }

                } else {
                    bug.getAttributes().put(history.getField(), history.getRemoved());
                }

            } catch (final Exception e) {
                LOGGER.debug(e.getMessage(), e);
            }


        }


        return bug;

    }
}
