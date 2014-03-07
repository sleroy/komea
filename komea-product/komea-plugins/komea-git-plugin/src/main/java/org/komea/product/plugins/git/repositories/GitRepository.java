
package org.komea.product.plugins.git.repositories;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.git.model.GitRepo;
import org.komea.product.plugins.git.repositories.api.IGitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This service defines the component that contains repositories.
 * 
 * @author sleroy
 */
@Service
public class GitRepository implements IGitRepository
{
    
    
    private final Map<Long, String>    cronTasks = new HashMap();
    private IDAOObjectStorage<GitRepo> daoStorage;
    
    
    @Autowired
    private IPluginStorageService      pluginStorageService;
    
    
    
    @Override
    public GitRepo findByName(final String _feedName) {
    
    
        for (final GitRepo feed : daoStorage.selectAll()) {
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
     * @see org.komea.product.plugins.git.repositories.api.IGitRepository#getAllRepositories()
     */
    @Override
    public List<GitRepo> getAllRepositories() {
    
    
        return getDAO().selectAll();
    }
    
    
    public IDAOObjectStorage<GitRepo> getDAO() {
    
    
        return daoStorage;
    }
    
    
    /**
     * @return the daoStorage
     */
    public IDAOObjectStorage<GitRepo> getDaoStorage() {
    
    
        return daoStorage;
    }
    
    
    public IPluginStorageService getPluginStorageService() {
    
    
        return pluginStorageService;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        daoStorage = pluginStorageService.registerDAOStorage("rss_plugin", GitRepo.class);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.repositories.api.IGitRepository#initializeCronName(org.komea.product.plugins.git.model.GitRepo)
     */
    @Override
    public synchronized String initializeCronName(final GitRepo _fetch) {
    
    
        final String cronName = "GIT_REPO_CRON_" + _fetch.getRepoName();
        cronTasks.put(_fetch.getId(), cronName);
        getDAO().saveOrUpdate(_fetch);
        return cronName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.repositories.api.IGitRepository#isAssociatedToCron(org.komea.product.plugins.git.model.GitRepo)
     */
    @Override
    public boolean isAssociatedToCron(final GitRepo _fetch) {
    
    
        return cronTasks.containsKey(_fetch.getId());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.repositories.api.IGitRepository#saveOrUpdate(org.komea.product.plugins.git.model.GitRepo)
     */
    @Override
    public void saveOrUpdate(final GitRepo _gitRepository) {
    
    
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
