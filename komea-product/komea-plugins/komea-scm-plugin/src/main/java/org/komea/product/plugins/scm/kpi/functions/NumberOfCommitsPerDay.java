/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.util.Collection;

import org.komea.product.plugins.scm.api.plugin.ICommitFunction;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.ScmUserQueryImplementation;



/**
 * This methods computes the number of commits performed in a day. It takes the list of commits received for a specific day.
 * 
 * @author sleroy
 */
public class NumberOfCommitsPerDay extends ScmUserQueryImplementation implements ICommitFunction
{
    
    
    @Override
    public double compute(final Collection<IScmCommit> commitsOfTheDay) {
    
    
        return commitsOfTheDay.size();
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.kpi.ScmUserQueryImplementation#getCommitFunction()
     */
    @Override
    public ICommitFunction getCommitFunction() {
    
    
        return this;
    }
    
}
