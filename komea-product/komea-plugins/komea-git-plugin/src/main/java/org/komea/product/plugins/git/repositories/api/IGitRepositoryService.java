
package org.komea.product.plugins.git.repositories.api;



import java.util.List;

import org.komea.product.plugins.git.model.GitRepositoryDefinition;



/**
 * Cette interface defines a DAO.
 * 
 * @author sleroy
 */
public interface IGitRepositoryService
{
    
    
    /**
     * Finds a repository by its name
     * 
     * @param _name
     *            the repository name
     * @return the rss feed.
     */
    GitRepositoryDefinition findByName(String _name);
    
    
    /**
     * Returns the list of all repositories.
     * 
     * @return the list of all repositories
     */
    List<GitRepositoryDefinition> getAllRepositories();
    
    
    /**
     * Initialize a cron name for the repository and saves it
     * 
     * @param _fetch
     *            the git repository.
     * @return the cron name.
     */
    String initializeCronName(GitRepositoryDefinition _fetch);
    
    
    /**
     * Returns true if the git repository has an associated cron task.
     */
    boolean isAssociatedToCron(GitRepositoryDefinition _fetch);
    
    
    /**
     * Save or update the git repository.
     * 
     * @param _gitRepository
     *            the git repository.
     */
    void saveOrUpdate(GitRepositoryDefinition _gitRepository);
}
