/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.util.Collection;

import org.komea.product.plugins.scm.api.plugin.ICommitFunction;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.ScmProjectQueryImplementation;



/**
 * This methods computes the number of commits performed in a day. It takes the list of commits received for a specific day.
 * 
 * @author sleroy
 */
public class NumberOfCommitsPerDayPerProject extends ScmProjectQueryImplementation implements
        ICommitFunction
{
    
    
    @Override
    public double compute(final Collection<IScmCommit> commitsOfTheDay) {
    
    
        return commitsOfTheDay.size();
        
    }
    
    
}
