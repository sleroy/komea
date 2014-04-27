/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.util.Collection;

import org.komea.product.plugins.scm.api.plugin.ICommitFunction;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;



public class AverageCommitMessageLength implements ICommitFunction
{
    
    
    private final Collection<IScmCommit> commitsOfTheDay;
    
    
    
    /**
     * @param _commitsOfTheDay
     */
    public AverageCommitMessageLength(final Collection<IScmCommit> _commitsOfTheDay) {
    
    
        commitsOfTheDay = _commitsOfTheDay;
        
        
    }
    
    
    @Override
    public double compute() {
    
    
        long sum = 0L;
        for (final IScmCommit commit : commitsOfTheDay) {
            sum = sum + commit.getMessage().length();
        }
        return sum / commitsOfTheDay.size();
        
        
    }
    
}
