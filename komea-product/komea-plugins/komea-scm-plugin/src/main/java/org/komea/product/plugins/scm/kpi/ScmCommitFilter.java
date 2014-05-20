/**
 * 
 */

package org.komea.product.plugins.scm.kpi;



import java.io.Serializable;

import org.komea.eventory.api.filters.IEventFilter;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class performs a filter that only accepts {@link IScmCommit} valid messages;F
 * 
 * @author sleroy
 */
public class ScmCommitFilter implements IEventFilter
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ScmCommitFilter.class);
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.filters.IEventFilter#isFiltered(java.io.Serializable)
     */
    @Override
    public boolean isFiltered(final Serializable _arg0) {
    
    
        final boolean isCommitEvent = _arg0 instanceof IScmCommit;
        if (!isCommitEvent) {
            return false;
        }
        final IScmCommit commit = (IScmCommit) _arg0;
        final boolean hasCompleteInformations = commit.hasCompleteInformations();
        if (!hasCompleteInformations) {
            LOGGER.warn("Invalid scm commit received {}", _arg0);
        }
        return hasCompleteInformations;
    }
}
