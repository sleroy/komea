
package org.komea.product.plugins.scm.repositories.api;



import java.util.List;

import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;



/**
 * This interface offers operations to manipulate a repository definition.
 * 
 * @author sleroy
 */
public interface IScmRepositoryService<TRepoDefinition extends ScmRepositoryDefinition>
{
    
    
    /**
     * Finds a repository by its name
     * 
     * @param _name
     *            the repository name
     * @return the rss feed.
     */
    TRepoDefinition findByName(String _name);
    
    
    /**
     * Returns the list of all repositories.
     * 
     * @return the list of all repositories
     */
    List<TRepoDefinition> getAllRepositories();
    
    
    /**
     * Initialize a cron name for the repository and saves it
     * 
     * @param _fetch
     *            the git repository.
     * @return the cron name.
     */
    String initializeCronName(TRepoDefinition _fetch);
    
    
    /**
     * Returns true if the git repository has an associated cron task.
     */
    boolean isAssociatedToCron(TRepoDefinition _fetch);
    
    
    /**
     * Removes the object.
     * 
     * @param _object
     *            the object to remove
     */
    void remove(TRepoDefinition _object);
    
    
    /**
     * Save or update the git repository.
     * 
     * @param _gitRepository
     *            the git repository.
     */
    void saveOrUpdate(TRepoDefinition _gitRepository);
}
