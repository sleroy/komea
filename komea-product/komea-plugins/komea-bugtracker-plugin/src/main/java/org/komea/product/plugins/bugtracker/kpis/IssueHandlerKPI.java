/**
 *
 */

package org.komea.product.plugins.bugtracker.kpis;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.bugtracking.model.IIssue;



/**
 * This class defines a kpi based on counting the number of bugs answering to a
 * collection of criterias.
 * 
 * @author sleroy
 */
public class IssueHandlerKPI extends

AbstractIssuePersonKPI
{
    
    
    /**
     * Builds the KPI.
     * 
     * @param _dynamicSource
     *            the dyamic data source name, the delay to perform the backup
     */
    public IssueHandlerKPI(final String _dynamicSource, final BackupDelay _delay) {
    
    
        super(_delay, _dynamicSource);
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.bugtracker.kpis.AbstractIssuePersonKPI#obtainPerson(org.komea.product.plugins.bugtracking.model.IIssue)
     */
    @Override
    protected Person obtainPerson(final IIssue _issue) {
    
    
        return _issue.getHandler();
    }
    
}
