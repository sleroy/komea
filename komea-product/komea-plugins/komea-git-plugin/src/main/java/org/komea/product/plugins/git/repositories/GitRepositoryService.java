
package org.komea.product.plugins.git.repositories;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This service defines the component that contains repositories.
 * 
 * @author sleroy
 */
@Service
public class GitRepositoryService implements IGitRepositoryService
{
    
    
    private final Map<String, String>                  cronTasks = new HashMap();
    private IDAOObjectStorage<GitRepositoryDefinition> daoStorage;
    
    
    @Autowired
    private IPluginStorageService                      pluginStorageService;
    
    
    
    @Override
    public GitRepositoryDefinition findByName(final String _feedName) {
    
    
        for (final GitRepositoryDefinition feed : daoStorage.selectAll()) {
            if (_feedName.equals(feed.getRepoName())) { return feed; }
        }
        return null;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.rss.repositories.api.IRssRepositories#getDAO()
     */
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.repositories.api.IGitRepositoryService#getAllRepositories()
     */
    @Override
    public List<GitRepositoryDefinition> getAllRepositories() {
    
    
        return getDAO().selectAll();
    }
    
    
    public IDAOObjectStorage<GitRepositoryDefinition> getDAO() {
    
    
        return daoStorage;
    }
    
    
    /**
     * @return the daoStorage
     */
    public IDAOObjectStorage<GitRepositoryDefinition> getDaoStorage() {
    
    
        return daoStorage;
    }
    
    
    public IPluginStorageService getPluginStorageService() {
    
    
        return pluginStorageService;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        daoStorage =
                pluginStorageService
                        .registerDAOStorage("git_plugin", GitRepositoryDefinition.class);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.repositories.api.IGitRepositoryService#initializeCronName(org.komea.product.plugins.git.model.
     * GitRepositoryDefinition)
     */
    @Override
    public synchronized String initializeCronName(final GitRepositoryDefinition _fetch) {
    
    
        final String cronName = "GIT_REPO_CRON_" + _fetch.getRepoName();
        cronTasks.put(_fetch.getKey(), cronName);
        getDAO().saveOrUpdate(_fetch);
        return cronName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.repositories.api.IGitRepositoryService#isAssociatedToCron(org.komea.product.plugins.git.model.
     * GitRepositoryDefinition)
     */
    @Override
    public boolean isAssociatedToCron(final GitRepositoryDefinition _fetch) {
    
    
        return cronTasks.containsKey(_fetch.getKey());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.repositories.api.IGitRepositoryService#saveOrUpdate(org.komea.product.plugins.git.model.
     * GitRepositoryDefinition)
     */
    @Override
    public void saveOrUpdate(final GitRepositoryDefinition _gitRepository) {
    
    
        getDAO().saveOrUpdate(_gitRepository);
        
    }
    
    
    /**
     * Sets the plugin storage service.
     * 
     * @param _pluginStorageService
     *            the plugin storage service.
     */
    public void setPluginStorageService(final IPluginStorageService _pluginStorageService) {
    
    
        pluginStorageService = _pluginStorageService;
    }
    
}
