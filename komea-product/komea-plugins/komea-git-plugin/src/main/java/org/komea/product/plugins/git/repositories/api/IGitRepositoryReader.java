/**
 * 
 */

package org.komea.product.plugins.git.repositories.api;



/**
 * @author sleroy
 *
 */
public interface IGitRepositoryReader
{
    
    
    /**
     * Fetch the repository and check updates.
     */
    public abstract void feed();
    
}
