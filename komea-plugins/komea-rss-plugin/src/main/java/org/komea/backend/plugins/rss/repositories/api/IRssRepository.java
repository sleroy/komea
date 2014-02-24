
package org.komea.backend.plugins.rss.repositories.api;



import org.komea.backend.plugins.rss.model.RssFeed;
import org.komea.product.backend.business.IDAOObjectStorage;



/**
 * Cette interface defines a DAO.
 * 
 * @author sleroy
 */
public interface IRssRepository
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
