/**
 * 
 */

package org.komea.product.plugins.scm.utils;



import org.komea.product.plugins.scm.api.plugin.IScmCommit;



/**
 * @author sleroy
 */
public interface IScmCommitGroupingFunction<T>
{
    
    
    /**
     * Builds a key from a commit
     * 
     * @param _commit
     *            the commit.
     * @return the computed key.
     */
    T getKey(IScmCommit _commit);
}
