/**
 *
 */

package org.komea.product.plugins.bugzilla.core;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;



/**
 * @author sleroy
 */
public class RollBackStatus
{


    private final Bug              bug;
    private final List<BugHistory> bugChanges;



    public RollBackStatus(final Bug _bug, final List<BugHistory> _bugChanges) {


        super();

        bug = _bug;
        bugChanges = _bugChanges;


    }


    public Bug rollback(final DateTime _untilDate) {


        final Map<String, Object> parameterMap = new HashMap(bug.getParameterMap());
        for (final BugHistory history : bugChanges) {
            if (_untilDate.isAfter(new DateTime(history.getWhen()))) {
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
