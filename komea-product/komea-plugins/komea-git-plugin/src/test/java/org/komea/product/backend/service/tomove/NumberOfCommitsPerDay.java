/**
 * 
 */

package org.komea.product.backend.service.tomove;



import java.util.Collection;

import org.komea.product.plugins.scm.api.plugin.IScmCommit;



/**
 * This methods computes the number of commits performed in a day. It takes the list of commits received for a specific day.
 * 
 * @author sleroy
 */
public class NumberOfCommitsPerDay
{
    
    
    private final Collection<IScmCommit> commitsOfTheDay;
    
    
    
    /**
     * @param _commitsOfTheDay
     */
    public NumberOfCommitsPerDay(final Collection<IScmCommit> _commitsOfTheDay) {
    
    
        commitsOfTheDay = _commitsOfTheDay;
        
        
    }
    
    
    public int compute() {
    
    
        return commitsOfTheDay.size();
        
    }
    
}
