
package org.komea.product.plugins.rss.repositories;



import javax.annotation.PostConstruct;

import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.rss.model.RssFeed;
import org.komea.product.plugins.rss.repositories.api.IRssRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This service defines the component that contains repositories.
 * 
 * @author sleroy
 */
@Service
public class RssRepositories implements IRssRepositories
{
    
    
    private IDAOObjectStorage<RssFeed> daoStorage;
    @Autowired
    private IPluginStorageService      pluginStorageService;
    
    
    
    public RssRepositories() {
    
    
        super();
    }
    
    
    @Override
    public RssFeed findByName(final String _feedName) {
    
    
        for (final RssFeed feed : daoStorage.selectAll()) {
            if (_feedName.equals(feed.getFeedName())) { return feed; }
        }
        return null;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.rss.repositories.api.IRssRepositories#getDAO()
     */
    @Override
    public IDAOObjectStorage<RssFeed> getDAO() {
    
    
        return daoStorage;
    }
    
    
    public IPluginStorageService getPluginStorageService() {
    
    
        return pluginStorageService;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        daoStorage = pluginStorageService.registerDAOStorage("rss_plugin", RssFeed.class);
    }
    
    
    public void setPluginStorageService(final IPluginStorageService _pluginStorageService) {
    
    
        pluginStorageService = _pluginStorageService;
    }
    
}
