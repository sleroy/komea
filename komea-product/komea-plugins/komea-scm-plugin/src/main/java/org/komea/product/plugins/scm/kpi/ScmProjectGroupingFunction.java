/**
 * 
 */

package org.komea.product.plugins.scm.kpi;



import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.utils.IScmCommitGroupingFunction;
import org.komea.product.service.dto.EntityKey;



/**
 * @author sleroy
 */
public class ScmProjectGroupingFunction implements IScmCommitGroupingFunction<EntityKey>
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.utils.IScmCommitGroupingFunction#getKey(org.komea.product.plugins.scm.api.plugin.IScmCommit)
     */
    @Override
    public EntityKey getKey(final IScmCommit _commit) {
    
    
        return _commit.getProject().getEntityKey();
    }
    
}
