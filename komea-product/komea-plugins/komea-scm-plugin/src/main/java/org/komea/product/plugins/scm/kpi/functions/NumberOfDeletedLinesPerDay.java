/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.util.Collection;

import org.komea.product.plugins.scm.api.plugin.ICommitFunction;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.ScmUserQueryImplementation;



public class NumberOfDeletedLinesPerDay extends ScmUserQueryImplementation implements
        ICommitFunction
{
    
    
    @Override
    public double compute(final Collection<IScmCommit> commitsOfTheDay) {
    
    
        int res = 0;
        for (final IScmCommit commit : commitsOfTheDay) {
            res += commit.getNumberOfDeletedLines();
        }
        return res;
        
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
