/**
 * 
 */

package org.komea.product.plugins.mantis.core;



import biz.futureware.mantis.rpc.soap.client.IssueData;

import com.google.common.base.Predicate;



/**
 * @author sleroy
 */
public class MatcherPredicate implements Predicate<IssueData>
{
    
    
    private final IBugMatcher<IssueData> matcher;
    
    
    
    /**
     * @param _matcher
     */
    public MatcherPredicate(final IBugMatcher<IssueData> _matcher) {
    
    
        matcher = _matcher;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.google.common.base.Predicate#apply(java.lang.Object)
     */
    @Override
    public boolean apply(final IssueData _input) {
    
    
        return matcher.matches(_input);
    }
    
}
