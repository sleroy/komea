
package org.komea.product.plugins.rss.repositories.api;



import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.plugins.rss.model.RssFeed;



/**
 * Cette interface defines a DAO.
 * 
 * @author sleroy
 */
public interface IRssRepositories
{
    
    
    /**
     * Finds a feed by its name
     * 
     * @param _feedName
     *            the feed name
     * @return the rss feed.
     */
    RssFeed findByName(String _feedName);
    
    
    IDAOObjectStorage<RssFeed> getDAO();
    
}
