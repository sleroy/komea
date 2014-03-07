/**
 * 
 */

package org.komea.product.plugins.git.bean;



import org.komea.product.plugins.git.model.GitRepo;
import org.komea.product.plugins.git.utils.GitCloner;



/**
 * This interface handles Git cloner objects.
 * 
 * @author sleroy
 */
public interface IGitClonerService
{
    
    
    /**
     * Returns the cloner
     * 
     * @return the cloner.
     */
    GitCloner getOrCreate(GitRepo _gitID);
}
