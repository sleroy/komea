
package org.komea.product.plugins.rss.bean;



import java.util.Date;
import java.util.List;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.rss.model.RssFeed;
import org.komea.product.plugins.rss.repositories.api.IRssRepositories;
import org.komea.product.plugins.rss.utils.RssFeeder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RssCronJob implements Job
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RssProviderBean.class);
    
    
    
    public RssCronJob() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        Date lastDate = (Date) _context.get("lastDate");
        final IEventPushService esperEngine = (IEventPushService) _context.get("esperEngine");
        final IRssRepositories repository = (IRssRepositories) _context.get("repository");
        
        
        final Date launched = new Date();
        final List<RssFeed> feeds = repository.getDAO().selectAll();
        
        for (final RssFeed fetch : feeds) {
            LOGGER.debug("Fetching RSS feed  : {} {}", fetch.getFeedName(), fetch.getUrl());
            
            new RssFeeder(fetch, lastDate, esperEngine).feed();
        }
        lastDate = launched;
    }
    
    
}
