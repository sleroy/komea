/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.util.Collection;

import org.komea.product.plugins.scm.api.plugin.ICommitFunction;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.ScmUserQueryImplementation;



public class AverageCommitMessageLength extends ScmUserQueryImplementation implements
        ICommitFunction
{
    
    
    /**
     * @param _commitsOfTheDay
     */
    public AverageCommitMessageLength() {
    
    
    }
    
    
    @Override
    public double compute(final Collection<IScmCommit> commitsOfTheDay) {
    
    
        long sum = 0L;
        for (final IScmCommit commit : commitsOfTheDay) {
            sum = sum + commit.getMessage().length();
        }
        return sum / commitsOfTheDay.size();
        
        
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
