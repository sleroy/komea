/**
 *
 */

package org.komea.product.plugins.bugzilla.service.backup;



import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.komea.product.backend.utils.IFilter;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugzilla.api.IBugZillaToIssueConvertor;
import org.komea.product.plugins.bugzilla.core.RollBackStatus;
import org.komea.product.plugins.bugzilla.datasource.BZIssueWrapper;



/**
 * This filter wraps another filter and modify the issue to reflect time changes on an issue.
 *
 * @author sleroy
 */
public class RebuildFilter implements IFilter<IIssue>
{
    
    
    private DateTime                        checkTime;
    private final IBugZillaToIssueConvertor convertor;
    
    private final IFilter<IIssue>           filter;
    
    
    
    public RebuildFilter(final IFilter<IIssue> _filter, final IBugZillaToIssueConvertor _convertor) {
    
    
        super();
        filter = _filter;
        convertor = _convertor;
        Validate.notNull(filter);
        Validate.notNull(convertor);
    }
    
    
    public DateTime getCheckTime() {


        return checkTime;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.utils.IFilter#matches(java.lang.Object)
     */
    @Override
    public boolean matches(final IIssue _task) {
    
    
        Validate.notNull(checkTime);
        if (checkTime == null) {
            return filter.matches(_task);
        }
        final BZIssueWrapper bzIssueWrapper = (BZIssueWrapper) _task;
        final RollBackStatus rollBackStatus =
                new RollBackStatus(bzIssueWrapper.getDataCustomFields().getFieldsAsMap(),
                        bzIssueWrapper.getHistory());
        return filter.matches(convertToIssue(rollBackStatus, bzIssueWrapper));
    }
    
    
    public void setCheckTime(final DateTime _checkTime) {


        checkTime = _checkTime;
    }
    
    
    private IIssue convertToIssue(final RollBackStatus rollBackStatus, final BZIssueWrapper _task) {


        return convertor.convert(rollBackStatus.rollback(checkTime), _task.getProduct(),
                _task.getServerConfiguration());
    }
}
