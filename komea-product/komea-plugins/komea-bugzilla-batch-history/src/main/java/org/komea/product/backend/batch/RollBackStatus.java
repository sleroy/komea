/**
 *
 */

package org.komea.product.backend.batch;



import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.joda.time.DateTime;
import org.komea.product.database.dto.BugBugZilla;
import org.komea.product.database.dto.BugHistory;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.datasource.DataCustomFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;



/**
 * @author sleroy
 */
public class RollBackStatus
{


    private static final Logger    LOGGER = LoggerFactory.getLogger(RollBackStatus.class);


    private final IIssue           bug;
    
    
    private final List<BugHistory> bugChanges;
    
    
    
    public RollBackStatus(final IIssue _issue, final List<BugHistory> _bugChanges) {


        super();

        bug = _issue;
        bugChanges = _bugChanges;


    }
    
    
    public IIssue rollback(final DateTime _untilDate) {


        for (final BugHistory history : bugChanges) {
            final DateTime when = history.getWhen();
            if (when == null) {
                LOGGER.error("No date for the history {} of bug {}", history, bug);
                continue;
            }
            final DateTime whenTime = new DateTime(when);
            if (_untilDate.isAfter(whenTime)) {
                continue;
            }
            final PropertyDescriptor descriptor =
                    BeanUtils.getPropertyDescriptor(BugBugZilla.class, history.getField());
            try {
                org.apache.commons.beanutils.BeanUtils.setProperty(bug, history.getField(),
                        history.getRemoved());
            } catch (final IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (final InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (descriptor == null) {
                LOGGER.error("No descriptor for {} field", history.getField());
                continue;
            }

        }
        ((DataCustomFields) bug.getCustomFields()).put("status", bug.getStatus());
        ((DataCustomFields) bug.getCustomFields()).put("resolution", bug.getResolution());

        return bug;

    }
}
