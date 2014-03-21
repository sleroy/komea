
package org.komea.product.plugins.rss.bean;



// https://forums.terracotta.org/forums/posts/list/2768.page

import java.util.Date;
import java.util.List;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.rss.model.RssFeed;
import org.komea.product.plugins.rss.repositories.api.IRssRepositories;
import org.komea.product.plugins.rss.utils.RssFeeder;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@DisallowConcurrentExecution
public class RssCronJob implements Job
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RssProviderPlugin.class);
    
    
    
    public RssCronJob() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        final JobDataMap mergedJobDataMap = _context.getMergedJobDataMap();
        mergedJobDataMap.get("lastDate");
        final IEventPushService esperEngine =
                (IEventPushService) mergedJobDataMap.get("esperEngine");
        final IRssRepositories repository = (IRssRepositories) mergedJobDataMap.get("repository");
        
        
        final Date launched = new Date();
        final List<RssFeed> feeds = repository.getDAO().selectAll();
        
        for (final RssFeed fetch : feeds) {
            LOGGER.debug("Fetching RSS feed  : {} {}", fetch.getFeedName(), fetch.getUrl());
            try {
                new RssFeeder(fetch, esperEngine).feed();
            } finally {
                repository.getDAO().saveOrUpdate(fetch);
            }
        }
        _context.put("lastDate", launched);
    }
    
}
