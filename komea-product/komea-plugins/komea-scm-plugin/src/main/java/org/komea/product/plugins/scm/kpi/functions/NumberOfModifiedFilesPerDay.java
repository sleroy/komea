/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.util.Collection;

import org.komea.product.plugins.scm.api.plugin.IScmCommit;



public class NumberOfModifiedFilesPerDay
{
    
    
    private final Collection<IScmCommit> commitsOfTheDay;
    
    
    
    /**
     * @param _commitsOfTheDay
     */
    public NumberOfModifiedFilesPerDay(final Collection<IScmCommit> _commitsOfTheDay) {
    
    
        commitsOfTheDay = _commitsOfTheDay;
        
        
    }
    
    
    public int compute() {
    
    
        int res = 0;
        for (final IScmCommit commit : commitsOfTheDay) {
            res += commit.getNumberOfModifiedFiles();
        }
        return res;
        
    }
    
}
