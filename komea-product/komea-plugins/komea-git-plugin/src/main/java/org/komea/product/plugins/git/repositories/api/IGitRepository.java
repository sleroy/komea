
package org.komea.product.plugins.git.repositories.api;



import java.util.List;

import org.komea.product.plugins.git.model.GitRepo;



/**
 * Cette interface defines a DAO.
 * 
 * @author sleroy
 */
public interface IGitRepository
{
    
    
    /**
     * Finds a repository by its name
     * 
     * @param _name
     *            the repository name
     * @return the rss feed.
     */
    GitRepo findByName(String _name);
    
    
    /**
     * Returns the list of all repositories.
     * 
     * @return the list of all repositories
     */
    List<GitRepo> getAllRepositories();
    
    
    /**
     * Initialize a cron name for the repository and saves it
     * 
     * @param _fetch
     *            the git repository.
     * @return the cron name.
     */
    String initializeCronName(GitRepo _fetch);
    
    
    /**
     * Returns true if the git repository has an associated cron task.
     */
    boolean isAssociatedToCron(GitRepo _fetch);
    
    
    /**
     * Save or update the git repository.
     * 
     * @param _gitRepository
     *            the git repository.
     */
    void saveOrUpdate(GitRepo _gitRepository);
}
