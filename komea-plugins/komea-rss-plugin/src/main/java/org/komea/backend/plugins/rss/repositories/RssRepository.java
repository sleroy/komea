
package org.komea.backend.plugins.rss.repositories;



import org.komea.backend.plugins.rss.model.RssFeed;
import org.komea.backend.plugins.rss.repositories.api.IRssRepository;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RssRepository implements IRssRepository
{
    
    
    private IDAOObjectStorage<RssFeed> daoStorage;
    @Autowired
    private IPluginStorageService      pluginStorageService;
    
    
    
    public RssRepository() {
    
    
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
     * @see org.komea.backend.plugins.rss.repositories.api.IRssRepository#getDAO()
     */
    @Override
    public IDAOObjectStorage<RssFeed> getDAO() {
    
    
        return daoStorage;
    }
    
    
    public IPluginStorageService getPluginStorageService() {
    
    
        return pluginStorageService;
    }
    
    
    @Autowired
    public void init() {
    
    
        daoStorage = pluginStorageService.registerDAOStorage("rss_plugin", RssFeed.class);
    }
    
    
    public void setPluginStorageService(final IPluginStorageService _pluginStorageService) {
    
    
        pluginStorageService = _pluginStorageService;
    }
    
}
