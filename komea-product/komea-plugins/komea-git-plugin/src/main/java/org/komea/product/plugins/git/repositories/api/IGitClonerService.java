/**
 * 
 */

package org.komea.product.plugins.git.repositories.api;



import org.komea.product.plugins.git.model.GitRepositoryDefinition;



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
    IGitCloner getOrCreate(GitRepositoryDefinition _gitID);
}
