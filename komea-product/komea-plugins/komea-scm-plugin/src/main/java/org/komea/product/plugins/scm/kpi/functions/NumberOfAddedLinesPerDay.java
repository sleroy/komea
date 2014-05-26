/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.util.Collection;

import org.komea.product.plugins.scm.api.plugin.ICommitFunction;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.ScmUserQueryImplementation;



public class NumberOfAddedLinesPerDay extends ScmUserQueryImplementation implements ICommitFunction
{
    
    
    @Override
    public double compute(final Collection<IScmCommit> commitsOfTheDay) {
    
    
        int res = 0;
        for (final IScmCommit commit : commitsOfTheDay) {
            res += commit.getNumberOfAddedLines();
        }
        return res;
        
    }
    
    
}
