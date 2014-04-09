
package org.komea.product.plugins.scm.api;



import java.util.List;

import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;



/**
 * This interface offers operations to manipulate a repository definition.
 * 
 * @author sleroy
 */
public interface IScmRepositoryService
{
    
    
    /**
     * Finds a repository by its name
     * 
     * @param _name
     *            the repository name
     * @return the rss feed.
     */
    ScmRepositoryDefinition findByName(String _name);
    
    
    /**
     * Returns the list of all repositories.
     * 
     * @return the list of all repositories
     */
    List<ScmRepositoryDefinition> getAllRepositories();
    
    
    /**
     * Returns the list of repositories not associated to a cron job.
     * 
     * @return the list of repositories not associated to a cron job.
     */
    List<ScmRepositoryDefinition> getRepositoriesNotAssociated();
    
    
    /**
     * Returns true if the git repository has an associated cron task.
     */
    boolean isAssociatedToCron(ScmRepositoryDefinition _fetch);
    
    
    /**
     * Initialize a cron name for the repository and saves it
     * 
     * @param _fetch
     *            the git repository.
     * @return the cron name.
     */
    String registerCronJobOfScm(ScmRepositoryDefinition _fetch);
    
    
    /**
     * Removes the object.
     * 
     * @param _object
     *            the object to remove
     */
    void remove(ScmRepositoryDefinition _object);
    
    
    /**
     * Save or update the git repository.
     * 
     * @param _gitRepository
     *            the git repository.
     */
    void saveOrUpdate(ScmRepositoryDefinition _gitRepository);
    
    
    /**
     * Update the repositories by forcing cron.
     */
    void updateRepositories();
}
