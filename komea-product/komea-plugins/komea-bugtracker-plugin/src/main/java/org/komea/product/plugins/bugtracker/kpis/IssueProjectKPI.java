/**
 * 
 */

package org.komea.product.plugins.bugtracker.kpis;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssue;



/**
 * @author sleroy
 */
public class IssueProjectKPI extends AbstractIssueProjectKPI
{
    
    
    /**
     * @param _delay
     * @param _dynamicSource
     */
    public IssueProjectKPI(final BackupDelay _delay, final String _dynamicSource) {
    
    
        super(_delay, _dynamicSource);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.bugtracker.kpis.AbstractIssueProjectKPI#obtainProject(org.komea.product.plugins.bugtracking.model.IIssue)
     */
    @Override
    protected Project obtainProject(final IIssue _issue) {
    
    
        return _issue.getProduct();
    }
    
}
