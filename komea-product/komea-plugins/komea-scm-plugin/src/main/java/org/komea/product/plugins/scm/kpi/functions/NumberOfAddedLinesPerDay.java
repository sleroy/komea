/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.util.Collection;

import org.komea.product.plugins.scm.api.plugin.IScmCommit;



public class NumberOfAddedLinesPerDay
{
    
    
    private final Collection<IScmCommit> commitsOfTheDay;
    
    
    
    /**
     * @param _commitsOfTheDay
     */
    public NumberOfAddedLinesPerDay(final Collection<IScmCommit> _commitsOfTheDay) {
    
    
        commitsOfTheDay = _commitsOfTheDay;
        
        
    }
    
    
    public int compute() {
    
    
        int res = 0;
        for (final IScmCommit commit : commitsOfTheDay) {
            res += commit.getNumberOfAddedLines();
        }
        return res;
        
    }
    
}
